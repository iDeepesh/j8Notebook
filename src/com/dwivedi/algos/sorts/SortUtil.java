package com.dwivedi.algos.sorts;

import java.util.List;
import java.util.function.BiPredicate;


public class SortUtil {
  static void swapListElements(BiPredicate<Integer, Integer> orderCheck, List<Integer> arr, int x, int y) {
    if (orderCheck.test(arr.get(x), arr.get(y))) {
      Integer t = arr.get(y);
      arr.set(y, arr.get(x));
      arr.set(x, t);
    }
  }
}
