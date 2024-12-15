package com.yarally.aoc24.library.Tuple;

import java.util.Objects;

public class Tuple<X, Y> {
    public final X x;
    public final Y y;

    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return ("x=" + x + "y=" + y).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equals(x, tuple.x) && Objects.equals(y, tuple.y);
    }
}
