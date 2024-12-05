package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.ArrayUtils;
import com.yarally.aoc24.library.FileReader;
import com.yarally.aoc24.solutions.Day5.Manual;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 extends AbstractSolution<Manual> {

    @Override
    protected String getInput() {
        return "day5.txt";
    }

    @Override
    protected Manual parse(String input) {
        List<String> lines = FileReader.readFile(input);
        List<Rule> rules = new ArrayList<>();
        List<int[]> updates = new ArrayList<>();
        for (String line : lines) {
            if (line.contains("|")) {
                rules.add(new Rule(line.split("\\|")));
            }
            if (line.contains(",")) {
                updates.add(Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray());
            }
        }
        return new Manual(rules, updates);
    }

    @Override
    protected String solve1(Manual manual) {
        int sum = 0;
        for (int[] update : manual.updates) {
            if (Arrays.stream(update).allMatch(manual::test)) {
                sum += update[update.length / 2];
            }
            manual.reset();
        }
        return sum + "";
    }

    @Override
    protected String solve2(Manual manual) {
        int sum = 0;
        for (int[] update : manual.updates) {
            if (Arrays.stream(update).anyMatch(val -> !manual.test(val))) {
                sum += manual.fixUpdate(update)[update.length / 2];
            }
            manual.reset();
        }
        return sum + "";
    }

    protected static class Manual {

        public List<Rule> rules;
        public List<int[]> updates;
        private Rule badRule = null;

        public Manual(List<Rule> rules, List<int[]> updates) {
            this.rules = rules;
            this.updates = updates;
        }

        public boolean test(int val) {
            for (Rule rule : rules) {
                if (!rule.test(val)) {
                    badRule = rule;
                    return false;
                }
            }
            return true;
        }

        public int[] fixUpdate(int[] update) {
            int[] newUpdate = update.clone();
            for (int i = 0; i < newUpdate.length; i++) {
                if (!test(newUpdate[i])) {
                    int index = ArrayUtils.indexOf(newUpdate, badRule.second);
                    newUpdate[index] = badRule.first;
                    newUpdate[i] = badRule.second;
                    reset();
                    return fixUpdate(newUpdate);
                }
            }
            return newUpdate;
        }

        public void reset() {
            for (Rule rule : rules) {
                rule.hasSecond = false;
            }
            badRule = null;
        }

        @Override
        public String toString() {
            return "Manual{" +
                "rules=" + rules +
                ", updates=" + updates +
                '}';
        }
    }

    protected static class Rule {

        private final int first;
        private final int second;
        private boolean hasSecond = false;

        public Rule(String[] input) {
            this.first = Integer.parseInt(input[0]);
            this.second = Integer.parseInt(input[1]);
        }

        public boolean test(int number) {
            if (number != first && number != second) {
                return true;
            }
            if (number == first && !hasSecond) {
                return true;
            }
            if (number == second) {
                hasSecond = true;
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return "Rule{" +
                "first=" + first +
                ", second=" + second +
                '}';
        }
    }

}
