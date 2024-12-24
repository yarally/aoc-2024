package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;
import com.yarally.aoc24.library.Tuple.Tuple;

import java.util.*;
import java.util.stream.Collectors;

public class Day24 extends AbstractSolution<Tuple<HashMap<String, Boolean>, HashMap<String, Set<Day24.Gate>>>> {

    @Override
    protected String getInput() {
        return "day24.txt";
    }

    @Override
    protected Tuple<HashMap<String, Boolean>, HashMap<String, Set<Gate>>> parse(String input) {
        var lines = FileReader.readFile(input);
        HashMap<String, Boolean> initialWireValues = new HashMap<>();
        HashMap<String, Set<Gate>> wireMap = new HashMap<>();
        for (var line : lines) {
            if (line.contains(":")) {
                var split = line.split(": ");
                initialWireValues.put(split[0], split[1].equals("1"));
            }
            if (line.contains("->")) {
                var split = line.split(" -> ");
                var gateSplit = split[0].split(" ");
                Gate gate = switch (gateSplit[1]) {
                    case "AND" -> new AndGate(split[1], split[0]);
                    case "OR" -> new OrGate(split[1], split[0]);
                    case "XOR" -> new XOrGate(split[1], split[0]);
                    default -> throw new IllegalArgumentException("Invalid gate");
                };
                if (!wireMap.containsKey(gateSplit[0])) {
                    wireMap.put(gateSplit[0], new HashSet<>());
                }
                if (!wireMap.containsKey(gateSplit[2])) {
                    wireMap.put(gateSplit[2], new HashSet<>());
                }
                wireMap.get(gateSplit[0]).add(gate);
                wireMap.get(gateSplit[2]).add(gate);
            }
        }
        return new Tuple<>(initialWireValues, wireMap);
    }

    @Override
    protected String solve1(Tuple<HashMap<String, Boolean>, HashMap<String, Set<Gate>>> input) {
        var wireMap = input.y;
        var wireValues = simulate(cloneMap(input.x), wireMap);
        return getValue("z", wireValues) + "";
    }

    @Override
    protected String solve2(Tuple<HashMap<String, Boolean>, HashMap<String, Set<Gate>>> input) {
        var wireMap = input.y;
        var allGates = input.y.values().stream().flatMap(Set::stream).collect(Collectors.toSet()).stream().toList();
        allGates.forEach(Gate::reset);
        var gateMap = new HashMap<String, Gate>();
        var outMap = new HashMap<String, Gate>();
        for (Gate gate : allGates) {
            gateMap.put(gate.id, gate);
            outMap.put(gate.outputWire, gate);
        }
        List<String> swaps = new ArrayList<>();
        wLoop: while (true) {
            for (int bit=1; bit < 45; bit++) {
                String curX = "x" + String.format("%02d", bit);
                String curY = "y" + String.format("%02d", bit);
                String curZ = "z" + String.format("%02d", bit);
                var xor1 = gateMap.getOrDefault(curX + " XOR " + curY, gateMap.getOrDefault(curY + " XOR " + curX, null));
                var and1 = gateMap.getOrDefault(curX + " AND " + curY, gateMap.getOrDefault(curY + " AND " + curX, null));
                if (xor1 == null) {
                    System.out.println("XOR1: " + bit);
                }
                if (and1 == null) {
                    System.out.println("AND1: " + bit);
                }
                var xor2 = gateMap.getOrDefault(gateMap.keySet().stream().filter(k -> k.contains("XOR") && k.contains(xor1.outputWire)).findFirst().orElse(null), null);
                if (xor2 == null) {
                    var corrXor2 = outMap.get(curZ);
                    var in1 = corrXor2.id.split(" XOR ")[0];
                    var in2 = corrXor2.id.split(" XOR ")[1];
                    var ancestor1 = outMap.get(in1);
                    var ancestor2 = outMap.get(in2);
                    if (!ancestor1.id.contains(" OR ") && !ancestor1.id.contains(" XOR ")) {
                        //invalid ancestor, so swap
                        swaps.add(xor1.outputWire);
                        swaps.add(in1);
                        xor1.swap(ancestor1);
                        continue wLoop;
                    }
                    if (!ancestor2.id.contains(" OR ") && !ancestor2.id.contains(" XOR ")) {
                        //invalid ancestor, so swap
                        swaps.add(xor1.outputWire);
                        swaps.add(in2);
                        xor1.swap(ancestor2);
                        continue wLoop;
                    }

                } else if (!xor2.outputWire.equals(curZ)){
                    swaps.add(xor2.outputWire);
                    swaps.add(curZ);
                    xor2.swap(outMap.get(curZ));
                    continue wLoop;
                }
            }
            break;
        }

        return String.join(",", swaps.stream().sorted().toList());
    }

