package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;
import java.util.Arrays;

public class Day9 extends AbstractSolution<Integer[]> {

    @Override
    protected String getInput() {
        return "day9.txt";
    }

    @Override
    protected Integer[] parse(String input) {
        var line = FileReader.readFile(input).getFirst().chars().map(Character::getNumericValue)
            .toArray();
        boolean isFile = true;
        int fileId = 0;
        Integer[] files = new Integer[Arrays.stream(line).sum()];
        int idx = 0;
        for (int data : line) {
            for (int i = 0; i < data; i++) {
                files[idx + i] = isFile ? fileId : null;
            }
            idx += data;
            if (isFile) {
                fileId++;
            }
            isFile = !isFile;
        }
        return files;
    }

    @Override
    protected String solve1(Integer[] input) {
        var files = input.clone();
        int i = 0;
        int j = files.length - 1;
        while (i != j) {
            if (files[i] != null) {
                i++;
                continue;
            }
            if (files[j] == null) {
                j--;
                continue;
            }
            files[i] = files[j];
            files[j] = null;
        }
        return getCheckSum(files) + "";
    }

    @Override
    protected String solve2(Integer[] input) {
        var files = input.clone();
        for (int right = files.length - 1; right >= 0; right--) {
            if (files[right] == null) {
                continue;
            }
            var blockSizeR = 0;
            while (right - blockSizeR >= 0
                && files[right - blockSizeR] != null
                && files[right - blockSizeR].equals(files[right])) {
                blockSizeR++;
            }
            for (int left = 0; left < right - blockSizeR; left++) {
                if (files[left] != null) {
                    continue;
                }
                var blockSizeL = 0;
                while (left + blockSizeL < files.length
                    && files[left + blockSizeL] == null) {
                    blockSizeL++;
                }
                if (blockSizeL < blockSizeR) {
                    left += blockSizeL - 1;
                    continue;
                }
                // Fitting block found
                for (int i = 0; i < blockSizeR; i++) {
                    files[left + i] = files[right - i];
                    files[right - i] = null;
                }
                break;
            }
            right -= blockSizeR - 1;
        }

        return getCheckSum(files) + "";
    }

    public long getCheckSum(Integer[] input) {
        var checkSum = 0L;
        for (int i = 0; i < input.length; i++) {
            if (input[i] == null) {
                continue;
            }
            checkSum += (long) i * input[i];
        }
        return checkSum;
    }
}
