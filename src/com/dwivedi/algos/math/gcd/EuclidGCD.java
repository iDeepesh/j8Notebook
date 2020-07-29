package com.dwivedi.algos.math.gcd;

public class EuclidGCD {
  static int gcd(int a, int b) {
    if (a < b) {
      a = a ^ b;
      b = a ^ b;
      a = a ^ b;
    }
    int r = a % b;
    while (r > 0) {
      a = b;
      b = r;
      r = a % b;
    }

    return b;
  }

  static int gcdRecursive(int a, int b) {
    if (a < b) {
      a = a ^ b;
      b = a ^ b;
      a = a ^ b;
    }

    int r = a % b;
    if (r > 0) {
      return gcdRecursive(b, r);
    }

    return b;
  }
}
