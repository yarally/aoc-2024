package com.yarally.aoc24.library.Graphs;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Node {
    public final String id;
    public final Set<Node> neighbours;

    public Node(String id) {
        this.id = id;
        this.neighbours = new HashSet<>();
    }

    public void addNeighbour(Node neighbour) {
        this.neighbours.add(neighbour);
    }

    public void pop() {
        this.neighbours.forEach(neigh -> neigh.neighbours.remove(this));
    }

    public int degree() {
        return neighbours.size();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(id, node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        var neighbours = this.neighbours.stream().map(n -> n.id).toList();
        return id + ": " + neighbours;
    }
}
