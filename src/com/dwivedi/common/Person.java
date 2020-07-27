package com.dwivedi.common;

import com.dwivedi.ds.tree.bst.BinarySearchTreeTest;


public class Person {
  public static Person YOUNGEST = new Person(0);
  public static Person OLDEST = new Person(100);

  public int age;

  public Person(int age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "[age:" + age + "]";
  }
}
