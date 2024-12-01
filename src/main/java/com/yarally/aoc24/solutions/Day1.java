package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        for (String x: parsed) {
            left.add(Integer.parseInt(x.split("   ")[0]));
            right.add(Integer.parseInt(x.split("   ")[1]));
        }
        List<List<Integer>> result = new ArrayList<>();
        result.add(left);
        result.add(right);
        return result;
    }

    @Override
    protected String solve(List<List<Integer>> input) {
//        var left = input.get(0).stream().sorted().toList();
//        var right = input.get(1).stream().sorted().toList();
//        int diff = 0;
//        for (int i = 0; i < left.size(); i++) {
//            diff += Math.abs(left.get(i) - right.get(i));
//        }
//        return String.valueOf(diff);
        var left = input.get(0);
        var right = input.get(1);
        var score = 0;
        for (int i = 0; i < left.size(); i++) {
            int finalI = i;
            score += left.get(i) * (int) right.stream().filter(x -> Objects.equals(x, left.get(finalI))).count();
        }
        return String.valueOf(score);
    }
}
