package com.yarally.aoc24.library;

import com.yarally.aoc24.library.Maps.AbstractMap;

import java.util.List;
import java.util.stream.Stream;

public class Point {

    private int x;
    private int y;

    public static Point UP = new Point(0, 1);
    public static Point DOWN = new Point(0, -1);
    public static Point LEFT = new Point(-1, 0);
    public static Point RIGHT = new Point(1, 0);

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point getUp() {
        return new Point(x, y + 1);
    }

    public Point getDown() {
        return new Point(x, y - 1);
    }

    public Point getLeft() {
        return new Point(x - 1, y);
    }

    public Point getright() {
        return new Point(x + 1, y);
    }

    public Point move(int xDist, int yDist) {
        x += xDist;
        y += yDist;
        return this;
    }

    public Point update(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Point dryMove(int xDist, int yDist) {
        return new Point(x + xDist, y + yDist);
    }

    public Point add(Point other) {
        return new Point(x + other.x, y + other.y);
    }

    public Point min(Point other) {
        return new Point(x - other.x, y - other.y);
    }

    public int manhattan(Point other) {
        return Math.abs(other.y - this.y) + Math.abs(other.x - this.x);
    }

    public Point getClone() {
        return new Point(x, y);
    }

    public List<Point> get4Neighbours(AbstractMap map, boolean inBound) {
        return Stream.of(
                new Point(x + 1, y),
                new Point(x, y - 1),
                new Point(x - 1, y),
                new Point(x, y + 1)
        ).filter(p -> !map.outOfBounds(p) || !inBound).toList();
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int hashCode() {
        return ("x=" + x + "y=" + y).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Point that)) {
            return false;
        }

        return this.x == that.x && this.y == that.y;
    }
}
