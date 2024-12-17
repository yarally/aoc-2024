package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day17 extends AbstractSolution<List<Long>> {
    @Override
    protected String getInput() {
        return "day17.txt";
    }

    @Override
    protected List<Long> parse(String input) {
        List<Long> program = new ArrayList<>();
        var lines = FileReader.readFile(input);
        for (var line : lines) {
            if (line.startsWith("Register")) {
                program.add((long) Integer.parseInt(line.split(": ")[1]));
            }
            if (line.startsWith("Program")) {
                program.addAll(Arrays.stream(line.split(": ")[1].split(",")).mapToLong(Long::valueOf).boxed().toList());
            }
        }
        return program;
    }

    @Override
    protected String solve1(List<Long> input) {
        Computer cpu = new Computer(input);
        return cpu.execute().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    @Override
    protected String solve2(List<Long> input) {
        Computer cpu = new Computer(input);
        for (long i = 11837872637L; i < 10000000000000000L; i += 8589934592L) {
            var output = cpu.execute2(i);
            if (output.size() >= 16
                    && output.get(0) == 2 && output.get(1) == 4
                    && output.get(2) == 1 && output.get(3) == 3
                    && output.get(4) == 7 && output.get(5) == 5
                    && output.get(6) == 4 && output.get(7) == 0
                    && output.get(8) == 1 && output.get(9) == 3
                    && output.get(10) == 0 && output.get(11) == 3
                    && output.get(12) == 5 && output.get(13) == 5
                    && output.get(14) == 3 && output.get(15) == 0
            ) {
                return i + "";
            }
        }
        return "404 Program not found";
    }

    private class Computer {
        // 2,4      1,3     7,5     4,0     1,3     0,3     5,5     3,0
        // B <- A
        // B <- B ^ 3 (= A ^ 3)
        // C <- A // 2**B
        // B <- B ^ C
        // B <- B ^ 3
        // A <- A // 8
        // OUT B % 8
        // JUMP 0
        private int pointer;
        private long A;
        private long B;
        private long C;
        private long[] instructions;

        private List<Long> output;

        public Computer(List<Long> input) {
            A = input.get(0);
            B = input.get(1);
            C = input.get(2);
            instructions = input.subList(3, input.size()).stream().mapToLong(l -> l).toArray();
        }

        public List<Long> execute() {
            output = new ArrayList<>();
            while (pointer < instructions.length) {
                switch ((int) instructions[pointer]) {
                    case 0:
                        adv();
                        break;
                    case 1:
                        bxl();
                        break;
                    case 2:
                        bst();
                        break;
                    case 3:
                        jnz();
                        break;
                    case 4:
                        bxc();
                        break;
                    case 5:
                        out();
                        break;
                    case 6:
                        bdv();
                        break;
                    case 7:
                        cdv();
                        break;
                }
            }
            return output;
        }

        public List<Long> execute2(long A_) {
            A = A_;
            B = 0L;
            C = 0L;
            pointer = 0;
            output = new ArrayList<>();
            while (pointer < instructions.length) {
                switch ((int) instructions[pointer]) {
                    case 0:
                        adv();
                        break;
                    case 1:
                        bxl();
                        break;
                    case 2:
                        bst();
                        break;
                    case 3:
                        jnz();
                        break;
                    case 4:
                        bxc();
                        break;
                    case 5:
                        out();
                        break;
                    case 6:
                        bdv();
                        break;
                    case 7:
                        cdv();
                        break;
                }
            }
            return output;
        }

        private long combo(long i) {
            return switch ((int) i) {
                case 0, 1, 2, 3 -> i;
                case 4 -> A;
                case 5 -> B;
                case 6 -> C;
                default -> throw new IndexOutOfBoundsException("Invalid program");
            };
        }

        // 0
        private void adv() {
            A = A / (long) Math.pow(2, combo(instructions[pointer + 1]));
            pointer += 2;
        }

        // 1
        private void bxl() {
            B = B ^ instructions[pointer + 1];
            pointer += 2;
        }

        // 2
        private void bst() {
            B = combo(instructions[pointer + 1]) % 8;
            pointer += 2;
        }

        // 3
        private void jnz() {
            if (A == 0) {
                pointer += 2;
            } else {
                pointer = (int) instructions[pointer + 1];
            }
        }

        // 4
        private void bxc() {
            B = B ^ C;
            pointer += 2;
        }

        // 5
        private void out() {
            output.add(combo(instructions[pointer + 1]) % 8);
            pointer += 2;
        }

        // 6
        private void bdv() {
            B = A / (long) Math.pow(2, combo(instructions[pointer + 1]));
            pointer += 2;
        }

        // 7
        private void cdv() {
            C = A / (long) Math.pow(2, combo(instructions[pointer + 1]));
            pointer += 2;
        }

        @Override
        public String toString() {
            return "Computer{" +
                    "pointer=" + pointer +
                    ", A=" + A +
                    ", B=" + B +
                    ", C=" + C +
                    ", instructions=" + Arrays.toString(instructions) +
                    '}';
        }
    }
}
