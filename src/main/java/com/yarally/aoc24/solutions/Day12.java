package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;
import com.yarally.aoc24.library.Maps.RichMap;
import com.yarally.aoc24.library.Point;
import com.yarally.aoc24.library.Tuple;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day12 extends AbstractSolution<RichMap<Character>> {

    @Override
    protected String getInput() {
        return "day12.txt";
    }

    @Override
    protected RichMap<Character> parse(String input) {
        var lines = FileReader.readFile(input);
        var values = new Character[lines.size()][lines.getFirst().length()];
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.getFirst().length(); x++) {
                char[] chars = lines.get(y).toCharArray();
                values[y][x] = chars[x];
            }
        }
        return new RichMap<>(values);
    }

    @Override
    protected String solve1(RichMap<Character> input) {
        var plots = new ArrayList<Plot>();
        for (int y = 0; y < input.bounds[1]; y++) {
            for (int x = 0; x < input.bounds[0]; x++) {
                var p = new Point(x, y);
                if (plots.stream().anyMatch(plt -> plt.getPositions().contains(p))) {
                    continue;
                }
                plots.add(bfs(p, input));
            }
        }
        return plots.stream().mapToInt(Plot::getScore).sum() + "";
    }

    @Override
    protected String solve2(RichMap<Character> input) {
        var plots = new ArrayList<Plot>();
        for (int y = 0; y < input.bounds[1]; y++) {
            for (int x = 0; x < input.bounds[0]; x++) {
                var p = new Point(x, y);
                if (plots.stream().anyMatch(plt -> plt.getPositions().contains(p))) {
                    continue;
                }
                plots.add(bfs(p, input));
            }
        }
        return plots.stream().mapToInt(Plot::getBulkScore).sum() + "";
    }

    private Plot bfs(Point start, RichMap<Character> map) {
        LinkedList<Point> todo = new LinkedList<>();
        todo.push(start);
        Set<Point> visited = new HashSet<>();
        while (!todo.isEmpty()) {
            var current = todo.pop();
            visited.add(current);
            for (var neigh : current.get4Neighbours(map, true)) {
                if (!visited.contains(neigh)
                    && !todo.contains(neigh)
                    && map.getValue(current) == map.getValue(neigh)) {
                    todo.push(neigh);
                }
            }
        }
        return new Plot(map.getValue(start), visited, map);
    }

    private static class Plot {

        private final char crop;
        private final Set<Point> positions;
        private RichMap<Character> map;

        public Plot(char crop, Set<Point> positions, RichMap<Character> map) {
            this.crop = crop;
            this.positions = positions;
            this.map = map;
        }

        public Set<Point> getPositions() {
            return positions;
        }

        public int getPerimeter() {
            var perimeter = 0;
            for (var p : positions) {
                for (var n : p.get4Neighbours(map, false)) {
                    var val = map.getValue(n);
                    if (val == null || val != crop) {
                        perimeter++;
                    }
                }
            }
            return perimeter;
        }

        public int getBulkPerimeter() {
            List<Tuple<Point, Point>> perimeter = new ArrayList<>();
            for (var p : positions) {
                for (var n : p.get4Neighbours(map, false)) {
                    var val = map.getValue(n);
                    if (val == null || val != crop) {
                        perimeter.add(new Tuple<>(p, n.min(p)));
                    }
                }
            }
            var fences = 0;
            Set<Tuple<Point, Point>> visited = new HashSet<>();
            for (var tp : perimeter) {
                if (visited.contains(tp)) {
                    continue;
                }
                visited.add(tp);
                var pos = tp.x;
                var dir = tp.y;
                var options = perimeter.stream().filter(t -> t.y.equals(dir)).map(t -> t.x)
                    .collect(Collectors.toSet());
                // TOP & Bot
                if (dir.getY() != 0) {
                    var current = pos.getClone();
                    while (true) {
                        if (options.contains(current.move(1, 0))) {
                            visited.add(new Tuple<>(current.getClone(), dir));
                        } else {
                            break;
                        }
                    }
                    current = pos.getClone();
                    while (true) {
                        if (options.contains(current.move(-1, 0))) {
                            visited.add(new Tuple<>(current.getClone(), dir));
                        } else {
                            break;
                        }
                    }
                    // LEFT & RIGHT
                } else {
                    var current = pos.getClone();
                    while (true) {
                        if (options.contains(current.move(0, 1))) {
                            visited.add(new Tuple<>(current.getClone(), dir));
                        } else {
                            break;
                        }
                    }
                    current = pos.getClone();
                    while (true) {
                        if (options.contains(current.move(0, -1))) {
                            visited.add(new Tuple<>(current.getClone(), dir));
                        } else {
                            break;
                        }
                    }
                }
                fences++;
            }
            return fences;
        }

        public int getScore() {
            return positions.size() * getPerimeter();
        }

        public int getBulkScore() {
            return positions.size() * getBulkPerimeter();
        }

        @Override
        public String toString() {
            return "Plot{" +
                "crop=" + crop +
                ", area=" + positions.size() +
                ", perimeter=" + getPerimeter() +
                '}';
        }
    }
}
