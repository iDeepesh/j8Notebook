package com.dwivedi.algos.sorts;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;


public class SortTest {
  public static void main(String[] args) {
    List<Integer> a = SortTest.generateIntegerArray(10);
    System.out.println("Unsorted: " + a);

    System.out.print("Select sort type: (b)ubble, (s)election: ");
    Scanner s = new Scanner(System.in);
    String sortType = s.nextLine();

    System.out.println("Selected sort type is " + sortType + ".");
    BiConsumer<List<Integer>, Boolean> sort = SortTest.getSortFunction(sortType);
    sort.accept(a, true);
    System.out.println("Ascending: " + a);
    sort.accept(a, false);
    System.out.println("Descending: " + a);
  }

  private static BiConsumer<List<Integer>, Boolean> getSortFunction(String sortType) {
    BiConsumer<List<Integer>, Boolean> sortFunc = null;
    switch (sortType) {
      case "b":
        sortFunc = Sorts::bubbleSort;
        break;
      case "s":
        sortFunc = Sorts::selectionSort;
        break;
      default:
        System.out.println("Invalid sort type selected.");
    }
    return sortFunc;
  }

  private static List<Integer> generateIntegerArray(int n) {
    List<Integer> a = new ArrayList<>();

    IntStream.range(0, n).forEach(i -> a.add((int) (Math.random() * 1000)));

    return a;
  }
}
