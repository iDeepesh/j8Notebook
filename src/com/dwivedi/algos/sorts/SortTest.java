package com.dwivedi.algos.sorts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;


public class SortTest {
  private static Map<String, BiConsumer<List<Integer>, Boolean>> sortFuncMap = new HashMap<>();

  static {
    sortFuncMap.put("b", Sorts::bubbleSort);
    sortFuncMap.put("s", Sorts::selectionSort);
    sortFuncMap.put("i", Sorts::insertionSort);
  }

  public static void main(String[] args) {
    List<Integer> a = SortTest.generateIntegerArray(10);
    System.out.println("Unsorted: " + a);

    System.out.print("Select sort type: (b)ubble, (s)election, (i)nsertion: ");
    Scanner s = new Scanner(System.in);
    String sortType = s.nextLine();

    System.out.println("Selected sort type is " + sortType + ".");
    BiConsumer<List<Integer>, Boolean> sort = SortTest.sortFuncMap.get(sortType);
    sort.accept(a, true);
    System.out.println("Ascending: " + a);
    sort.accept(a, false);
    System.out.println("Descending: " + a);
  }

  private static List<Integer> generateIntegerArray(int n) {
    List<Integer> a = new ArrayList<>();

    IntStream.range(0, n).forEach(i -> a.add((int) (Math.random() * 1000)));

    return a;
  }
}
