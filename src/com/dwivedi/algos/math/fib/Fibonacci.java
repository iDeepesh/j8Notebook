package com.dwivedi.algos.math.fib;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.IntStream;


public class Fibonacci {
  static int fibonacciRecursive(int n) {
    if (n < 2) {
      return n;
    }

    return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
  }

  static int fibonacciBottomUpDynamicProgramming(int n) {
    Map<Integer, Integer> f = new HashMap<>();
    f.put(0, 0);
    f.put(1, 1);

    IntStream.range(2, n + 1).forEach(i -> {
      f.put(i, f.get(i - 1) + f.get(i - 2));
    });

    return f.get(n);
  }

  static int fibonacciTopDownDynamicProgramming(int n) {
    Map<Integer, Integer> m = new HashMap<>();
    m.put(0, 0);
    m.put(1, 1);

    // for recursive invocation
    BiFunction<BiFunction, Integer, Integer> fib = (f, y) -> {
      if (m.containsKey(y)) {
        return m.get(y);
      } else {
        int f1 = (int) f.apply(f, y - 1);
        int f2 = (int) f.apply(f, y - 2);
        m.put(y, f1 + f2);
        return f1 + f2;
      }
    };

    return fib.apply(fib, n);
  }
}
