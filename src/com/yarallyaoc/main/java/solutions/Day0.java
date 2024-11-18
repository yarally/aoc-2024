package com.yarallyaoc.main.java.solutions;

import com.yarallyaoc.main.java.library.AbstractSolution;
import com.yarallyaoc.main.java.library.FileReader;

import java.util.List;

public class Day0 extends AbstractSolution<List<String>> {

    @Override
    protected String getInput() {
        return "day0.txt";
    }

    @Override
    protected List<String> parse() {
        return FileReader.readFile(getInput());
    }

    @Override
    protected String solve(List<String> input) {
        return String.join(" ", input);
    }
}
