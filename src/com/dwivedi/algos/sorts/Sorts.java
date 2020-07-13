package com.dwivedi.algos.sorts;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;


public class Sorts {
  static void bubbleSort(List<Integer> arr, Boolean asc) {
    System.out.print("Bubble sort - ");

    BiPredicate<Integer, Integer> p = SortUtil.getSortPredicate(arr, asc);

    IntStream.range(0, arr.size() - 1)
        .forEach(i -> IntStream.range(1, arr.size() - i)
            .forEach(j -> {
              if(p.test(j-1,j)) SortUtil.swapElements(arr, j-1, j);
            }));
  }

  static void selectionSort(List<Integer> arr, Boolean asc) {
    System.out.print("Selection sort - ");

    BiPredicate<Integer, Integer> p = SortUtil.getSortPredicate(arr, asc);

    IntStream.range(0, arr.size()-1)
        .forEach(i -> {
          int s = IntStream.range(i, arr.size())
              .reduce(i, (k, j)->{
                if(p.test(k, j)) k=j;
                return k;
              });
          SortUtil.swapElements(arr, i, s);
        });
  }

  static void insertionSort(List<Integer> arr, Boolean asc) {
    System.out.print("Insertion sort - ");

    BiPredicate<Integer, Integer> p = SortUtil.getSortPredicate(arr, asc);

    IntStream.range(1, arr.size())
        .forEach(i -> {
          int k = IntStream.range(0, i)
              .reduce(-1, (m ,j) -> {
                if(!p.test(j, i)) {
                  m=j;
                }
                return m;
              });

          IntStream.range(k+1, i)
              .forEach(n -> SortUtil.swapElements(arr, n, i));
        });
  }
}
