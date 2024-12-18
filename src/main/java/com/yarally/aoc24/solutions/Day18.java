package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;
import com.yarally.aoc24.library.Maps.ObstacleMap;
import com.yarally.aoc24.library.Point;
import com.yarally.aoc24.library.Tuple.Tuple;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Day18 extends AbstractSolution<Tuple<ObstacleMap, List<Point>>> {

    @Override
    protected String getInput() {
        return "day18.txt";
    }

    @Override
    protected Tuple<ObstacleMap, List<Point>> parse(String input) {
        var lines = FileReader.readFile(input);
        var bytes = new ArrayList<Point>();
        for (var line : lines) {
            var coords = line.split(",");
            bytes.add(new Point(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])));
        }
        return new Tuple<>(new ObstacleMap(new int[]{71, 71}, null), bytes);
    }

    @Override
    protected String solve1(Tuple<ObstacleMap, List<Point>> input) {
        var map = input.x;
        var bytes = input.y;
        return dijkstra(map, bytes, new Point(0, 0), new Point(70, 70)) + "";
    }

    @Override
    protected String solve2(Tuple<ObstacleMap, List<Point>> input) {
        var map = input.x;
        var bytes = input.y;
        int lb = 1024;
        int ub = bytes.size();
        int mid = (lb + ub) / 2;
        while (lb < ub) {
            map.clearObstacles();
            if (dijkstra2(map, bytes, new Point(0, 0), new Point(70, 70), mid)) {
                ub = mid;
            } else {
                lb = mid + 1;
            }
            mid = (lb + ub) / 2;
        }
        var ans = bytes.get(mid - 1);
        return ans.getX() + "," + ans.getY();
    }

    private int dijkstra(ObstacleMap map, List<Point> bytes, Point source, Point target) {
        Set<Point> visited = new HashSet<>();
        Map<Point, Integer> dist = new HashMap<>();
        Queue<Point> queue = new PriorityQueue<>(Comparator.comparingInt(dist::get));
        queue.add(source);
        dist.put(source, 0);
        bytes.subList(0, 1024).forEach(map::addObstacle);
        while (!queue.isEmpty()) {
            var current = queue.poll();
            visited.add(current);
            if (current.equals(target)) {
                return dist.get(current);
            }
            for (Point neigh : current.get4Neighbours(map, true)) {
                if (visited.contains(neigh)
                    || queue.contains(neigh)
                    || map.outOfBounds(neigh)
                    || map.hitObstacle(neigh)) {
                    continue;
                }
                dist.put(neigh, dist.get(current) + 1);
                queue.add(neigh);
            }
        }
        return -1;
    }

    private boolean dijkstra2(ObstacleMap map, List<Point> bytes, Point source, Point target, int fallCount) {
        Set<Point> visited = new HashSet<>();
        Map<Point, Integer> dist = new HashMap<>();
        Queue<Point> queue = new PriorityQueue<>(Comparator.comparingInt(dist::get));
        queue.add(source);
        dist.put(source, 0);
        bytes.subList(0, fallCount).forEach(map::addObstacle);
        while (!queue.isEmpty()) {
            var current = queue.poll();
            visited.add(current);
            if (current.equals(target)) {
                return false;
            }
            for (Point neigh : current.get4Neighbours(map, true)) {
                if (visited.contains(neigh)
                    || queue.contains(neigh)
                    || map.outOfBounds(neigh)
                    || map.hitObstacle(neigh)) {
                    continue;
                }
                dist.put(neigh, dist.get(current) + 1);
                queue.add(neigh);
            }
        }
        return true;
    }
}
