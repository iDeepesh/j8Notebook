package com.dwivedi.ds.bloomfilter;

import com.dwivedi.common.Person;
import java.util.function.Supplier;
import java.util.stream.IntStream;


public class BloomFilterTest {
  public static void main(String[] args) {
    testBloomFilter(() -> (int)(Math.random()*1000));
    testBloomFilter(() -> new Person((int)(Math.random()*1000)));
  }

  private static <T> void testBloomFilter(Supplier<T> rand) {
    BloomFilter<T> bf = new BloomFilter<>(1024);

    T t1 = rand.get();
    bf.add(t1);

    IntStream.range(0, 100)
        .forEach(i -> bf.add(rand.get()));

    T t2 = rand.get();

    System.out.println("Checking for first element t1=" + t1 + ": " + bf.mayBePresent(t1));
    System.out.println("Checking for random element t2=" + t2 + ": " + bf.mayBePresent(t2));
  }
}
