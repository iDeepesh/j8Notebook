package com.dwivedi.algos.math.gcd;

public class EuclidGCDTest {
  public static void main(String[] args) {
    int a = (int) (Math.random() * 100);
    int b = (int) (Math.random() * 100);
    System.out.println("a=" + a + ", b=" + b + ". GCD for a & b = " + EuclidGCD.gcd(a, b));
    System.out.println("a=" + a + ", b=" + b + ". GCD recursive = " + EuclidGCD.gcdRecursive(a, b));
  }
}
