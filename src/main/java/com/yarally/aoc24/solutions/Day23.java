package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;
import com.yarally.aoc24.library.Graphs.Graph;
import com.yarally.aoc24.library.Graphs.Node;
import com.yarally.aoc24.library.Tuple.Tuple;

import java.util.*;
import java.util.stream.Collectors;

public class Day23 extends AbstractSolution<Graph> {

    @Override
    protected String getInput() {
        return "day23.txt";
    }

    @Override
    protected Graph parse(String input) {
        var lines = FileReader.readFile(input);
        Graph g = new Graph();
        for (var line : lines) {
            var split = line.split("-");
            var leftId = split[0];
            var rightId = split[1];
            Node left = g.getNode(leftId) == null ? new Node(leftId) : g.getNode(leftId);
            Node right = g.getNode(rightId) == null ? new Node(rightId) : g.getNode(rightId);
            left.addNeighbour(right);
            right.addNeighbour(left);
            g.put(left, right);
            g.put(new Tuple<>(left, right));
        }
        return g;
    }

    @Override
    protected String solve1(Graph input) {
        HashSet<HashSet<Node>> cliques = new HashSet<>();
        for (var edge: input.edges) {
            cliques.add(new HashSet<>(List.of(edge.x, edge.y)));
        }
        for (int i = 2; i < 3; i++) {
            HashSet<HashSet<Node>> nextCliques = new HashSet<>();
            for (var clique : cliques) {
                Set<Node> intersection = null;
                for (var n : clique) {
                    if (intersection == null) {
                        intersection =  new HashSet<>(n.neighbours);
                        continue;
                    }
                    intersection.retainAll(n.neighbours);
                }

                for (Node commonNeighbour : intersection) {
                    var nextClq = new HashSet<>(clique);
                    nextClq.add(commonNeighbour);
                    nextCliques.add(nextClq);
                }
            }
            cliques = nextCliques;
        }
        return cliques.stream().filter(clq -> clq.stream().anyMatch(n -> n.id.startsWith("t"))).count() + "";
    }

    @Override
    protected String solve2(Graph input) {
        HashSet<HashSet<Node>> cliques = new HashSet<>();
        for (var edge: input.edges) {
            cliques.add(new HashSet<>(List.of(edge.x, edge.y)));
        }
        while (cliques.size() > 1) {
            HashSet<HashSet<Node>> nextCliques = new HashSet<>();
            for (var clique : cliques) {
                Set<Node> intersection = null;
                for (var n : clique) {
                    if (intersection == null) {
                        intersection =  new HashSet<>(n.neighbours);
                        continue;
                    }
                    intersection.retainAll(n.neighbours);
                }

                for (Node commonNeighbour : intersection) {
                    var nextClq = new HashSet<>(clique);
                    nextClq.add(commonNeighbour);
                    nextCliques.add(nextClq);
                }
            }
            cliques = nextCliques;
        }
        var maxClique = cliques.stream().toList().get(0);
        var ids = maxClique.stream().map(n -> n.id).sorted(Comparator.naturalOrder()).toList();
        return String.join(",", ids);
    }
}
