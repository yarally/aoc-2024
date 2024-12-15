package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;
import com.yarally.aoc24.library.Tuple.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day7 extends AbstractSolution<List<Tuple<Long, List<Long>>>> {
    @Override
    protected String getInput() {
        return "day7.txt";
    }

    @Override
    protected List<Tuple<Long, List<Long>>> parse(String input) {
        var lines = FileReader.readFile(input);
        List<Tuple<Long, List<Long>>> result = new ArrayList<>();
        for (String line : lines) {
            var values = Arrays.stream(line.split(": ")[1].split(" ")).mapToLong(Long::parseLong).boxed().toList();
            result.add(new Tuple<>(Long.parseLong(line.split(": ")[0]), values));
        }
        return result;
    }

    @Override
    protected String solve1(List<Tuple<Long, List<Long>>> input) {
        long count = 0L;
        for (var tuple : input) {
            var target = tuple.x;
            var values = tuple.y;
            count += trySolve(target, values.subList(1, values.size()), values.get(0)) ? target : 0;
        }
        return count + "";
    }

    @Override
    protected String solve2(List<Tuple<Long, List<Long>>> input) {
        long count = 0L;
        for (var tuple : input) {
            var target = tuple.x;
            var values = tuple.y;
            count += trySolve2(target, values.subList(1, values.size()), values.get(0)) ? target : 0;
        }
        return count + "";
    }

    private boolean trySolve(long target, List<Long> values, long acc) {
        if (values.isEmpty()) {
            return acc == target;
        }

        return trySolve(target, values.subList(1, values.size()), acc + values.get(0)) || trySolve(target, values.subList(1, values.size()), acc * values.get(0));
    }

    private boolean trySolve2(long target, List<Long> values, long acc) {
        if (values.isEmpty()) {
            return acc == target;
        }

        return trySolve2(target, values.subList(1, values.size()), acc + values.get(0))
                || trySolve2(target, values.subList(1, values.size()), acc * values.get(0))
                || trySolve2(target, values.subList(1, values.size()), Long.parseLong(acc + "" + values.get(0)));
    }
}
