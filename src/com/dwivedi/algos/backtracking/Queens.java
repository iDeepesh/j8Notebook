package com.dwivedi.algos.backtracking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.IntStream;


public class Queens {

  static List<Map<Integer, Integer>> getAllBoards(int n) {
    List<Map<Integer, Integer>> s = new ArrayList<>();

    Map<Integer, Integer> q = new HashMap<>();

    BiFunction<BiFunction, Integer, Boolean> placeQueenForRow = (pqfr, r) -> {
      if (r >= n) {
        Map<Integer, Integer> qCopy = new HashMap<>();
        q.entrySet().stream().forEach(e -> qCopy.put(e.getKey(), e.getValue()));
        s.add(qCopy);
        return true;
      }

      int c = 0;
      while (c < n) {
        if (!threatened(r, c, q)) {
          q.put(r, c);
          pqfr.apply(pqfr, r + 1);
        }
        c++;
      }

      return false;
    };

    placeQueenForRow.apply(placeQueenForRow, 0);

    return s;
  }

  static Map<Integer, Integer> placeQueens(int n) {
    Map<Integer, Integer> q = new HashMap<>();

    BiFunction<BiFunction, Integer, Boolean> placeQueenForRow = (pqfr, r) -> {
      if (r >= n) {
        return true;
      }

      int c = 0;
      while (c < n) {
        q.put(r, c);
        if (!threatened(r, c, q) && pqfr.apply(pqfr, r + 1).equals(true)) {
          return true;
        }
        c++;
      }

      return false;
    };

    placeQueenForRow.apply(placeQueenForRow, 0);
    return q;
  }

  static private boolean threatened(int row, int col, Map<Integer, Integer> q) {
    return IntStream.range(0, row)
        .filter(r -> ((q.get(r) == col) || (Math.abs(q.get(r) - col) == Math.abs(r - row))))
        .findFirst()
        .isPresent();
  }
}
