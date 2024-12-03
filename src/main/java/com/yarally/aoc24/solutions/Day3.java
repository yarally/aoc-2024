package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 extends AbstractSolution<String> {

    @Override
    protected String getInput() {
        return "day3.txt";
    }

    @Override
    protected String parse(String input) {
        return String.join("", FileReader.readFile(input));
    }

    @Override
    protected String solve1(String input) {
        var regex = "mul\\([0-9]{1,3},[0-9]{1,3}\\)";
        Matcher m = Pattern.compile(regex).matcher(input);
        List<Operation> operations = new ArrayList<>();
        while (m.find()) {
            var group = m.group();
            operations.add(new Operation(group));
        }
        return operations.stream().map(Operation::compute).mapToInt(Integer::intValue).sum() + "";
    }

    @Override
    protected String solve2(String input) {
        var regex = "mul\\([0-9]{1,3},[0-9]{1,3}\\)|do\\(\\)|don't\\(\\)";
        Matcher m = Pattern.compile(regex).matcher(input);
        boolean take = true;
        List<Operation> operations = new ArrayList<>();
        while (m.find()) {
            var group = m.group();
            if (take && group.startsWith("mul")) {
                operations.add(new Operation(group));
            } else if (group.startsWith("don't")) {
                take = false;
            } else if (group.startsWith("do")) {
                take = true;
            }
        }
        return operations.stream().map(Operation::compute).mapToInt(Integer::intValue).sum() + "";
    }

    protected class Operation {

        private String operator;
        private int left;
        private int right;

        public Operation(String operator, int left, int right) {
            this.operator = operator;
            this.left = left;
            this.right = right;
        }

        public Operation(String expression) {
            expression = expression.replace('(', ',').substring(0, expression.length() - 1);
            var splitted = expression.split(",");
            this.operator = splitted[0];
            this.left = Integer.parseInt(splitted[1]);
            this.right = Integer.parseInt(splitted[2]);
        }

        public int compute() {
            switch (operator) {
                case "mul":
                    return left * right;
                default:
                    throw new RuntimeException("Unknown operator: " + operator);
            }
        }

        @Override
        public String toString() {
            return "Operation{" +
                "operator='" + operator + '\'' +
                ", left=" + left +
                ", right=" + right +
                '}';
        }
    }
}