    private HashMap<String, Boolean> simulate(HashMap<String, Boolean> wireValues, HashMap<String, Set<Gate>> wireMap) {
        var todo = new LinkedList<>(wireValues.keySet());
        var visited = new ArrayList<String>();
        while (!todo.isEmpty()) {
            var current = todo.pop();
            if (visited.contains(current)) {
                return wireValues;
            }
            visited.add(current);
            var gates = wireMap.get(current);
            for (var gate : gates) {
                var outReady = gate.updateInput(current, wireValues.get(current));
                if (outReady) {
                    wireValues.put(gate.outputWire, gate.getOutput());
                    if (!gate.outputWire.startsWith("z")) {
                        todo.push(gate.outputWire);
                    }
                }
            }
        }
        return wireValues;
    }

    private HashMap<String, Boolean> cloneMap(HashMap<String, Boolean> wireValues) {
        var clone = new HashMap<String, Boolean>();
        for (var entry : wireValues.entrySet()) {
            clone.put(String.valueOf(entry.getKey()), entry.getValue());
        }
        return clone;
    }

    private long getValue(String wirePrefix, HashMap<String, Boolean> wireValues) {
        return Long.parseLong(getBinary(wirePrefix, wireValues), 2);
    }

    private String getBinary(String wirePrefix, HashMap<String, Boolean> wireValues) {
        List<String> sortedValues = wireValues.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(wirePrefix))
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getValue() ? "1" : "0")
                .toList();
        return String.join("", sortedValues.reversed());
    }


    protected abstract static class Gate {
        public String outputWire;
        public final String id;
        protected HashMap<String, Boolean> input;
        protected String initialOutWire;

        public Gate(String outputWire, String id) {
            this.input = new HashMap<>();
            this.outputWire = outputWire;
            this.initialOutWire = outputWire;
            this.id = id;
        }

        public boolean updateInput(String wire, boolean value) {
            input.put(wire, value);
            return input.size() == 2;
        }

        public void swap(Gate other) {
            var temp = this.outputWire;
            this.outputWire = other.outputWire;
            other.outputWire = temp;
        }

        public void reset() {
            this.outputWire = initialOutWire;
            this.input = new HashMap<>();
        }

        public abstract boolean getOutput();

        @Override
        public String toString() {
            return id;
        }
    }

    private static class AndGate extends Gate {

        public AndGate(String outputWire, String id) {
            super(outputWire, id);
        }

        @Override
        public boolean getOutput() {
            return input.values().stream().allMatch(v -> v);
        }
    }

    private static class OrGate extends Gate {

        public OrGate(String outputWire, String id) {
            super(outputWire, id);
        }

        @Override
        public boolean getOutput() {
            return input.values().stream().anyMatch(v -> v);
        }
    }

    private static class XOrGate extends Gate {
        public XOrGate(String outputWire, String id) {
            super(outputWire, id);
        }

        @Override
        public boolean getOutput() {
            var values = input.values().stream().toList();
            return values.getFirst() != values.getLast();
        }
    }

}
