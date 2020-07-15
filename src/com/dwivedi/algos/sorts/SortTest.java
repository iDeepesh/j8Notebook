package com.dwivedi.algos.sorts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.stream.IntStream;


public class SortTest {
  private static Map<Character, BiFunction<List<Integer>, Boolean, List<Integer>>> sortFuncMap = new HashMap<>();
  private static Map<Character, String> sortDesc = new HashMap<>();

  static {
    sortFuncMap.put('b', Sorts::bubbleSort);
    sortFuncMap.put('s', Sorts::selectionSort);
    sortFuncMap.put('i', Sorts::insertionSort);
    sortFuncMap.put('m', Sorts::mergeSort);

    sortDesc.put('b', "Bubble sort");
    sortDesc.put('s', "Selection sort");
    sortDesc.put('i', "Insertion sort");
    sortDesc.put('m', "Merge sort");
  }

  public static void main(String[] args) {
    List<Integer> a = SortTest.generateIntegerArray(10);
    System.out.println("Unsorted: " + a);

    System.out.print("Select sort type: (b)ubble, (s)election, (i)nsertion, (m)erge: ");
    Scanner s = new Scanner(System.in);
    Character sortType = s.next()
        .charAt(0);

    System.out.println("Selected sort type is " + sortDesc.get(sortType) + ".");
    BiFunction<List<Integer>, Boolean, List<Integer>> sort = SortTest.sortFuncMap.get(sortType);
    List<Integer> asc = sort.apply(a, true);
    System.out.println(sortDesc.get(sortType) + " - Ascending: " + asc);
    List<Integer> desc = sort.apply(a, false);
    System.out.println(sortDesc.get(sortType) + " - Descending: " + desc);
  }

  private static List<Integer> generateIntegerArray(int n) {
    List<Integer> a = new ArrayList<>();

    IntStream.range(0, n)
        .forEach(i -> a.add((int) (Math.random() * 1000)));

    return a;
  }
}
