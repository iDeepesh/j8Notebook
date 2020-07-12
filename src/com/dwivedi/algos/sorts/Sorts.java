package com.dwivedi.algos.sorts;

import java.util.List;
import java.util.stream.IntStream;


public class Sorts {
  static void bubbleSort(List<Integer> arr, Boolean asc) {
    IntStream.range(0, arr.size() - 1)
        .forEach(i -> IntStream.range(1, arr.size() - i)
            .forEach(j -> SortUtil.swapListElements((x, y) -> asc ? x > y : x < y, arr, j - 1, j)));
  }
}
