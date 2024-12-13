package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 extends AbstractSolution<List<List<long[]>>> {
    @Override
    protected String getInput() {
        return "day13.txt";
    }

    @Override
    protected List<List<long[]>> parse(String input) {
        var lines = FileReader.readFile(input);
        List<List<long[]>> machines = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += 4) {
            List<long[]> vectors = new ArrayList<>();
            Pattern pattern = Pattern.compile("\\d+");
            Matcher m = pattern.matcher(lines.get(i) + lines.get(i + 1) + lines.get(i + 2));
            while (m.find()) {
                long x = Integer.parseInt(m.group());
                m.find();
                long y = Integer.parseInt(m.group());
                vectors.add(new long[]{x, y}); // Add each parsed Polong
            }
            machines.add(vectors);
        }
        return machines;
    }

    @Override
    protected String solve1(List<List<long[]>> input) {
        var score = 0L;
        for (List<long[]> vectors : input) {
            long[] p = vectors.get(2);
            long[][] B = new long[][]{new long[]{vectors.get(0)[0], vectors.get(1)[0]}, new long[]{vectors.get(0)[1], vectors.get(1)[1]}};
            long det = determinant(B);
            long[][] BI = inverse(B);
            long[] c_ = new long[]{BI[0][0] * p[0] + BI[0][1] * p[1], BI[1][0] * p[0] + BI[1][1] * p[1]};
            if (c_[0] % det != 0 || c_[1] % det != 0) continue;
            long[] c = new long[] {c_[0] / det, c_[1] / det};
            score += 3 * c[0] + c[1];
        }
        return score + "";
    }

    @Override
    protected String solve2(List<List<long[]>> input) {
        var score = 0L;
        for (List<long[]> vectors : input) {
            long[] p = vectors.get(2);
            p[0] += 10000000000000L;
            p[1] += 10000000000000L;
            long[][] B = new long[][]{new long[]{vectors.get(0)[0], vectors.get(1)[0]}, new long[]{vectors.get(0)[1], vectors.get(1)[1]}};
            long det = determinant(B);
            long[][] BI = inverse(B);
            long[] c_ = new long[]{BI[0][0] * p[0] + BI[0][1] * p[1], BI[1][0] * p[0] + BI[1][1] * p[1]};
            if (c_[0] % det != 0 || c_[1] % det != 0) continue;
            long[] c = new long[] {c_[0] / det, c_[1] / det};
            score += 3 * c[0] + c[1];
        }
        return score + "";
    }

    private long determinant(long[][] matrix) {
        return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
    }

    private long[][] inverse(long[][] B) {
        var matrix = new long[][]{B[0].clone(), B[1].clone()};
        long temp = matrix[0][0];
        matrix[0][0] = matrix[1][1];
        matrix[1][1] = temp;
        long[][] inverseMatrix = new long[2][2];
        inverseMatrix[0][0] = matrix[0][0];
        inverseMatrix[1][1] = matrix[1][1];
        inverseMatrix[0][1] = -matrix[0][1];
        inverseMatrix[1][0] = -matrix[1][0];
        return inverseMatrix;
    }

    private boolean isInteger(double d) {
        return (Math.abs(d - Math.round(d)) <= 0.21f);
    }
}

//163992010290768 << HIGH
//42765026049651 << Wrong
//29567962904353 << LOW
