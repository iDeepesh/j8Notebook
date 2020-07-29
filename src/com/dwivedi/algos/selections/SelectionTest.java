package com.dwivedi.algos.selections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;


public class SelectionTest {
  public static void main(String[] args) {
    List<Integer> l = new ArrayList<>();
    IntStream.range(0, 25).forEach(i -> l.add((int) (Math.random() * 1000)));

    System.out.println(l);
    System.out.println("What is the rank you want to get: ");
    Scanner s = new Scanner(System.in);
    int rank = s.nextInt();
    int value = Selection.select(l, rank, Integer::compare);
    System.out.println("The ranked element is: " + value);
    Collections.sort(l);
    System.out.println(l);
  }
}
