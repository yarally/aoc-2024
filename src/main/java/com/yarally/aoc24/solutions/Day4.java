package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Day4 extends AbstractSolution<char[][]> {

    @Override
    protected String getInput() {
        return "day4.txt";
    }

    @Override
    protected char[][] parse(String input) {
        return FileReader.readFile(input).stream().map(String::toCharArray).toArray(char[][]::new);
    }

    @Override
    protected String solve1(char[][] input) {
        // Horizontal
        ArrayList<String> permutations = new ArrayList<>(FileReader.readFile(getInput()));

        // Vertical
        for (int col = 0; col < input[0].length; col++) {
            StringBuilder line = new StringBuilder();
            for (int row = 0; row < input.length; row++) {
                line.append(input[row][col]);
            }
            permutations.add(line.toString());
        }

        // Diagonal 1
        for (int i = 0; i < input.length; i++) {
            StringBuilder line1 = new StringBuilder();
            StringBuilder line2 = new StringBuilder();
            for (int row = 0; row < input.length; row++) {
                if (row + i < input.length) {
                    line1.append(input[row][row + i]);
                }
                if (i == 0) {
                    continue;
                }
                if (row - i >= 0) {
                    line2.append(input[row][row - i]);
                }
            }
            if (line1.length() >= 4) {
                permutations.add(line1.toString());

            }
            if (line2.length() >= 4) {
                permutations.add(line2.toString());
            }
        }

        // Diagonal 2
        for (int i = 0; i < input.length; i++) {
            StringBuilder line1 = new StringBuilder();
            StringBuilder line2 = new StringBuilder();
            for (int row = input.length - 1; row >= 0; row--) {
                if (i + input.length - 1 - row < input.length) {
                    line1.append(input[row][i + input.length - 1 - row]);
                }
                if (i == 0) {
                    continue;
                }
                if (row - i >= 0) {
                    line2.append(input[row - i][input.length - 1 - row]);
                }
            }
            if (line1.length() >= 4) {
                permutations.add(line1.toString());

            }
            if (line2.length() >= 4) {
                permutations.add(line2.toString());
            }
        }
        var regex = "(?=(XMAS|SAMX))";
        Pattern p = Pattern.compile(regex);
        return
            permutations.stream().map(s -> p.matcher(s).results().count()).mapToInt(Long::intValue)
                .sum() + "";
    }

    @Override
    protected String solve2(char[][] input) {
        int count = 0;
        for (int row = 0; row < input.length; row++) {
            for (int col = 0; col < input.length; col++) {
                if (row == 0 || row == input.length - 1 || col == 0 || col == input.length - 1) {
                    continue;
                }
                if (input[row][col] == 'A'
                    && (
                    (input[row + 1][col - 1] == 'M' && input[row - 1][col + 1] == 'S')
                        || (input[row + 1][col - 1] == 'S' && input[row - 1][col + 1] == 'M')
                ) && ((input[row - 1][col - 1] == 'M' && input[row + 1][col + 1] == 'S')
                    || (input[row - 1][col - 1] == 'S' && input[row + 1][col + 1] == 'M'))
                ) {
                    count++;
                }
            }
        }
        return count + "";
    }
}

