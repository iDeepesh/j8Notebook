package com.dwivedi.algos.branchandbound;

import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;


public class EditDistanceTest {

  public static void main(String[] args) {
    String s1 = createRandomString(15);
    System.out.println("s1=" + s1);
    String s2 = createRandomString(15);
    System.out.println("s2=" + s2);

    Function<BiFunction<String, String, Integer>, Integer> timed = (f) -> {
      long s = System.nanoTime();
      int d = f.apply(s1, s2);
      long e = System.nanoTime();
      System.out.println("Time taken to execute: " + (e - s) / 1000 + " micro seconds.");
      System.out.println("Calculated distance: " + d);
      return d;
    };

    System.out.println("\nDynamic Programming:");
    timed.apply(EditDistance::minDistanceDynamicProgramming);
    System.out.println("\nBrute force:");
    timed.apply(EditDistance::minDistance);
  }

  private static String createRandomString(int maxLength) {
    int l = (int) (Math.random() * maxLength);
    l = l > maxLength / 2 ? l : l + maxLength / 2;
    StringBuilder sb = new StringBuilder();
    Random r = new Random();
    r.ints(l, 97, 123).forEach(i -> sb.append(Character.toChars(i)));

    return sb.toString();
  }
}
