package com.yarally.aoc24;


import com.yarally.aoc24.solutions.*;

public class App {
    public static void main(String[] args) {
        var t0 = System.nanoTime();
        new Day0().SolvePuzzle();
        new Day1().SolvePuzzle();
        new Day2().SolvePuzzle();
        new Day3().SolvePuzzle();
        new Day4().SolvePuzzle();
        new Day5().SolvePuzzle();
        new Day6().SolvePuzzle();
        new Day7().SolvePuzzle();
        new Day8().SolvePuzzle();
        new Day9().SolvePuzzle();
        new Day10().SolvePuzzle();
        new Day11().SolvePuzzle();
        new Day12().SolvePuzzle();
        new Day13().SolvePuzzle();
        new Day14().SolvePuzzle();
        new Day15().SolvePuzzle();
        new Day16().SolvePuzzle();
        new Day17().SolvePuzzle();
        new Day18().SolvePuzzle();
        new Day19().SolvePuzzle();
        new Day20().SolvePuzzle();
        new Day21().SolvePuzzle();
        new Day22().SolvePuzzle();
        new Day23().SolvePuzzle();
        new Day24().SolvePuzzle();
        new Day25().SolvePuzzle();
        var duration = System.nanoTime() - t0;
        System.out.println("TOTAL DURATION AOC 2024: " + duration / Math.pow(10, 9) + "s");
    }
}