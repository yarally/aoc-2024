package com.yarally.aoc24.library.Maps;

import com.yarally.aoc24.library.Point;

public class RichMap<T> extends AbstractMap {

    private final T[][] values;

    public RichMap(T[][] values) {
        super(new int[]{values[0].length, values.length});
        this.values = values;
    }

    public T[][] getValues() {
        return values;
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

    public void setValue(int x, int y, T value) {
        if (outOfBounds(x, y)) {
            System.out.println("Out of bounds set!");
            return;
        }
        values[y][x] = value;
    }

    public void setValue(Point point, T value) {
        setValue(point.getX(), point.getY(), value);
    }

    public void Move(Point source, Point dest) {
        setValue(dest, getValue(source));
    }

    public String revToString() {
        StringBuilder map = new StringBuilder();
        for (int row = bounds[1] - 1; row >= 0; row--) {
            for (int col = 0; col < bounds[0]; col++) {
                map.append(values[row][col]);
            }
            map.append('\n');
        }
        return map.toString();
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
