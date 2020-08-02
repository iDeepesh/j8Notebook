package com.dwivedi.algos.combinatorialoptimization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;


public class Knapsack<T> {
  Function<T, Integer> c;
  Function<T, Integer> v;

  Knapsack(Function<T, Integer> c, Function<T, Integer> v) {
    this.c = c;
    this.v = v;
  }

  List<T> fillKnapsackBruteForce(int money, List<T> items) {
    if (money <= 0 || items.isEmpty()) {
      return new ArrayList<>();
    }

    List<T> withList = new ArrayList<>();
    AtomicInteger with = new AtomicInteger();

    if (money >= c.apply(items.get(0))) {
      withList.add(items.get(0));
      withList.addAll(fillKnapsackBruteForce(money - c.apply(items.get(0)), items.subList(1, items.size())));
      withList.stream().forEach(i -> with.set(with.get() + v.apply(i)));
    }

    List<T> withoutList = fillKnapsackBruteForce(money, items.subList(1, items.size()));
    AtomicInteger without = new AtomicInteger();
    withoutList.stream().forEach(i -> without.set(without.get() + v.apply(i)));

    return with.get() > without.get() ? withList : withoutList;
  }

  List<T> fillKnapsackDynamicProgramming(int money, List<T> items) {
    Map<String, List<T>> cache = new HashMap<>();

    BiFunction<Integer, List<T>, String> hash = (m, it) -> {
      return String.valueOf(m.hashCode() + it.hashCode());
    };

    return fillKnapsackDynamicProgrammingInternal(money, items, cache, hash);
  }

  private List<T> fillKnapsackDynamicProgrammingInternal(int money, List<T> items, Map<String, List<T>> cache,
      BiFunction<Integer, List<T>, String> hash) {
    if (money <= 0 || items.isEmpty()) {
      return new ArrayList<>();
    }

    String h = hash.apply(money, items);
    if (cache.containsKey(h)) {
      return cache.get(h);
    }

    List<T> withList = new ArrayList<>();
    if (money >= c.apply(items.get(0))) {
      withList.add(items.get(0));
      withList.addAll(
          fillKnapsackDynamicProgrammingInternal(money - c.apply(items.get(0)), items.subList(1, items.size()), cache,
              hash));
    }

    List<T> withoutList = fillKnapsackDynamicProgrammingInternal(money, items.subList(1, items.size()), cache, hash);

    AtomicInteger with = new AtomicInteger();
    withList.forEach(i -> with.set(with.get() + v.apply(i)));

    AtomicInteger without = new AtomicInteger();
    withoutList.forEach(i -> without.set(without.get() + v.apply(i)));

    if (with.get() > without.get()) {
      cache.put(h, withList);
      return withList;
    } else {
      cache.put(h, withoutList);
      return withoutList;
    }
  }
}
