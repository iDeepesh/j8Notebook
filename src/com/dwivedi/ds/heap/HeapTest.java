package com.dwivedi.ds.heap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;


public class HeapTest {
  private static final Map<Character, BiPredicate<Integer, Integer>> PMAP = new HashMap<>();
  private static final int SIZE = 10;

  static {
    PMAP.put('x', (x, y) -> x > y);
    PMAP.put('n', (x, y) -> x < y);
  }

  public static void main(String[] args) {
    System.out.print("What type of Heap you need, ma(x) or mi(n)? ");
    Scanner s = new Scanner(System.in);
    char c = s.next()
        .charAt(0);

    Heap hp = new Heap(HeapTest.PMAP.get(c), HeapTest.SIZE);

    IntStream.range(0, 25)
        .forEach(i -> {
          if (Math.random() > 0.25) {
            int n = (int) (Math.random() * 1000);
            System.out.println("Added: " + (hp.add(n) ? n : "nothing") + ". " + hp.toString());
          } else {
            Optional<Integer> r = hp.remove();
            System.out.println("Removed: " + (r.isPresent() ? r.get() : "nothing") + ". " + hp.toString());
          }
        });
  }
}
