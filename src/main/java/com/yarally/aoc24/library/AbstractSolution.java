package com.yarally.aoc24.library;

public abstract class AbstractSolution<T> {

    protected abstract String getInput();

    protected abstract T parse();

    protected abstract String solve(T input);

    private String GetDay() {
        var cleaned = getInput().split(".txt")[0];
        return cleaned.replaceAll("day(\\d+)", "Day $1");
    }

    public String SolvePuzzle() {
        var parsedInput = parse();
        var solution = solve(parsedInput);
        System.out.println(GetDay() + ": " +solution);
        return solution;
    }
}
