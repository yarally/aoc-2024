package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;
import com.yarally.aoc24.library.Maps.RichMap;
import com.yarally.aoc24.library.Point;
import com.yarally.aoc24.library.Tuple.Tuple3;

import java.util.*;

public class Day15 extends AbstractSolution<Tuple3<RichMap<Character>, Point, List<Point>>> {
    @Override
    protected String getInput() {
        return "day15.txt";
    }

    @Override
    protected Tuple3<RichMap<Character>, Point, List<Point>> parse(String input) {
        var lines = FileReader.readFile(input);
        var mapStr = lines.stream().filter(l -> l.startsWith("#")).toList();
        Character[][] values = new Character[mapStr.size()][mapStr.get(0).length()];
        Point robot = null;
        for (int y = 0; y < mapStr.size(); y++) {
            for (int x = 0; x < mapStr.get(0).length(); x++) {
                char[] chars = mapStr.get(y).toCharArray();
                if (chars[x] == '@') {
                    robot = new Point(x, y);
                }
                values[y][x] = chars[x];
            }
        }
        var parsedInstructions = new ArrayList<Point>();
        var instructions = lines.stream().filter(l -> l.startsWith(">") || l.startsWith("<") || l.startsWith("^") || l.startsWith("v")).toList();
        for (var line : instructions) {
            for (var instr : line.toCharArray()) {
                switch (instr) {
                    case '<':
                        parsedInstructions.add(Point.LEFT);
                        break;
                    case '>':
                        parsedInstructions.add(Point.RIGHT);
                        break;
                    case 'v':
                        parsedInstructions.add(Point.UP); // reversed
                        break;
                    case '^':
                        parsedInstructions.add(Point.DOWN); // reversed
                        break;
                    default:
                        System.out.println("Invalid Instruction!");
                }
            }
        }
        return new Tuple3<>(new RichMap<Character>(values), robot, parsedInstructions);
    }

    private Tuple3<RichMap<Character>, Point, List<Point>> parse2(String input) {
        var lines = FileReader.readFile(input);
        var mapStr = lines.stream().filter(l -> l.startsWith("#")).toList();
        Character[][] values = new Character[mapStr.size()][mapStr.get(0).length() * 2];
        Point robot = null;
        for (int y = 0; y < mapStr.size(); y++) {
            for (int x = 0; x < mapStr.get(0).length() * 2; x += 2) {
                char[] chars = mapStr.get(y).toCharArray();
                if (chars[x / 2] == '@') {
                    robot = new Point(x, y);
                    values[y][x] = '@';
                    values[y][x + 1] = '.';
                } else if (chars[x / 2] == '#') {
                    values[y][x] = '#';
                    values[y][x + 1] = '#';
                } else if (chars[x / 2] == 'O') {
                    values[y][x] = '[';
                    values[y][x + 1] = ']';
                } else if (chars[x / 2] == '.') {
                    values[y][x] = '.';
                    values[y][x + 1] = '.';
                }
            }
        }
        var parsedInstructions = new ArrayList<Point>();
        var instructions = lines.stream().filter(l -> l.startsWith(">") || l.startsWith("<") || l.startsWith("^") || l.startsWith("v")).toList();
        for (var line : instructions) {
            for (var instr : line.toCharArray()) {
                switch (instr) {
                    case '<':
                        parsedInstructions.add(Point.LEFT);
                        break;
                    case '>':
                        parsedInstructions.add(Point.RIGHT);
                        break;
                    case 'v':
                        parsedInstructions.add(Point.UP); // reversed
                        break;
                    case '^':
                        parsedInstructions.add(Point.DOWN); // reversed
                        break;
                    default:
                        System.out.println("Invalid Instruction!");
                }
            }
        }
        return new Tuple3<>(new RichMap<Character>(values), robot, parsedInstructions);
    }

    @Override
    protected String solve1(Tuple3<RichMap<Character>, Point, List<Point>> input) {
        var map = input.x;
        var robot = input.y;
        var instructions = input.z;
        for (var instruction : instructions) {
            if (moveIfClear(robot, instruction, map)) {
                map.setValue(robot, '.');
                robot = robot.add(instruction);
            }
        }
        return getScore(map) + "";
    }

    private boolean moveIfClear(Point source, Point dir, RichMap<Character> map) {
        Point dest = source.add(dir);
        Character object = map.getValue(dest);
        if (object == '.') {
            map.Move(source, dest);
            return true;
        }
        if (object == '#') {
            return false;
        }
        if (object == 'O' && moveIfClear(dest, dir, map)) {
            map.Move(source, dest);
            return true;
        }
        return false;
    }

    private long getScore(RichMap<Character> map) {
        long score = 0L;
        for (int y = 1; y < map.bounds[1]; y++) {
            for (int x = 1; x < map.bounds[0]; x++) {
                if (map.getValue(x, y) == 'O' || map.getValue(x, y) == '[') {
                    score += y * 100L + x;
                }
            }
        }
        return score;
    }

    @Override
    protected String solve2(Tuple3<RichMap<Character>, Point, List<Point>> input) {
        input = parse2(getInput());
        var map = input.x;
        var robot = input.y;
        var instructions = input.z;
        for (int i = 0; i < instructions.size(); i++) {
            var instruction = instructions.get(i);
            var toMove = new HashMap<Point, Character>();
            var toUpdate = new HashMap<Point, Boolean>();
            if (isClear2(robot, instruction, map, toMove, toUpdate)) {
                for (Map.Entry<Point, Character> entry : toMove.entrySet()) {
                    Point key = entry.getKey();
                    Character val = entry.getValue();
                    map.setValue(key, val);
                    toUpdate.put(key, false);
                    if (val == '@') {
                        robot = key;
                    }
                }
                toUpdate.forEach((pos, update) -> {
                    if (update) {
                        map.setValue(pos, '.');
                    }
                });
            }
        }

        return getScore(map) + "";
    }
    private boolean isClear2(Point source, Point dir, RichMap<Character> map, HashMap<Point, Character> toMove, HashMap<Point, Boolean> toUpdate) {
        Point dest = source.add(dir);
        Character object = map.getValue(dest);
        if (object == '.') {
            toMove.put(dest, map.getValue(source));
            toUpdate.put(source, true);
            return true;
        } else if (object == '#') {
            return false;
        } else if (object == '[' && isClear2(dest, dir, map, toMove, toUpdate) && (dir.getY() == 0 || isClear2(dest.add(Point.RIGHT), dir, map, toMove, toUpdate))) {
            toMove.put(dest, map.getValue(source));
            toUpdate.put(source, true);
            return true;
        } else if (object == ']' && isClear2(dest, dir, map, toMove, toUpdate) && (dir.getY() == 0 || isClear2(dest.add(Point.LEFT), dir, map, toMove, toUpdate))) {
            toMove.put(dest, map.getValue(source));
            toUpdate.put(source, true);
            return true;
        }
        return false;
    }
}
