package com.dwivedi.ds.tree;

public class Node<T> {
  public T t;
  public Node<T> left;
  public Node<T> right;

  // for AVL Tree only
  public int height = Integer.MIN_VALUE;
  public int balFact;

  @Override
  public String toString() {
    //choose if the height/balance factor is used or not
    return (height < 0) ? "[t=" + t +"]"
        : "[t=" + t + ",ht=" + height + ",balFact=" + balFact + "]";
  }
}