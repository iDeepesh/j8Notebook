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

  static List<Integer> quickSort(List<Integer> arr, Boolean asc) {
    if (arr.size() < 1) {
      return arr;
    }

    BiPredicate<Integer, Integer> p = SortUtil.getSortPredicate(arr, asc);

    int r = arr.size() - 1;
    int a = IntStream.range(0, r)
        .reduce(0, (c, i) -> {
          if (p.test(i, r)) {
            SortUtil.swapElements(arr, c, i);
            c++;
          }
          return c;
        });

    SortUtil.swapElements(arr, a, r);

    Sorts.quickSort(arr.subList(0, a), asc);
    Sorts.quickSort(arr.subList(a + 1, arr.size()), asc);

    return arr;
  }

  static List<Integer> radixSort(List<Integer> arr, Boolean asc) {
    final int BUCKET_COUNT = 10;
    final int MULTIPLIER = 10;

    Integer max = arr.stream()
        .max(Integer::compare)
        .get();

    List<List<Integer>> buckets = new ArrayList<>();
    IntStream.range(0, BUCKET_COUNT)
        .forEach(i -> buckets.add(new ArrayList<>()));

    Integer d = 1;
    while (max / d != 0) {
      int r1 = d;
      int r2 = d * MULTIPLIER;

      IntStream.range(0, arr.size())
          .forEach(i -> {
            int n = (arr.get(i) % r2) / r1;
            n = asc ? n : (BUCKET_COUNT - n - 1);
            buckets.get(n)
                .add(arr.get(i));
          });

      arr.clear();
      buckets.stream()
          .forEach(b -> {
            arr.addAll(b);
            b.clear();
          });

      d *= MULTIPLIER;
    }

    return arr;
  }

  static List<Integer> heapSort(List<Integer> arr, Boolean asc) {
    System.out.println("To be implemented.....");
    return arr;
  }
}
