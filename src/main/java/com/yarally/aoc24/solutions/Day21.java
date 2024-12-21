package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;
import com.yarally.aoc24.library.Point;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day21 extends AbstractSolution<List<char[]>> {
    @Override
    protected String getInput() {
        return "day21.txt";
    }

    @Override
    protected List<char[]> parse(String input) {
        var lines = FileReader.readFile(input);
        return lines.stream().map(String::toCharArray).toList();
    }

    @Override
    protected String solve1(List<char[]> input) {
        var res = 0L;
        for (var code : input) {
            DirectionalRobot parent = null;
            for (int i = 0; i < 3; i++) {
                parent = new DirectionalRobot(parent);
            }
            HashMap<String, Long> cache = new HashMap<>();
            KeypadRobot kpBot = new KeypadRobot(parent);
            var score1 = kpBot.calculateSequence(String.valueOf(code), 0, cache);
            var score2 = Integer.parseInt(String.valueOf(code).replaceAll("[^0-9]", ""));
            res += score1 * score2;
        }
        return res + "";
    }

    @Override
    protected String solve2(List<char[]> input) {
        var res = 0L;
        for (var code : input) {
            DirectionalRobot parent = null;
            for (int i = 0; i < 26; i++) {
                parent = new DirectionalRobot(parent);
            }
            HashMap<String, Long> cache = new HashMap<>();
            KeypadRobot kpBot = new KeypadRobot(parent);
            var score1 = kpBot.calculateSequence(String.valueOf(code), 0, cache);
            var score2 = Long.parseLong(String.valueOf(code).replaceAll("[^0-9]", ""));
            res += score1 * score2;
        }
        return res + "";
    }

    private abstract static class Robot {
        protected Robot parent;
        protected Point pointer;
        protected Point deathPoint;
        protected final HashMap<Character, Point> keypad = new HashMap<>();


        public String executeSequence(char[] sequence) {
            if (parent == null) {
                return String.valueOf(sequence);
            }
            var result = "";
            for (var key : sequence) {
                var keyPos = keypad.get(key);
                // case already there
                if (keyPos.equals(pointer)) {
                    result += "A";
                    parent.executeSequence(new char[]{'A'});
                    pointer = keyPos;
                    continue;
                }

                // determine translation
                var dx = keyPos.getX() - pointer.getX();
                var dy = keyPos.getY() - pointer.getY();
                char[] nextSequence;
                String xChars = (dx > 0 ? ">" : "<").repeat(Math.abs(dx));
                String yChars = (dy > 0 ? "^" : "v").repeat(Math.abs(dy));
                if (new Point(pointer.getX() + dx, pointer.getY()).equals(this.deathPoint)) {
                    nextSequence = (yChars + xChars + "A").toCharArray();
                } else if (new Point(pointer.getX(), pointer.getY() + dy).equals(this.deathPoint)) {
                    nextSequence = (xChars + yChars + "A").toCharArray();
                } else {
                    var s1 = parent.executeSequence((xChars + yChars + "A").toCharArray());
                    var s2 = parent.executeSequence((yChars + xChars + "A").toCharArray());
                    nextSequence = s1.length() < s2.length() ? (xChars + yChars + "A").toCharArray() : (yChars + xChars + "A").toCharArray();

                }
                result += parent.executeSequence(nextSequence);
                pointer = keyPos;
            }
            return result;
        }

        public long calculateSequence(String sequence, int depth, HashMap<String, Long> cache) {
            String cacheKey = depth + sequence;
            if (cache.containsKey(cacheKey)) {
                return cache.get(cacheKey);
            }

            if (parent == null) {
                cache.put(cacheKey, (long) sequence.length());
                return sequence.length();
            }
            var steps = 0L;
            for (var key : sequence.toCharArray()) {
                var keyPos = keypad.get(key);
                // case already there
                if (keyPos.equals(pointer)) {
                    steps += 1L;
                    parent.calculateSequence("A", depth + 1, cache);
                    pointer = keyPos;
                    continue;
                }

                // determine translation
                var dx = keyPos.getX() - pointer.getX();
                var dy = keyPos.getY() - pointer.getY();
                String nextSequence;
                String xChars = (dx > 0 ? ">" : "<").repeat(Math.abs(dx));
                String yChars = (dy > 0 ? "^" : "v").repeat(Math.abs(dy));
                if (pointer.dryMove(dx, 0).equals(this.deathPoint)) {
                    nextSequence = (yChars + xChars + "A");
                    steps += parent.calculateSequence(nextSequence, depth + 1, cache);
                } else if (pointer.dryMove(0, dy).equals(this.deathPoint)) {
                    nextSequence = (xChars + yChars + "A");
                    steps += parent.calculateSequence(nextSequence, depth + 1, cache);
                } else {
                    var s1 = xChars + yChars + "A";
                    var s2 = yChars + xChars + "A";
                    steps += Math.min(parent.calculateSequence(s1, depth + 1, cache), parent.calculateSequence(s2, depth + 1, cache));
                }
                pointer = keyPos;
            }
            cache.put(cacheKey, steps);
            return steps;
        }
    }

    private static class KeypadRobot extends Robot {


        public KeypadRobot(Robot parent) {
            this.pointer = new Point(2, 0);
            this.parent = parent;
            this.deathPoint = new Point(0, 0);
            keypad.put('0', new Point(1, 0));
            keypad.put('A', new Point(2, 0));
            keypad.put('1', new Point(0, 1));
            keypad.put('2', new Point(1, 1));
            keypad.put('3', new Point(2, 1));
            keypad.put('4', new Point(0, 2));
            keypad.put('5', new Point(1, 2));
            keypad.put('6', new Point(2, 2));
            keypad.put('7', new Point(0, 3));
            keypad.put('8', new Point(1, 3));
            keypad.put('9', new Point(2, 3));
        }
    }

    private static class DirectionalRobot extends Robot {
        public DirectionalRobot(Robot parent) {
            this.deathPoint = new Point(0, 1);
            this.pointer = new Point(2, 1);
            this.parent = parent;
            keypad.put('<', new Point(0, 0));
            keypad.put('v', new Point(1, 0));
            keypad.put('>', new Point(2, 0));
            keypad.put('^', new Point(1, 1));
            keypad.put('A', new Point(2, 1));
        }
    }
}
