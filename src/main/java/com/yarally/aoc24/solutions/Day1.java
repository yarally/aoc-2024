package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;

import java.util.List;

public class Day1 extends AbstractSolution<List<String>> {
    @Override
    protected String getInput() {
        return "day1.txt";
    }

    @Override
    protected List<String> parse(String input) {
        return FileReader.readFile(input);
    }

    @Override
    protected String solve(List<String> input) {
        return input.getFirst();
    }
}
