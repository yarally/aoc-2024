package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;
import com.yarally.aoc24.library.linkedlist.DLLNode;
import com.yarally.aoc24.library.linkedlist.DoublyLinkedList;
import java.util.ArrayList;
import java.util.List;

public class Day9 extends AbstractSolution<DoublyLinkedList<Integer>> {

    @Override
    protected String getInput() {
        return "day9.txt";
    }

    @Override
    protected DoublyLinkedList<Integer> parse(String input) {
        var line = FileReader.readFile(input).stream().map(String::toCharArray)
            .toArray(char[][]::new)[0];
        boolean isFile = true;
        int fileId = 0;
        List<DLLNode<Integer>> nodes = new ArrayList<>();
        for (char c : line) {
            int size = Integer.parseInt(String.valueOf(c));
            for (int i = 0; i < size; i++) {
                nodes.add(new DLLNode<>(isFile ? fileId : -1));
            }
            if (isFile) {
                fileId++;
            }
            isFile = !isFile;
        }
        for (int i = 0; i < nodes.size(); i++) {
            if (i > 0) {
                nodes.get(i).setPrevious(nodes.get(i - 1));
            }
            if (i < nodes.size() - 1) {
                nodes.get(i).setNext(nodes.get(i + 1));
            }
        }
        return new DoublyLinkedList<>(nodes.getFirst(), nodes.getLast(), nodes);
    }

    @Override
    protected String solve1(DoublyLinkedList<Integer> input) {
        return "";
//        var left = input.getHead();
//        var right = input.getTail();
//        while (left != right) {
//            if (left.value >= 0) {
//                left = left.getNext();
//                continue;
//            }
//            if (right.value < 0) {
//                right = right.getPrevious();
//                continue;
//            }
//            var lNext = left.getNext();
//            var lPrev = left.getPrevious();
//            left.setNext(right.getNext());
//            left.setPrevious(right.getPrevious());
//            right.setNext(lNext);
//            right.setPrevious(lPrev);
//            if (right == input.getTail()) {
//                input.setTail(left);
//            }
//            if (left == input.getHead()) {
//                input.setHead(right);
//            }
//            right = left.getPrevious();
//            left = lNext;
//        }
//        var current = input.getHead();
//        var idx = 0L;
//        var checkSum = 0L;
//        while (current.value >= 0) {
//            checkSum += current.value * idx;
//            idx++;
//            current = current.getNext();
//        }
//        return checkSum + "";
    }

    @Override
    protected String solve2(DoublyLinkedList<Integer> input) {
        var left = input.getHead();
        var right = input.getTail();
        while (left != right && right != null) {
            if (left.value >= 0) {
                left = left.getNext();
                continue;
            }
            if (right.value < 0) {
                right = right.getPrevious();
                continue;
            }
//            System.out.println(right.value);
            var lBlockSize = getForwardBlockSize(left);
            var rBlockSize = getBackwardBlockSize(right);
            while (left != null) {
                if (left == right) {
                    break;
                }
                if (left.value >= 0) {
                    left = left.getNext();
                    continue;
                }
                lBlockSize = getForwardBlockSize(left);
                if (lBlockSize >= rBlockSize) {
                    break;
                }
                left = left.getNext();
            }
            if (left == null || lBlockSize < rBlockSize) {
                right = getBackwardBlock(right).getPrevious();
                left = input.getHead();
                continue;
            }
            var l1 = left;
            var l2 = left.getNext(rBlockSize);
            var lp = l1.getPrevious();
            var ln = l2.getNext();
            var r1 = getBackwardBlock(right);
            var r2 = right;
            var rp = r1.getPrevious();
            var rn = r2.getNext();
            if (lp != null) {
                lp.setNext(r1);
            }
            r1.setPrevious(lp);
            ln.setPrevious(r2);
            r2.setNext(ln);
            rp.setNext(l1);
            l1.setPrevious(rp);
            if (rn != null) {
                rn.setPrevious(l2);
            }
            l2.setNext(rn);
            left = input.getHead();
            right = rp;
        }
//        System.out.println(input);
        var current = input.getHead();
        var idx = 0L;
        var checkSum = 0L;
        while (current != null) {
            if (current.value > 0) {
                checkSum += current.value * idx;
            }
            idx++;
            current = current.getNext();
        }
        return checkSum + "";
        // wrong: 6435922584968
        // wrong: 10186223672776
    }

    private DLLNode<Integer> getBackwardBlock(DLLNode<Integer> start) {
        var id = start.value;
        var current = start;
        while (true) {
            if (current.getPrevious() != null && current.getPrevious().value.equals(id)) {
                current = current.getPrevious();
            } else {
                return current;
            }
        }
    }

    private DLLNode<Integer> getForwardBlock(DLLNode<Integer> start) {
        var id = start.value;
        var current = start;
        while (true) {
            if (current.getPrevious() != null && current.getPrevious().value.equals(id)) {
                current = current.getPrevious();
            } else {
                return current;
            }
        }
    }

    private int getBackwardBlockSize(DLLNode<Integer> start) {
        var size = 0;
        var id = start.value;
        var current = start;
        while (true) {
            size++;
            if (current.getPrevious() != null && current.getPrevious().value.equals(id)) {
                current = current.getPrevious();
            } else {
                return size;
            }
        }
    }

    private int getForwardBlockSize(DLLNode<Integer> start) {
        var id = start.value;
        var current = start;
        var size = 0;
        while (true) {
            size++;
            if (current.getNext() != null && current.getNext().value.equals(id)) {
                current = current.getNext();
            } else {
                return size;
            }
        }
    }
}
