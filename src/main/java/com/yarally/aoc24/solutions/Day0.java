package com.yarally.aoc24.solutions;


import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;

import java.util.List;

public class Day0 extends AbstractSolution<List<String>> {

    @Override
    protected String getInput() {
        return "day0.txt";
    }

    @Override
    protected List<String> parse(String input) {
        return FileReader.readFile(input);
    }

    @Override
    protected String solve1(List<String> input) {
        return String.join(" ", input);
    }

    @Override
    protected String solve2(List<String> input) {
        return String.join(" ", input);
    }
}
