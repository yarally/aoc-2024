package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.*;
import com.yarally.aoc24.library.Maps.ObstacleMap;
import com.yarally.aoc24.library.Tuple.Tuple;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day6 extends AbstractSolution<Tuple<ObstacleMap, Point>> {
    @Override
    protected String getInput() {
        return "day6.txt";
    }

    @Override
    protected Tuple<ObstacleMap, Point> parse(String input) {
        List<String> lines = FileReader.readFile(input).reversed();
        Point startPos = null;
        Set<Point> obstacles = new HashSet<>();
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                char[] chars = lines.get(y).toCharArray();
                if (chars[x] == '#') {
                    obstacles.add(new Point(x, y));
                }
                if (chars[x] == '^'){
                    startPos = new Point(x, y);
                }
            }
        }
        int[] bounds = new int[] {lines.get(0).length(), lines.size()};
        return new Tuple<>(new ObstacleMap(bounds, obstacles), startPos);
    }

    @Override
    protected String solve1(Tuple<ObstacleMap, Point> input) {

        return (getPath(input).size() - 1) + "";
    }

    @Override
    protected String solve2(Tuple<ObstacleMap, Point> input) {
        int count = 0;
        var validPositions = getPath(input);
        for (int i = 0; i < input.x.bounds[1]; i++) {
            for (int j = 0; j < input.x.bounds[0]; j++) {
                Point newObstacle = new Point(j, i);
                if (!validPositions.contains(newObstacle)) {
                    continue;
                }
                var map = input.x.getClone();
                int[] direction = new int[] { 0, 1 };
                Set<String> visited = new HashSet<>();
                var guard = input.y.getClone();
                visited.add(guard.getClone().toString() + direction[0] + direction[1]);
                if (map.hitObstacle(newObstacle)) {
                    continue;
                }
                map.addObstacle(newObstacle);
                while (!map.outOfBounds(guard)) {
                    if (map.hitObstacle(guard.dryMove(direction[0], direction[1]))) {
                        direction = new int[] {direction[1], -direction[0]};
                        continue;
                    }
                    var nextState = guard.move(direction[0], direction[1]).toString() + direction[0] + direction[1];
                    if (visited.contains(nextState)) {
                        count++;
                        break;
                    }
                    visited.add(nextState);
                }
            }
        }
        return count + "";
    }

    private Set<Point> getPath(Tuple<ObstacleMap, Point> input) {
        var map = input.x;
        var guard = input.y.getClone();
        int[] direction = new int[] { 0, 1 };
        Set<Point> visited = new HashSet<>();
        visited.add(guard.getClone());
        while (!map.outOfBounds(guard)) {
            if (map.hitObstacle(guard.dryMove(direction[0], direction[1]))) {
                direction = new int[] {direction[1], -direction[0]};
                continue;
            }
            visited.add(guard.move(direction[0], direction[1]).getClone());
        }
        return visited;
    }
}
