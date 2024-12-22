package com.yarally.aoc24.solutions;

import com.yarally.aoc24.library.AbstractSolution;
import com.yarally.aoc24.library.FileReader;

import java.util.*;
import java.util.stream.Collectors;

public class Day22 extends AbstractSolution<List<Long>> {
    @Override
    protected String getInput() {
        return "day22.txt";
    }

    @Override
    protected List<Long> parse(String input) {
        return FileReader.readFile(input).stream().mapToLong(Long::valueOf).boxed().toList();
    }

    @Override
    protected String solve1(List<Long> input) {
        long res = 0L;
        for (var number : input) {
            var price = number;
            for (int i = 0; i < 2000; i++) {
                price = next(price);
            }
            res += price;
        }
        return res + "";
    }

    @Override
    protected String solve2(List<Long> input) {
        List<List<Long>> allRelativeChanges = new ArrayList<>();
        List<List<Long>> allPrices = new ArrayList<>();
        for (var number : input) {
            var price = number;
            List<Long> relativeChanges = new ArrayList<>();
            List<Long> prices = new ArrayList<>();
            prices.add(number % 10);
            relativeChanges.add(null);
            for (int i = 0; i < 2000; i++) {
                var nextPrice = next(price);
                relativeChanges.add((nextPrice % 10) - (price % 10));
                prices.add(nextPrice % 10);
                price = nextPrice;
            }
            allRelativeChanges.add(relativeChanges);
            allPrices.add(prices);
        }
        HashMap<String, Long> bananaMap = new HashMap<>();
        for (int changesIdx = 0; changesIdx < allRelativeChanges.size(); changesIdx++) {
            var visited = new HashSet<String>();
            var relativeChanges = allRelativeChanges.get(changesIdx);
            for (int windowStart = 1; windowStart < relativeChanges.size()-3; windowStart++) {
                var window = relativeChanges.subList(windowStart, windowStart + 4);
                var key = String.valueOf(window);
                if (window.stream().mapToLong(l -> l).sum() < 2 || visited.contains(key)) {
                    continue;
                }
                visited.add(key);
                var idx = Collections.indexOfSubList(relativeChanges, window);
                var price = allPrices.get(changesIdx).get(idx + 3);
                bananaMap.put(key, bananaMap.getOrDefault(key, 0L) + price);
            }
        }
        return bananaMap.values().stream().max(Comparator.naturalOrder()).get() + "";
//        var maxBananas = 0L;
//        var visited = new HashSet<String>();
//        for (int k = 0; k < allRelativeChanges.size(); k++) {
//            var relativeChanges = allRelativeChanges.get(k);
//            for (int i = 1; i < relativeChanges.size(); i += 4) {
//                var window = relativeChanges.subList(i, i + 4);
//                var cacheKey = String.valueOf(window);
//                if (visited.contains(cacheKey) || window.stream().mapToLong(l -> l).sum() < 0) {
//                    continue;
//                }
//                visited.add(cacheKey);
//                var bananas = 0L;
//                for (int j = 0; j < allRelativeChanges.size(); j++) {
//                    var rc = allRelativeChanges.get(j);
//                    var idx = Collections.indexOfSubList(rc, window);
//                    if (idx < 0) continue;
//                    bananas += allPrices.get(j).get(idx + 3);
//                }
//                maxBananas = Math.max(maxBananas, bananas);
//            }
//            System.out.println(k);
//        }
//        return maxBananas + "";
    }

    private static List<Integer> getAllIndices(List<Long> source, List<Long> target) {
        List<Integer> indices = new ArrayList<>();
        int start = 0;

        while (start < source.size()) {
            int index = Collections.indexOfSubList(source.subList(start, source.size()), target);
            if (index == -1) {
                break;
            }
            indices.add(start + index);
            start += index + 1;
        }

        return indices;
    }

    private Long next(long current) {
        long mod = 16777216L;
        current = ((current * 64L) ^ current) % mod;
        current = ((current / 32L) ^ current) % mod;
        current = ((current * 2048L) ^ current) % mod;
        return current;
    }
}
