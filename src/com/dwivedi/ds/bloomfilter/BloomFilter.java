package com.dwivedi.ds.bloomfilter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;


public class BloomFilter<T> {
  List<Function<T, Integer>> funcs;
  byte[] bits;
  int size;

  BloomFilter(int size) {
    this.size = size;
    funcs = new ArrayList<>();
    bits = new byte[size];
    int k = 7; //assuming a distribution of 10 bits per element
    double d = Math.random();
    IntStream.range(0, k).forEach(i -> funcs.add(t -> Math.abs((t.toString() + d).hashCode())));
  }

  void add(T t) {
    funcs.forEach(f -> {
      int n = f.apply(t) % (size * 8);
      int i = n / 8;
      int j = n % 8;
      byte b = (byte) (1 << j);
      bits[i] = (byte)(b | bits[i]);
    });
  }

  boolean mayBePresent(T t) {
    for(Function<T, Integer> f: funcs) {
      int n = f.apply(t) % (size * 8);
      int i = n / 8;
      int j = n % 8;
      byte b = (byte)(1 << j);
      if((b & bits[i]) != b) {
        return false;
      }
    }

    return true;
  }
}
