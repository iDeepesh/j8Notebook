package com.dwivedi.ds.heap;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiPredicate;


public class Heap {
  private Integer[] hp;
  private BiPredicate<Integer, Integer> p;
  private int len;

  Heap(BiPredicate<Integer, Integer> p, int size) {
    this.p = p;
    this.hp = new Integer[size];
  }

  boolean add(Integer n) {
    if (len == hp.length) {
      return false;
    }

    hp[len++] = n;
    balance(len - 1);

    return true;
  }

  Optional<Integer> peek() {
    return len > 0 ? Optional.of(hp[0]) : Optional.empty();
  }

  Optional<Integer> remove() {
    if (len <= 0) {
      return Optional.empty();
    }

    swap(0, --len);

    balance(0);

    return Optional.of(hp[len]);
  }

  private void balance(int i) {
    int f = (i - 1) / 2;
    if (i > 0 && p.test(hp[i], hp[f])) {
      swap(i, f);
      balance(f);
      return;
    }

    int l = i * 2 + 1;
    if (l < len && p.test(hp[l], hp[i])) {
      swap(l, i);
      balance(l);
    }

    int r = i * 2 + 2;
    if (r < len && p.test(hp[r], hp[i])) {
      swap(r, i);
      balance(r);
    }
  }

  private void swap(int i, int j) {
    Integer t = hp[i];
    hp[i] = hp[j];
    hp[j] = t;
  }

  public String toString() {
    return "Length: " + len + ", " + Arrays.toString(Arrays.copyOfRange(hp, 0, len)) + " -- " + Arrays.toString(
        Arrays.copyOfRange(hp, len, hp.length));
  }
}
