package com.dwivedi.algos.combinatorialoptimization;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;


public class KnapsackTest {
  public static class Item {
    public int cost;
    public int value;

    @Override
    public String toString() {
      return "{" + "c:" + cost + ",v:" + value + '}';
    }
  }

  public static void main(String[] args) {
    Supplier<Item> s = () -> {
      Item i = new Item();
      i.cost = (int) (Math.random() * 100);
      i.value = (int) (Math.random() * 100);
      return i;
    };

    fillKnapsackTest(s, (i) -> i.cost, (i) -> i.value);
  }

  private static <T> void fillKnapsackTest(Supplier<T> rand, Function<T, Integer> cost, Function<T, Integer> value) {
    List<T> items = new ArrayList<>();

    IntStream.range(0, 25).forEach(i -> {
      items.add(rand.get());
    });

    int money = (int) (Math.random() * 1000);

    System.out.println("Available money: " + money);
    System.out.println("Available items: " + items);

    Knapsack<T> ks = new Knapsack<>(cost, value);

    BiConsumer<BiFunction<Integer, List<T>, List<T>>, String> timed = (f, prefix) -> {
      long s = System.currentTimeMillis();
      List<T> knapsack = f.apply(money, items);
      long e = System.currentTimeMillis();
      System.out.println("\n" + prefix + "Time taken: " + (e - s) + " milli seconds.");

      int totalCost = 0, totalValue = 0;
      for (T t : knapsack) {
        totalCost += cost.apply(t);
        totalValue += value.apply(t);
      }
      System.out.println(prefix + ": Total cost=" + totalCost + ", value=" + totalValue);
      System.out.println(knapsack);
    };

    timed.accept(ks::fillKnapsackBruteForce, "Bruteforce");
    timed.accept(ks::fillKnapsackDynamicProgramming, "Dynamic Programming");
  }
}
