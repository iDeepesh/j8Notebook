package com.dwivedi.algos.math.sqrt;

import java.util.function.Supplier;


public class SquareRoot {
  static double sqrt(double d, Supplier<Double> threshold) {
    double l = 0;
    double h = d;
    double r = (l + h) / 2;
    double rr = r * r;

    while (Math.abs(d - rr) > threshold.get()) {
      if (rr < d) {
        l = r;
      } else {
        h = r;
      }
      r = (l + h) / 2;
      rr = r * r;
    }
    return r;
  }
}
