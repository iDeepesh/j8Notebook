package com.dwivedi.algos.selections;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;


public class Selection {
  static <T> T select(List<T> l, int r, Comparator<T> c) {
    BiConsumer<Integer, Integer> swap = (i, j) -> {
      T temp = l.get(i);
      l.set(i, l.get(j));
      l.set(j, temp);
    };

    Supplier<Integer> anchor = () -> {
      T last = l.get(l.size() - 1);
      int a = IntStream.range(0, l.size() - 1).reduce(0, (x, i) -> {
        if (c.compare(l.get(i), last) <= 0) {
          swap.accept(i, x++);
        }
        return x;
      });
      swap.accept(a, l.size() - 1);
      return a;
    };

    int rank = anchor.get();

    if (rank != r) {
      int newRank = r > rank ? r - rank - 1 : r;
      int start = r > rank ? rank + 1 : 0;
      int end = r > rank ? l.size() : rank;
      return select(l.subList(start, end), newRank, c);
    }

    return l.get(rank);
  }
}
