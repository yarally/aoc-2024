package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;
import com.yarally.aoc24.library.Maps.RichMap;
import com.yarally.aoc24.library.Point;
import com.yarally.aoc24.library.Tuple.Tuple;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Day10 extends AbstractSolution<Tuple<RichMap<Integer>, Set<Point>>> {
    @Override
    protected String getInput() {
        return "day10.txt";
    }

    @Override
    protected Tuple<RichMap<Integer>, Set<Point>> parse(String input) {
        var lines = FileReader.readFile(input);
        var values = new Integer[lines.size()][lines.get(0).length()];
        var zeroPositions = new HashSet<Point>();
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {
                char[] chars = lines.get(y).toCharArray();
                var value = Integer.parseInt(String.valueOf(chars[x]));
                if (value == 0) {
                    zeroPositions.add(new Point(x, y));
                }
                values[y][x] = value;
            }
        }
        var map = new RichMap<>(values);
        return new Tuple<>(map, zeroPositions);
    }

    @Override
    protected String solve1(Tuple<RichMap<Integer>, Set<Point>> input) {
        var map = input.x;
        var zeroPositions = input.y;
        var paths = 0;
        for (var start : zeroPositions) {
            paths += findShortestPaths(start, map);
        }
        return paths + "";
    }

    private int findShortestPaths(Point start, RichMap<Integer> map) {
        var paths = 0;
        var directions = new Point[] {new Point(1, 0), new Point(0, -1), new Point(-1, 0), new Point(0, 1)};
        var visited = new HashSet<Point>();
        var todo = new Stack<Point>();
        todo.addFirst(start);
        while (!todo.isEmpty()) {
            var current = todo.pop();
            visited.add(current);
            if (map.getValue(current.getX(), current.getY()) == 9) {
                paths++;
                continue;
            }
            var value = map.getValue(current.getX(), current.getY());
            for (var dir : directions) {
                var neighbour = current.add(dir);
                if (map.outOfBounds(neighbour) || map.getValue(neighbour.getX(), neighbour.getY()) - value != 1 || visited.contains(neighbour)) {
                    continue;
                }
                todo.addLast(neighbour);
            }
        }
        return paths;
    }

    @Override
    protected String solve2(Tuple<RichMap<Integer>, Set<Point>> input) {
        var map = input.x;
        var zeroPositions = input.y;
        var paths = 0;
        for (var start : zeroPositions) {
            paths += findAllPaths(start, map);
        }
        return paths + "";
    }

    private int findAllPaths(Point start, RichMap<Integer> map) {
        var paths = 0;
        var directions = new Point[] {new Point(1, 0), new Point(0, -1), new Point(-1, 0), new Point(0, 1)};
        var todo = new Stack<Point>();
        todo.addFirst(start);
        while (!todo.isEmpty()) {
            var current = todo.pop();
            if (map.getValue(current.getX(), current.getY()) == 9) {
                paths++;
                continue;
            }
            var value = map.getValue(current.getX(), current.getY());
            for (var dir : directions) {
                var neighbour = current.add(dir);
                if (map.outOfBounds(neighbour) || map.getValue(neighbour.getX(), neighbour.getY()) - value != 1) {
                    continue;
                }
                todo.addLast(neighbour);
            }
        }
        return paths;
    }
}
