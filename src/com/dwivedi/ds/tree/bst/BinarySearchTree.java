package com.dwivedi.ds.tree.bst;

import com.dwivedi.ds.tree.BinaryTree;
import com.dwivedi.ds.tree.Node;
import java.util.Comparator;
import java.util.function.Supplier;


public class BinarySearchTree<T> extends BinaryTree<T> {

  private Comparator<T> c;
  private Supplier<T> minSupplier;
  private Supplier<T> maxSupplier;

  public BinarySearchTree(Comparator<T> c, Supplier<T> min, Supplier<T> max) {
    this.c = c;
    this.minSupplier = min;
    this.maxSupplier = max;
  }

  Node<T> add(Node<T> r, Node<T> n) {
    if (r == null) {
      return n;
    }

    if (c.compare(r.t, n.t) < 0) {
      r.right = add(r.right, n);
    } else {
      r.left = add(r.left, n);
    }

    return r;
  }

  Node<T> delete(Node<T> r, T t) {
    if (r == null) {
      return r;
    }

    int cRes = c.compare(r.t, t);

    if (cRes == 0) {
      if (r.left == null && r.right == null) {
        return null;
      } else if (r.left == null) {
        return r.right;
      } else if (r.right == null) {
        return r.left;
      } else {
        Node<T> max = findMax(r.left);
        swapValues(r, max);
        r.left = delete(r.left, t);
      }
    } else if (cRes > 0) {
      r.left = delete(r.left, t);
    } else {
      r.right = delete(r.right, t);
    }

    return r;
  }

  void swapValues(Node<T> n1, Node<T> n2) {
    T t = n1.t;
    n1.t = n2.t;
    n2.t = t;
  }

//  Node<T> find(Node<T> r, T value) {
//    if (r == null) {
//      return r;
//    }
//
//    int cRes = c.compare(r.t, value);
//
//    if (cRes == 0) {
//      return r;
//    }
//
//    if (cRes < 0) {
//      return find(r.left, value);
//    } else {
//      return find(r.right, value);
//    }
//  }

  Node<T> findMax(Node<T> r) {
    if (r == null) {
      return r;
    }

    if (r.right == null) {
      return r;
    } else {
      return findMax(r.right);
    }
  }

  Node<T> findMin(Node<T> r) {
    if (r == null) {
      return r;
    }

    if (r.left == null) {
      return r;
    } else {
      return findMin(r.left);
    }
  }

  boolean checkBST(Node<T> r) {
    return checkBSTInternal(r, minSupplier.get(), maxSupplier.get());
  }

  private boolean checkBSTInternal(Node<T> r, T min, T max) {
    if (r == null) {
      return true;
    }

    if (c.compare(r.t, max) > 0 || c.compare(r.t, min) < 0) {
      return false;
    }

    if (r.left != null && !checkBSTInternal(r.left, min, r.t)) {
      return false;
    }

    if (r.right != null && !checkBSTInternal(r.right, r.t, max)) {
      return false;
    }

    return true;
  }
}
