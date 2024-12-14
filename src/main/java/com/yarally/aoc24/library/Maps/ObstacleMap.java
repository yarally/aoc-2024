package com.yarally.aoc24.library.Maps;

import com.yarally.aoc24.library.Point;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ObstacleMap extends AbstractMap {
    private Set<Point> obstacles;
    private boolean infinite;

    public ObstacleMap(int[] bounds, Set<Point> obstacles, boolean infinite) {
        super(bounds);
        this.obstacles = obstacles != null ? obstacles : new HashSet<>();
        this.infinite = infinite;
    }

    public void setInfinite(boolean val) {
        this.infinite = val;
    }

    public boolean hitObstacle(Point pos) {
        return obstacles.contains(pos);
    }

    public void addObstacle(Point pos) {
        obstacles.add(pos);
    }

    @Override
    public String toString() {
        StringBuilder map = new StringBuilder();
        for (int row = 0; row < bounds[1]; row++) {
            for (int col = 0; col < bounds[0]; col++) {
                map.append(obstacles.contains(new Point(col, row)) ? '#' : '.');
            }
            map.append('\n');
        }
        return map.toString();
    }

    public ObstacleMap getClone() {
        return new ObstacleMap(bounds.clone(), obstacles.stream().map(Point::getClone).collect(Collectors.toSet()), infinite);
    }
}
