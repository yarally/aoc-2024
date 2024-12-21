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
            DirectionalRobot player = new DirectionalRobot(null, "Player");
            DirectionalRobot d1 = new DirectionalRobot(player, "DIR1");
            DirectionalRobot d2 = new DirectionalRobot(d1, "DIR2");
            KeypadRobot k1 = new KeypadRobot(d2, "KPAD");
            var score1 = k1.executeSequence(code);
            var score2 = Integer.parseInt(String.valueOf(code).replaceAll("[^0-9]", ""));
            System.out.println(score1 + ": " + score1.length());
            res += (long) score1.length() * score2;
        }
        return res + "";
        // 178070 too high
    }

    @Override
    protected String solve2(List<char[]> input) {
        return null;
    }

    private abstract static class Robot {
        protected DirectionalRobot parent;
        protected Point pointer;

        protected String id;
    }

    private static class KeypadRobot extends Robot {

        private final HashMap<Character, Point> keypad = new HashMap<>();

        public KeypadRobot(DirectionalRobot parent, String id) {
            this.pointer = new Point(2, 0);
            this.parent = parent;
            this.id = id;
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

        public String executeSequence(char[] sequence) {
            var count = "";
            for (var key : sequence) {
                var keyPos = keypad.get(key);
                // case already there
                if (keyPos.equals(pointer)) {
                    count += "A";
                    parent.executeSequence(new char[]{'A'});
                    pointer = keyPos;
                    continue;
                }

                // determine translation
                var dx = keyPos.getX() - pointer.getX();
                var dy = keyPos.getY() - pointer.getY();
                char[] nextSequence1;
                char[] nextSequence2;
                String xChars = (dx > 0 ? ">" : "<").repeat(Math.abs(dx));
                String yChars = (dy > 0 ? "^" : "v").repeat(Math.abs(dy));
                if (dy >= 0) {
                    nextSequence1 = (yChars + xChars + "A").toCharArray();

                } else {
                    nextSequence1 = (xChars + yChars + "A").toCharArray();
                }
                if (dx >= 0) {
                    nextSequence2 = (xChars + yChars + "A").toCharArray();

                } else {
                    nextSequence2 = (yChars + xChars + "A").toCharArray();
                }
                if (Arrays.equals(nextSequence1, nextSequence2)) {
                    count += parent.executeSequence(nextSequence1);
                } else {
                    var s1 = parent.executeSequence(nextSequence1);
                    var s2 = parent.executeSequence(nextSequence2);
                    if (s1.length() != s2.length()) {
                        System.out.println("HIT");
                    }
                    count += s1.length() < s2.length() ? s1 : s2;
                }
                pointer = keyPos;
            }
            return count;
        }
    }

    private static class DirectionalRobot extends Robot {

        private final HashMap<Character, Point> dirpad = new HashMap<>();

        public DirectionalRobot(DirectionalRobot parent, String id) {
            this.pointer = new Point(2, 1);
            this.parent = parent;
            this.id = id;
            dirpad.put('<', new Point(0, 0));
            dirpad.put('v', new Point(1, 0));
            dirpad.put('>', new Point(2, 0));
            dirpad.put('^', new Point(1, 1));
            dirpad.put('A', new Point(2, 1));
        }

        public String executeSequence(char[] sequence) {
            if (parent == null) {
                return String.valueOf(sequence);
            }
            var count = "";
            for (var key : sequence) {
                var keyPos = dirpad.get(key);
                // case already there
                if (keyPos.equals(pointer)) {
                    count += "A";
                    parent.executeSequence(new char[]{'A'});
                    pointer = keyPos;
                    continue;
                }

                // determine translation
                var dx = keyPos.getX() - pointer.getX();
                var dy = keyPos.getY() - pointer.getY();
                char[] nextSequence1;
                char[] nextSequence2 = new char[0];
                String xChars = (dx > 0 ? ">" : "<").repeat(Math.abs(dx));
                String yChars = (dy > 0 ? "^" : "v").repeat(Math.abs(dy));
                if (dx >= 0) {
                    nextSequence1 = (xChars + yChars + "A").toCharArray();
                } else {
                    nextSequence1 = (yChars + xChars + "A").toCharArray();
                }
                if (dy <= 0) {
                    nextSequence2 = (yChars + xChars + "A").toCharArray();
                } else {
                    nextSequence2 = (xChars + yChars + "A").toCharArray();
                }
                if (Arrays.equals(nextSequence1, nextSequence2)) {
                    count += parent.executeSequence(nextSequence1);
                } else {
                    var s1 = parent.executeSequence(nextSequence1);
                    var s2 = parent.executeSequence(nextSequence2);
                    if (s1.length() != s2.length()) {
                        System.out.println("HIT");
                    }
                    count += s1.length() > s2.length() ? s1 : s2;
                }
                pointer = keyPos;
            }
            return count;
        }
    }
}
