package com.yarally.aoc24.library;

public class Point {
    private int x;
    private int y;

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

    public Point move(int xDist, int yDist) {
        x += xDist;
        y += yDist;
        return this;
    }

    public Point dryMove(int xDist, int yDist) {
        return new Point(x + xDist, y + yDist);
    }

    public Point add(Point other) {
        return new Point(x + other.x, y + other.y);
    }

    public Point getClone() {
        return new Point(x, y);
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
        if (this == obj) return true;
        if (!(obj instanceof Point that)) return false;

        return this.x == that.x && this.y == that.y;
    }
}
