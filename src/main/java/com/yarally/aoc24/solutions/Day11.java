package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day11 extends AbstractSolution<List<Long>> {

    @Override
    protected String getInput() {
        return "day11.txt";
    }

    @Override
    protected List<Long> parse(String input) {
        return Arrays.stream(FileReader.readFile(input).getFirst().split(" "))
            .mapToLong(Long::parseLong).boxed().toList();
    }

    @Override
    protected String solve1(List<Long> input) {
        var stones = 0L;
        HashMap<String, Long> cache = new HashMap<>();
        for (Long v : input) {
            stones += blink(v, 0, 25, cache);
        }
        return stones + "";
    }

    @Override
    protected String solve2(List<Long> input) {
        var stones = 0L;
        HashMap<String, Long> cache = new HashMap<>();
        for (Long v : input) {
            stones += blink(v, 0, 75, cache);
        }
        return stones + "";
    }

    private Long blink(Long value, int depth, int maxDepth, HashMap<String, Long> cache) {
        var key = value + ":" + depth;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        Long res;
        if (depth == maxDepth) {
            res = 1L;
        } else if (value == 0L) {
            res = blink(1L, depth + 1, maxDepth, cache);
        } else if (String.valueOf(value).length() % 2 == 0) {
            var str = String.valueOf(value);
            res =
                blink(Long.valueOf(str.substring(0, str.length() / 2)), depth + 1, maxDepth, cache)
                    + blink(Long.valueOf(str.substring(str.length() / 2)), depth + 1, maxDepth,
                    cache);
        } else {
            res = blink(value * 2024L, depth + 1, maxDepth, cache);
        }
        cache.put(key, res);
        return res;
    }
}
