package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;
import com.yarally.aoc24.solutions.Day2.Report;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2 extends AbstractSolution<List<Report>> {

    @Override
    protected String getInput() {
        return "day2.txt";
    }

    @Override
    protected List<Report> parse(String input) {
        List<String> lines = FileReader.readFile(input);
        assert lines != null;
        return lines.stream().map(line -> new Report(
            Arrays.stream(line.split(" ")).map(Integer::valueOf).toList())).toList();
    }

    @Override
    protected String solve1(List<Report> input) {
        return input.stream().filter(Report::isSafe).count() + "";
    }

    @Override
    protected String solve2(List<Report> input) {
        int count = 0;
        for (Report report : input) {
            count += report.GetVariations().stream().anyMatch(Report::isSafe) ? 1 : 0;
        }
        return count + "";
    }

    protected static class Report {
        private List<Integer> levels;

        public Report(List<Integer> levels) { this.levels = levels; }

        public boolean isSafe() {
            boolean increasing = false;
            boolean decreasing = false;

            int prev = levels.getFirst();
            for (int i = 1; i < this.levels.size(); i++) {
                var diff = Math.abs(levels.get(i) - prev);
                if (levels.get(i) > prev) {increasing = true;}
                if (levels.get(i) < prev) {decreasing = true;}
                if ((increasing && decreasing) || diff == 0 || diff > 3) {
                    return false;
                }
                prev = levels.get(i);
            }
            return true;
        }

        public List<Report> GetVariations() {
            List<Report> result = new ArrayList<>();
            for (int i = 0; i < levels.size(); i++) {
                List<Integer> newLevels = new ArrayList<>();
                for (int j = 0; j < levels.size(); j++) {
                    if (i == j) { continue; }
                    newLevels.add(levels.get(j));
                }
                result.add(new Report(newLevels));
            }
            return result;
        }
    }

}
