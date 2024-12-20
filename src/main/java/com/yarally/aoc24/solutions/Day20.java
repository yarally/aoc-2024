package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;
import com.yarally.aoc24.library.Maps.ObstacleMap;
import com.yarally.aoc24.library.Point;
import com.yarally.aoc24.library.Tuple.Tuple;

import java.util.*;

public class Day20 extends AbstractSolution<Tuple<ObstacleMap, Point[]>> {
    @Override
    protected String getInput() {
        return "day20.txt";
    }

    @Override
    protected Tuple<ObstacleMap, Point[]> parse(String input) {
        var lines = FileReader.readFile(input);
        var walls = new HashSet<Point>();
        var startEnd = new Point[2];
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                char[] chars = lines.get(y).toCharArray();
                if (chars[x] == '#') {
                    walls.add(new Point(x, y));
                }
                if (chars[x] == 'S') {
                    startEnd[0] = new Point(x, y);
                }
                if (chars[x] == 'E') {
                    startEnd[1] = new Point(x, y);
                }
            }
        }
        ObstacleMap map = new ObstacleMap(new int[]{lines.get(0).length(), lines.size()}, walls);
        return new Tuple<>(map, startEnd);
    }

    @Override
    protected String solve1(Tuple<ObstacleMap, Point[]> input) {
        var map = input.x;
        var src = input.y[0];
        var dest = input.y[1];
        var counts = raceWithCheats(map, src, dest, 2);
        return counts.entrySet().stream().map(entry -> entry.getKey() >= 100 ? entry.getValue() : 0).mapToInt(i -> i).sum() + "";
    }

    @Override
    protected String solve2(Tuple<ObstacleMap, Point[]> input) {
        var map = input.x;
        var src = input.y[0];
        var dest = input.y[1];
        var counts = raceWithCheats(map, src, dest, 20);
        return counts.entrySet().stream().map(entry -> entry.getKey() >= 100 ? entry.getValue() : 0).mapToInt(i -> i).sum() + "";
    }

    private Map<Integer, Integer> raceWithCheats(ObstacleMap map, Point src, Point dest, int cheatTime) {
        var distances = dijkstra(map, src);
        var counts = new HashMap<Integer, Integer>();
        for (int y = 0; y < map.bounds[1]; y++) {
            for (int x = 0; x < map.bounds[0]; x++) {
                var current = new Point(x, y);
                if (!map.isFree(current) || !distances.containsKey(current)) continue;
                var toStart = distances.get(current);
                if (toStart > distances.get(dest)) continue;
                for (int dx = -cheatTime; dx <= cheatTime; dx++) {
                    for (int dy = -cheatTime; dy <= cheatTime; dy++) {
                        var cheatDest = current.add(new Point(dx, dy));
                        var manhattan = Math.abs(dx) + Math.abs(dy);
                        if (manhattan > cheatTime || !map.isFree(cheatDest) || !distances.containsKey(cheatDest)) continue;
                        var toEnd = distances.get(dest) - distances.get(cheatDest);
                        var save = (int) (distances.get(dest) - (toStart + manhattan + toEnd));
                        if (save > 0) {
                            counts.put(save, counts.getOrDefault(save, 0) + 1);
                        }
                    }
                }
            }
        }
        return counts;
    }

    private Map<Point, Long> dijkstra(ObstacleMap map, Point src) {
        Set<Point> visited = new HashSet<>();
        Map<Point, Long> dist = new HashMap<>();
        Queue<Point> todo = new PriorityQueue<>(Comparator.comparingLong(dist::get));
        dist.put(src, 0L);
        todo.add(src);
        while (!todo.isEmpty()) {
            var current = todo.poll();
            visited.add(current);
            for (var neigh : current.get4Neighbours(map, true)) {
                if (!visited.contains(neigh) && !todo.contains(neigh)) {
                    if (!dist.containsKey(neigh)) {
                        dist.put(neigh, dist.get(current) + 1);
                    }
                    if (map.isFree(neigh)) {
                        todo.add(neigh);
                    }
                }
            }
        }
        return dist;
    }
}
