package com.yarally.aoc24.library.Maps;

import com.yarally.aoc24.library.Point;

public class RichMap<T> extends AbstractMap {

    private final T[][] values;
    public RichMap(T[][] values) {
        super(new int[] {values[0].length, values.length});
        this.values = values;
    }

    public T getValue(int x, int y) {
        if (outOfBounds(x, y)) {
            return null;
        }
        return values[y][x];
    }

    public T getValue(Point point) {
        return getValue(point.getX(), point.getY());
    }

    @Override
    public String toString() {
        StringBuilder map = new StringBuilder();
        for (int row = 0; row < bounds[1]; row++) {
            for (int col = 0; col < bounds[0]; col++) {
                map.append(values[row][col]);
            }
            map.append('\n');
        }
        return map.toString();
    }
}
