package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Day1 extends AbstractSolution<List<List<Integer>>> {
    @Override
    protected String getInput() {
        return "day1.txt";
    }

    @Override
    protected List<List<Integer>> parse(String input) {
        List<String> parsed = FileReader.readFile(input);
        var left = new ArrayList<Integer>();
        var right = new ArrayList<Integer>();
        for (String x : parsed) {
            left.add(Integer.parseInt(x.split("   ")[0]));
            right.add(Integer.parseInt(x.split("   ")[1]));
        }
        List<List<Integer>> result = new ArrayList<>();
        result.add(left);
        result.add(right);
        return result;
    }

    @Override
    protected String solve1(List<List<Integer>> input) {
        var left = input.get(0).stream().sorted().toList();
        var right = input.get(1).stream().sorted().toList();
        int diff = IntStream.range(0, left.size()).map(i -> Math.abs(left.get(i) - right.get(i))).sum();
        return String.valueOf(diff);
    }

    @Override
    protected String solve2(List<List<Integer>> input) {
        var left = input.get(0);
        var right = input.get(1);
        var score = left.stream().map(x -> x * Collections.frequency(right, x)).mapToInt(Integer::valueOf).sum();
        return String.valueOf(score);
    }
}
