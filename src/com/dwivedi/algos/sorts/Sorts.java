package com.dwivedi.algos.sorts;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;


public class Sorts {
  static List<Integer> bubbleSort(List<Integer> arr, Boolean asc) {
    BiPredicate<Integer, Integer> p = SortUtil.getSortPredicate(arr, asc);

    IntStream.range(0, arr.size() - 1)
        .forEach(i -> IntStream.range(1, arr.size() - i)
            .forEach(j -> {
              if (!p.test(j - 1, j)) {
                SortUtil.swapElements(arr, j - 1, j);
              }
            }));

    return arr;
  }

  static List<Integer> selectionSort(List<Integer> arr, Boolean asc) {
    BiPredicate<Integer, Integer> p = SortUtil.getSortPredicate(arr, asc);

    IntStream.range(0, arr.size() - 1)
        .forEach(i -> {
          int s = IntStream.range(i, arr.size())
              .reduce(i, (k, j) -> {
                if (!p.test(k, j)) {
                  k = j;
                }
                return k;
              });
          SortUtil.swapElements(arr, i, s);
        });

    return arr;
  }

  static List<Integer> insertionSort(List<Integer> arr, Boolean asc) {
    BiPredicate<Integer, Integer> p = SortUtil.getSortPredicate(arr, asc);

    IntStream.range(1, arr.size())
        .forEach(i -> {
          int k = IntStream.range(0, i)
              .reduce(-1, (m, j) -> {
                if (p.test(j, i)) {
                  m = j;
                }
                return m;
              });

          IntStream.range(k + 1, i)
              .forEach(n -> SortUtil.swapElements(arr, n, i));
        });

    return arr;
  }

  static List<Integer> mergeSort(List<Integer> arr, Boolean asc) {
    if (arr.size() == 1) {
      return arr;
    }

    int mid = arr.size() / 2;
    List<Integer> left = Sorts.mergeSort(arr.subList(0, mid), asc);
    List<Integer> right = Sorts.mergeSort(arr.subList(mid, arr.size()), asc);

    BiPredicate<Integer, Integer> p = (x, y) -> asc ? x < y : x > y;

    List<Integer> m = new ArrayList<>();

    int i = 0, j = 0;
    while (i < left.size() && j < right.size()) {
      if (p.test(left.get(i), right.get(j))) {
        m.add(left.get(i++));
      } else {
        m.add(right.get(j++));
      }
    }

    IntStream.range(i, left.size())
        .forEach(n -> m.add(left.get(n)));

    IntStream.range(j, right.size())
        .forEach(n -> m.add(right.get(n)));

    return m;
  }
}
