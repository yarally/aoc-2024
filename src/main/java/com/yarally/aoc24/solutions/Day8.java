package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.*;
import com.yarally.aoc24.library.Maps.ObstacleMap;
import com.yarally.aoc24.library.Tuple.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Day8 extends AbstractSolution<Tuple<ObstacleMap, HashMap<String, List<Point>>>> {
    @Override
    protected String getInput() {
        return "day8.txt";
    }

    @Override
    protected Tuple<ObstacleMap, HashMap<String, List<Point>>> parse(String input) {
        var lines = FileReader.readFile(input);
        HashMap<String, List<Point>> antennas = new HashMap<>();
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                char[] chars = lines.get(y).toCharArray();
                if (chars[x] != '.') {
                    if (!antennas.containsKey(String.valueOf(chars[x]))) {
                        antennas.put(String.valueOf(chars[x]), new ArrayList<>());
                    }
                    antennas.get(String.valueOf(chars[x])).add(new Point(x, y));
                }
            }
        }
        ObstacleMap map = new ObstacleMap(new int[]{lines.get(0).length(), lines.size()}, null);
        return new Tuple<>(map, antennas);
    }

    @Override
    protected String solve1(Tuple<ObstacleMap, HashMap<String, List<Point>>> input) {
        var map = input.x;
        var antennas = input.y;
        var antiNodes = new HashSet<Point>();
        antennas.forEach((freq, points) -> {
            points.forEach(p1 -> {
                points.forEach(p2 -> {
                    if (p1 != p2) {
                        var dir = new Point(p2.getX()-p1.getX(), p2.getY()-p1.getY());
                        var n1 = (new Point(p2.getX() + dir.getX(), p2.getY() + dir.getY()));
                        var n2 = (new Point(p1.getX() - dir.getX(), p1.getY() - dir.getY()));
                        if (!map.outOfBounds(n1)) {
                            antiNodes.add(n1);
                        }
                        if (!map.outOfBounds(n2)) {
                            antiNodes.add(n2);
                        }
                    }
                });
            });
        });
        return antiNodes.size() + "";
    }

    @Override
    protected String solve2(Tuple<ObstacleMap, HashMap<String, List<Point>>> input) {
        var map = input.x;
        var antennas = input.y;
        var antiNodes = new HashSet<Point>();
        antennas.forEach((freq, points) -> {
            points.forEach(p1 -> {
                points.forEach(p2 -> {
                    if (p1 != p2) {
                        var dir = new Point(p2.getX()-p1.getX(), p2.getY()-p1.getY());
                        var div = gcd(Math.abs(dir.getX()), Math.abs(dir.getY()));
                        var unitDir = new Point(dir.getX() / div, dir.getY() / div);
                        var current = p1.getClone();
                        while (!map.outOfBounds(current.dryMove(unitDir.getX(), unitDir.getY()))) {
                            antiNodes.add(current.move(unitDir.getX(), unitDir.getY()).getClone());
                        }
                        current = p1.getClone();
                        while (!map.outOfBounds(current.dryMove(-unitDir.getX(), -unitDir.getY()))) {
                            antiNodes.add(current.move(-unitDir.getX(), -unitDir.getY()).getClone());
                        }
                    }
                });
            });
        });
        return antiNodes.size() + "";
    }

    int gcd(int n1, int n2) {
        if (n2 == 0) {
            return n1;
        }
        return gcd(n2, n1 % n2);
    }
}
