package com.dwivedi.algos.sorts;

import java.util.List;
import java.util.function.BiPredicate;


public class SortUtil {

  static BiPredicate<Integer, Integer> getSortPredicate(List<Integer> arr, Boolean asc) {
    return (i, j) -> asc ? arr.get(i) < arr.get(j) : arr.get(i) > arr.get(j);
  }

  static void swapElements(List<Integer> arr, int x, int y) {
    Integer t = arr.get(y);
    arr.set(y, arr.get(x));
    arr.set(x, t);
  }
}
