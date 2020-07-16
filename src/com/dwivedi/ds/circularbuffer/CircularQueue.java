package com.dwivedi.ds.circularbuffer;

import java.util.Arrays;
import java.util.Optional;


public class CircularQueue implements Circular {
  private int size;
  private Integer[] buffer;
  private int start;
  private int len;

  CircularQueue(int size) {
    this.size = size;
    this.buffer = new Integer[size];
  }

  public boolean push(Integer n) {
    if (len >= this.size) {
      return false;
    }

    int i = (start + len) % this.size;
    buffer[i] = n;
    len++;
    return true;
  }

  public Optional<Integer> pop() {
    if (len <= 0) {
      return Optional.empty();
    }

    Optional<Integer> r = Optional.of(buffer[start++]);
    len--;
    start = start % this.size;
    return r;
  }

  public String toString() {
    return "start: " + start + ", length: " + len + ", " + Arrays.toString(buffer);
  }
}
