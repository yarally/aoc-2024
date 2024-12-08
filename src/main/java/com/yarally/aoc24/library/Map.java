package com.yarally.aoc24.library;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Map {
    public final int[] bounds;
    private Set<Point> obstacles;
    private boolean infinite;

    public Map(int[] bounds, Set<Point> obstacles, boolean infinite) {
        this.bounds = bounds;
        this.obstacles = obstacles != null ? obstacles : new HashSet<>();
        this.infinite = infinite;
    }

    public void setInfinite(boolean val) {
        this.infinite = val;
    }

    public boolean outOfBounds(Point pos) {
        return pos.getX() < 0 || pos.getX() > bounds[0] - 1 || pos.getY() < 0 || pos.getY() > bounds[1] - 1;
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
        for (int row = bounds[1] - 1; row >=0 ; row--) {
            for (int col = 0; col < bounds[0]; col++) {
                map.append(obstacles.contains(new Point(col, row)) ? '#' : '.');
            }
            map.append('\n');
        }
        return map.toString();
    }

    public Map getClone() {
        return new Map(bounds.clone(), obstacles.stream().map(Point::getClone).collect(Collectors.toSet()), infinite);
    }
}
