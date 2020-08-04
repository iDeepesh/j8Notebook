package com.dwivedi.algos.branchandbound;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;


public class EditDistance {
  static int minDistance(String s1, String s2) {
    char[] a1 = s1.toCharArray();
    char[] a2 = s2.toCharArray();

    return recMinDistance(a1, a2, 0, 0);
  }

  private static int recMinDistance(char[] a1, char[] a2, int i1, int i2) {
    if (i1 >= a1.length || i2 >= a2.length) {
      return Math.abs(a1.length - a2.length);
    }

    if (a1[i1] == a2[i2]) {
      return recMinDistance(a1, a2, i1 + 1, i2 + 1);
    }

    int n1 = recMinDistance(a1, a2, i1 + 1, i2);
    int n2 = recMinDistance(a1, a2, i1, i2 + 1);
    int n3 = recMinDistance(a1, a2, i1 + 1, i2 + 1);

    return (n1 < n2 ? (n1 < n3 ? n1 : n3) : (n2 < n3 ? n2 : n3)) + 1;
  }

  static int minDistanceDynamicProgramming(String s1, String s2) {
    char[] a1 = s1.toCharArray();
    char[] a2 = s2.toCharArray();
    Map<Integer, Integer> dp = new HashMap<>();
    BiFunction<Integer, Integer, Integer> hash = (i, j) -> ("i1=" + i + "i2=" + j).hashCode();

    return recMinDistanceDynamicProgramming(a1, a2, 0, 0, dp, hash);
  }

  private static int recMinDistanceDynamicProgramming(char[] a1, char[] a2, int i1, int i2, Map<Integer, Integer> dp,
      BiFunction<Integer, Integer, Integer> hash) {
    if (i1 >= a1.length || i2 >= a2.length) {
      return Math.abs(a1.length - a2.length);
    }

    Integer h = hash.apply(i1, i2);
    if (dp.containsKey(h)) {
      return dp.get(h);
    }

    int r = 0;
    if (a1[i1] == a2[i2]) {
      r = recMinDistanceDynamicProgramming(a1, a2, i1 + 1, i2 + 1, dp, hash);
    } else {
      int r1 = recMinDistanceDynamicProgramming(a1, a2, i1 + 1, i2, dp, hash);
      int r2 = recMinDistanceDynamicProgramming(a1, a2, i1, i2 + 1, dp, hash);
      int r3 = recMinDistanceDynamicProgramming(a1, a2, i1 + 1, i2 + 1, dp, hash);

      r = (r1 < r2 ? (r1 < r3 ? r1 : r3) : (r2 < r3 ? r2 : r3)) + 1;
    }

    dp.put(h, r);
    return r;
  }
}
