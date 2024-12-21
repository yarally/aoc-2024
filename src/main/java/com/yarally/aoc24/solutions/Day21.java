package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;
import com.yarally.aoc24.library.Point;

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
        for (var code : input) {
            DirectionalRobot player = new DirectionalRobot(null);
            DirectionalRobot d1 = new DirectionalRobot(player);
            DirectionalRobot d2 = new DirectionalRobot(d1);
            KeypadRobot k1 = new KeypadRobot(d2);
            k1.executeSequence(code);
            System.out.println(player.getScore());
        }
        return null;
    }

    @Override
    protected String solve2(List<char[]> input) {
        return null;
    }

    private abstract static class Robot {
        protected DirectionalRobot parent;
        protected Point pointer;

        public abstract void executeSequence(char... sequence);
    }

    private static class KeypadRobot extends Robot {

        private final HashMap<Character, Point> keypad = new HashMap<>();

        public KeypadRobot(DirectionalRobot parent) {
            this.pointer = new Point(2, 0);
            this.parent = parent;
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

        @Override
        public void executeSequence(char... sequence) {
            for (var key : sequence) {
                var keyPos = keypad.get(key);
                // case already there
                if (keyPos.equals(pointer)) {
                    parent.executeSequence('A');
                    pointer = keyPos;
                    continue;
                }

                // determine translation
                var dx = keyPos.getX() - pointer.getX();
                var dy = keyPos.getY() - pointer.getY();
                char[] nextSequence = new char[Math.abs(dx) + Math.abs(dy) + 1];
                if (dy >= 0) {
                    for (int i = 0; i < dy; i++) {
                        nextSequence[i] = '^';
                    }
                    for (int i = dy; i < nextSequence.length - 1; i++) {
                        nextSequence[i] = dx > 0 ? '>' : '<';
                    }
                } else {
                    for (int i = 0; i < Math.abs(dx); i++) {
                        nextSequence[i] = dx > 0 ? '>' : '<';
                    }
                    for (int i = Math.abs(dx); i < nextSequence.length - 1; i++) {
                        nextSequence[i] = 'v';
                    }
                }
                nextSequence[nextSequence.length - 1] = 'A';
                parent.executeSequence(nextSequence);
                pointer = keyPos;
                var a = 1;
            }
        }
    }

    private static class DirectionalRobot extends Robot {

        private final HashMap<Character, Point> dirpad = new HashMap<>();
        private int instructionCount;

        public DirectionalRobot(DirectionalRobot parent) {
            this.pointer = new Point(2, 1);
            this.parent = parent;
            dirpad.put('<', new Point(0, 0));
            dirpad.put('v', new Point(1, 0));
            dirpad.put('>', new Point(2, 0));
            dirpad.put('^', new Point(1, 1));
            dirpad.put('A', new Point(2, 1));
        }

        @Override
        public void executeSequence(char... sequence) {
            if (parent == null) {
                instructionCount += sequence.length;
                System.out.print(sequence);
                return;
            }
            for (var key : sequence) {
                var keyPos = dirpad.get(key);
                // case already there
                if (keyPos.equals(pointer)) {
                    parent.executeSequence('A');
                    pointer = keyPos;
                    continue;
                }
                var dx = keyPos.getX() - pointer.getX();
                var dy = keyPos.getY() - pointer.getY();
                char[] nextSequence = new char[Math.abs(dx) + Math.abs(dy) + 1];
                if (pointer.equals(new Point(0, 0))) {
                    for (int i = 0; i < dx; i++) {
                        nextSequence[i] = '>';
                    }
                    for (int i = dx; i < nextSequence.length - 1; i++) {
                        nextSequence[i] = '^';
                    }
                } else if (key == '<') {
                    for (int i = 0; i < Math.abs(dy); i++) {
                        nextSequence[i] = 'v';
                    }
                    for (int i = Math.abs(dy); i < nextSequence.length - 1; i++) {
                        nextSequence[i] = '<';
                    }
                } else {
                    if (dy > 0) {
                        for (int i = 0; i < dy; i++) {
                            nextSequence[i] = '^';
                        }
                        for (int i = dy; i < nextSequence.length - 1; i++) {
                            nextSequence[i] = dx > 0 ? '>' : '<';
                        }
                    } else {
                        for (int i = 0; i < Math.abs(dx); i++) {
                            nextSequence[i] = dx > 0 ? '>' : '<';
                        }
                        for (int i = Math.abs(dx); i < nextSequence.length - 1; i++) {
                            nextSequence[i] = 'v';
                        }
                    }
                }
                nextSequence[nextSequence.length - 1] = 'A';
                parent.executeSequence(nextSequence);
                pointer = keyPos;
            }

        }

        public int getScore() {
            return instructionCount;
        }
    }
}
