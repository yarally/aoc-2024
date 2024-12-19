package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;
import com.yarally.aoc24.library.Tuple.Tuple;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day19 extends AbstractSolution<Tuple<List<String>, HashSet<String>>> {

    @Override
    protected String getInput() {
        return "day19.txt";
    }

    @Override
    protected Tuple<List<String>, HashSet<String>> parse(String input) {
        var lines = FileReader.readFile(input);
        var towels = Arrays.stream(lines.getFirst().split(", "))
            .collect(Collectors.toCollection(HashSet::new));
        var targets = lines.subList(2, lines.size());
        return new Tuple<>(targets, towels);
    }

    @Override
    protected String solve1(Tuple<List<String>, HashSet<String>> input) {
        var targets = input.x;
        var towels = input.y;
        var count = 0;
        var cache = new HashMap<String, Long>();
        for (var target : targets) {
            count += isValidPattern(target, new HashSet<>(towels), cache) > 0L ? 1 : 0;
        }
        return count + "";
    }

    @Override
    protected String solve2(Tuple<List<String>, HashSet<String>> input) {
        var targets = input.x;
        var towels = input.y;
        var count = 0L;
        var cache = new HashMap<String, Long>();
        for (var target : targets) {
            count += isValidPattern(target, new HashSet<>(towels), cache);
        }
        return count + "";
    }


    public long isValidPattern(String pattern, HashSet<String> valid, HashMap<String, Long> cache) {
        if (cache.containsKey(pattern)) {
            return cache.get(pattern);
        }

        if (pattern.isEmpty()) {
            cache.put(pattern, 1L);
            return 1L;
        }

        var count = 0L;
        for (var pat : valid) {
            if (pattern.startsWith(pat)) {
                count += isValidPattern(pattern.substring(pat.length()), valid, cache);
            }
        }
        cache.put(pattern, count);
        return count;
    }
}
