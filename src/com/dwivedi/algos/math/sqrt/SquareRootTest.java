package com.dwivedi.algos.math.sqrt;

import java.util.function.Supplier;


public class SquareRootTest {
  static Supplier<Double> THRESHOLD = ()->0.001;

  public static void main(String[] args) {
    double d = Math.random()*10000000;
    System.out.println("d=" + d + ", Square root=" + SquareRoot.sqrt(d, THRESHOLD));
  }
}
