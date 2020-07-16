package com.dwivedi.ds.circularbuffer;

import java.util.Arrays;
import java.util.Optional;


public class CircularStack implements Circular {
  private int size;
  private Integer[] buffer;
  private int index;

  CircularStack(int size) {
    this.size = size;
    buffer = new Integer[size];
    index = -1;
  }

  public boolean push(Integer n) {
    if (index + 1 >= size) {
      return false;
    }

    buffer[++index] = n;
    return true;
  }

  public Optional<Integer> pop() {
    if (index < 0) {
      return Optional.empty();
    }

    return Optional.of(buffer[index--]);
  }

  public String toString() {
    return "index: " + index + ", " + Arrays.toString(buffer);
  }
}
