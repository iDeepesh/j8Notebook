package com.dwivedi.algos.math.fib;

import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Function;


public class FibonacciTest {
  public static void main(String[] args) {
    System.out.println("Trying to find a Fibonnacci number. What is the index in series? ");
    Scanner s = new Scanner(System.in);
    int n = s.nextInt();

    BiFunction<Function<Integer, Integer>, Integer, String> timed = (f, m) -> {
      long start = System.nanoTime();
      int fib = f.apply(m);
      long end = System.nanoTime();
      return fib + ". Took " + (end - start) + " nano seconds to execute.";
    };

    String str = " programming fibonnacci number for " + n + " is: ";

    System.out.println("Recursive        " + str + timed.apply(Fibonacci::fibonacciRecursive, n));
    System.out.println("Bottom up dynamic" + str + timed.apply(Fibonacci::fibonacciBottomUpDynamicProgramming, n));
    System.out.println("Top down dynamic " + str + timed.apply(Fibonacci::fibonacciTopDownDynamicProgramming, n));
  }
}
