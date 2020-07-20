package com.dwivedi.ds.tree;

public class Node<T> {
  public T t;
  public Node<T> left;
  public Node<T> right;

  @Override
  public String toString() {
    return "[" + t + "]";
  }
}