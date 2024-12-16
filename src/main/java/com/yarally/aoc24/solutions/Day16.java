package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;
import com.yarally.aoc24.library.Maps.ObstacleMap;
import com.yarally.aoc24.library.Point;
import com.yarally.aoc24.library.Tuple.Tuple;
import com.yarally.aoc24.library.Tuple.Tuple3;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Day16 extends AbstractSolution<Tuple3<ObstacleMap, Point, Point>> {

    @Override
    protected String getInput() {
        return "day16.txt";
    }

    @Override
    protected Tuple3<ObstacleMap, Point, Point> parse(String input) {
        var lines = FileReader.readFile(input);
        Set<Point> obstacles = new HashSet<>();
        Point start = null;
        Point end = null;
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                char[] chars = lines.get(y).toCharArray();
                if (chars[x] == '#') {
                    obstacles.add(new Point(x, y));
                }
                if (chars[x] == 'S') {
                    start = new Point(x, y);
                }
                if (chars[x] == 'E') {
                    end = new Point(x, y);
                }
            }
        }
        ObstacleMap map = new ObstacleMap(new int[]{lines.get(0).length(), lines.size()},
            obstacles);
        return new Tuple3<>(map, start, end);
    }

    @Override
    protected String solve1(Tuple3<ObstacleMap, Point, Point> input) {
        var map = input.x;
        var start = input.y;
        var end = input.z;
        return bfs(map, start, end) + "";
    }

    @Override
    protected String solve2(Tuple3<ObstacleMap, Point, Point> input) {
        var map = input.x;
        var start = input.y;
        var end = input.z;
        var minScore = bfs(map, start, end);
        var nonPathPositions = new HashMap<Tuple<Point, Point>, Integer>();
        var pathPoints = new HashSet<Point>();
        dfs(map, end, minScore, start, Point.RIGHT, 0, pathPoints, nonPathPositions);
        return pathPoints.size() + "";
    }

    private int bfs(ObstacleMap map, Point start, Point end) {
        var directions = List.of(Point.UP, Point.RIGHT, Point.DOWN, Point.LEFT);
        HashMap<Tuple<Point, Point>, Integer> g = new HashMap<>();
        Queue<Tuple<Point, Point>> todo = new PriorityQueue<>(Comparator.comparingInt(
            tup -> (Math.abs(end.getY() - tup.x.getY()) + Math.abs(end.getX() - tup.x.getX())
                + g.get(tup))));
        var tStart = new Tuple<>(start, new Point(1, 0));
        g.put(tStart, 0);
        todo.add(tStart);
        while (!todo.isEmpty()) {
            var current = todo.poll();
            var cPos = current.x;
            var cDir = current.y;
            for (var nDir : directions) {
                var nPos = cPos.add(nDir);
                if ((nDir.getX() != 0 && nDir.getX() == -cDir.getX()) || (nDir.getY() != 0
                    && nDir.getY() == -cDir.getY())) {
                    continue;
                }
                if (map.hitObstacle(nPos)) {
                    continue;
                }
                var neigh = new Tuple<>(nPos, nDir);
                int gScore;
                if (nDir.equals(cDir)) {
                    gScore = g.get(current) + 1;
                } else {
                    gScore = g.get(current) + 1001;
                }
                if (g.containsKey(neigh) && g.get(neigh) < gScore) {
                    continue;
                }
                g.put(neigh, gScore);
                todo.add(neigh);
            }

        }
        int min = Integer.MAX_VALUE;
        for (var dir : directions) {
            var t = new Tuple<>(end, dir);
            if (g.containsKey(t) && g.get(t) < min) {
                min = g.get(t);
            }
        }
        return min;
    }

    private boolean dfs(ObstacleMap map, Point end, int targetScore, Point cPos, Point cDir,
        int score, HashSet<Point> pathPoints,
        HashMap<Tuple<Point, Point>, Integer> nonPathPositions) {
        if (nonPathPositions.containsKey(new Tuple<>(cPos, cDir))
            && nonPathPositions.get(new Tuple<>(cPos, cDir)) <= score) {
            return false;
        }
        var directions = List.of(Point.UP, Point.RIGHT, Point.DOWN, Point.LEFT);
        if (score > targetScore) {
            return false;
        }
        if (cPos.equals(end)) {
            pathPoints.add(cPos);
            return true;
        }
        var pathExists = false;
        for (var nDir : directions) {
            var nPos = cPos.add(nDir);
            if ((nDir.getX() != 0 && nDir.getX() == -cDir.getX()) || (nDir.getY() != 0
                && nDir.getY() == -cDir.getY())) {
                continue;
            }
            if (map.hitObstacle(nPos)) {
                continue;
            }
            if (nDir.equals(cDir)) {
                if (dfs(map, end, targetScore, nPos, nDir, score + 1, pathPoints,
                    nonPathPositions)) {
                    pathExists = true;
                }
            } else {
                if (dfs(map, end, targetScore, nPos, nDir, score + 1001, pathPoints,
                    nonPathPositions)) {
                    pathExists = true;
                }
            }
        }
        if (pathExists) {
            pathPoints.add(cPos);
            return true;
        }
        nonPathPositions.put(new Tuple<>(cPos, cDir), score);
        return false;
    }
}
