package com.dwivedi.ds.circularbuffer;

import java.util.Optional;


public interface Circular {
  boolean push(Integer n);

  Optional<Integer> pop();
}
