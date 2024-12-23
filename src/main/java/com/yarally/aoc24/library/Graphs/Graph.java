package com.yarally.aoc24.library.Graphs;

import com.yarally.aoc24.library.Tuple.Tuple;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Graph {
    public final Set<Node> nodes;

    public final Set<Tuple<Node, Node>> edges;

    public HashMap<String, Node> nodeMap;

    public Graph(Set<Node> nodes, Set<Tuple<Node, Node>> edges) {
        this.nodes = nodes;
        this.edges = edges;
        this.nodeMap = new HashMap<>();
        this.nodes.forEach(n -> nodeMap.put(n.id, n));
    }

    public Graph() {
        this.nodes = new HashSet<>();
        this.edges = new HashSet<>();
        this.nodeMap = new HashMap<>();
    }

    public void put(Node... nodes) {
        for (Node n : nodes) {
            this.nodes.add(n);
            this.nodeMap.put(n.id, n);
        }
    }

    public void put(Tuple<Node, Node> e) {
        edges.add(e);
    }

    public Node getNode(String id) {
        return nodeMap.getOrDefault(id, null);
    }

    @Override
    public String toString() {
        return "Graph{" +
                "nodes=" + nodes +
                '}';
    }
}
