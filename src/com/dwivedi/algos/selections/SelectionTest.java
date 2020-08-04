package com.dwivedi.algos.selections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.IntStream;


public class SelectionTest {
  public static void main(String[] args) {
    testSelection(Integer::compare, () -> (int) (Math.random() * 1000));
  }

  private static <T> void testSelection(Comparator<T> c, Supplier<T> rand) {
    List<T> l = new ArrayList<>();
    IntStream.range(0, 25).forEach(i -> l.add(rand.get()));

    System.out.println(l);
    System.out.println("What is the rank you want to get: ");
    Scanner s = new Scanner(System.in);
    int rank = s.nextInt();

    T value = Selection.select(l, rank, c);

    System.out.println("The ranked element is: " + value);
    Collections.sort(l, c);
    System.out.println(l);
  }
}
