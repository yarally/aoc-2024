package com.yarally.aoc24.library.Tuple;

import java.util.Objects;

public class Tuple3<X, Y, Z> {
    public final X x;
    public final Y y;
    public final Z z;
    public Tuple3(X x, Y y, Z z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public int hashCode() {
        return ("x=" + x + "y=" + y + "z=" + z).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tuple3<?, ?, ?> tuple = (Tuple3<?, ?, ?>) o;
        return Objects.equals(x, tuple.x) && Objects.equals(y, tuple.y) && Objects.equals(z, tuple.z);
    }
}