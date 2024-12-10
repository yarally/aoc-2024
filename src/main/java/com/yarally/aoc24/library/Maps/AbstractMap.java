package com.yarally.aoc24.library.Maps;

import com.yarally.aoc24.library.Point;

public abstract class AbstractMap {

    public final int[] bounds;

    public AbstractMap(int[] bounds) {
        this.bounds = bounds;
    }

    public boolean outOfBounds(Point pos) {
        return pos.getX() < 0 || pos.getX() > bounds[0] - 1 || pos.getY() < 0 || pos.getY() > bounds[1] - 1;
    }

    public boolean outOfBounds(int x, int y) {
        return x < 0 || x > bounds[0] - 1 || y < 0 || y > bounds[1] - 1;
    }
}
