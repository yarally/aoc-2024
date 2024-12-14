package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;
import com.yarally.aoc24.library.Maps.ObstacleMap;
import com.yarally.aoc24.library.Point;
import com.yarally.aoc24.library.Tuple;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day14 extends AbstractSolution<List<Tuple<Point, Point>>> {
    @Override
    protected String getInput() {
        return "day14.txt";
    }

    @Override
    protected List<Tuple<Point, Point>> parse(String input) {
        var lines = FileReader.readFile(input);
        List<Tuple<Point, Point>> parsed = new ArrayList<>();
        Pattern pattern = Pattern.compile("-*\\d+");
        for (var line : lines) {
            Matcher m = pattern.matcher(line);
            m.find();
            int x = Integer.parseInt(m.group());
            m.find();
            int y = Integer.parseInt(m.group());
            m.find();
            int vx = Integer.parseInt(m.group());
            m.find();
            int vy = Integer.parseInt(m.group());
            parsed.add(new Tuple<>(new Point(x, y), new Point(vx, vy)));
        }
        return parsed;
    }

    @Override
    protected String solve1(List<Tuple<Point, Point>> input) {
        var seconds = 100L;
        int[] bounds = new int[]{101, 103};
        List<Point> finalPositions = new ArrayList<>();
        input.forEach(tup -> {
            var p = tup.x;
            var v = tup.y;
            long targetX = p.getX() + v.getX() * seconds;
            long targetY = p.getY() + v.getY() * seconds;
            finalPositions.add(new Point((int) (((targetX % bounds[0]) + bounds[0]) % bounds[0]), (int) ((targetY % bounds[1]) + bounds[1]) % bounds[1]));
        });
        int[] quads = new int[]{0, 0, 0, 0};
        finalPositions.forEach(p -> {
            if (p.getX() < bounds[0] / 2 && p.getY() > bounds[1] / 2) {
                quads[0]++;
            }
            if (p.getX() < bounds[0] / 2 && p.getY() < bounds[1] / 2) {
                quads[2]++;
            }
            if (p.getX() > bounds[0] / 2  && p.getY() > bounds[1] / 2) {
                quads[1]++;
            }
            if (p.getX() > bounds[0] / 2 && p.getY() < bounds[1] / 2) {
                quads[3]++;
            }
        });
        return quads[0] * quads[1] * quads[2] * quads[3] + "";
    }

    @Override
    protected String solve2(List<Tuple<Point, Point>> input) {
        int[] bounds = new int[]{101, 103};
        for (int i = 0; i < 101*103; i++) {
            List<Point> finalPositions = new ArrayList<>();
            input.forEach(tup -> {
                var p = tup.x;
                var v = tup.y;
                long targetX = p.getX() + v.getX();
                long targetY = p.getY() + v.getY();
                p.update((int) (((targetX % bounds[0]) + bounds[0]) % bounds[0]), (int) ((targetY % bounds[1]) + bounds[1]) % bounds[1]);
                finalPositions.add(p);
            });

            if (finalPositions.size() == new HashSet<>(finalPositions).size()) {
                return i+1 + "";
            }
        }
        return "404 Tree not found!";
    }
}
