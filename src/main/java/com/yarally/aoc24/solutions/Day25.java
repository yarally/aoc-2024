package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;

import java.util.ArrayList;
import java.util.List;

public class Day25 extends AbstractSolution<List<String>> {
    @Override
    protected String getInput() {
        return "day25.txt";
    }

    @Override
    protected List<String> parse(String input) {
        return FileReader.readFile(input);
    }

    @Override
    protected String solve1(List<String> input) {
        int state = 0;
        List<List<Integer>> locks = new ArrayList<>();
        List<List<Integer>> keys = new ArrayList<>();
        for (var line : input) {
            if (line.startsWith("#") && state == 0) {
                state = 1;
                var lock = new ArrayList<Integer>();
                lock.add(0);
                lock.add(0);
                lock.add(0);
                lock.add(0);
                lock.add(0);
                locks.add(lock);
                continue;
            }
            if (line.isEmpty()) {
                state = 0;
                continue;
            }
            if (line.startsWith(".") && state == 0) {
                state = 2;
                var key = new ArrayList<Integer>();
                key.add(5);
                key.add(5);
                key.add(5);
                key.add(5);
                key.add(5);
                keys.add(key);
                continue;
            }
            if (state == 1) {
                var chars = line.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    if (chars[i] == '#') {
                        locks.getLast().set(i, locks.getLast().get(i) + 1);
                    }
                }
            }
            if (state == 2) {
                var chars = line.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    if (chars[i] == '.') {
                        keys.getLast().set(i, keys.getLast().get(i) - 1);
                    }
                }
            }
        }
        var res = 0;
        for (var key : keys) {
            for (var lock: locks) {
                boolean fits = true;
                for (int i = 0; i < 5; i++) {
                    if (key.get(i) + lock.get(i) > 5) {
                        fits = false;
                    }
                }
                res += fits ? 1 : 0;
            }
        }
        return res + "";
    }

    @Override
    protected String solve2(List<String> input) {
        return "Merry Xmas";
    }
}
