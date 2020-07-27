package com.dwivedi.ds.tree.avl;

import com.dwivedi.ds.tree.Node;
import com.dwivedi.ds.tree.bst.BinarySearchTree;
import java.util.Comparator;
import java.util.function.Supplier;


public class AvlTree<T> extends BinarySearchTree<T> {
  AvlTree(Comparator<T> c, Supplier<T> min, Supplier<T> max) {
    super(c, min, max);
  }

  @Override
  public Node<T> add(Node<T> r, Node<T> n) {
    if (r == null) {
      n.height = 0;
      return n;
    }

    if (c.compare(r.t, n.t) < 0) {
      r.right = add(r.right, n);
    } else {
      r.left = add(r.left, n);
    }

    recalcHeight(r);

    return balance(r);
  }

  @Override
  public Node<T> delete(Node<T> r, T t) {
    if (r == null) {
      return null;
    }

    int eq = c.compare(r.t, t);

    if (eq < 0) {
      r.right = delete(r.right, t);
    } else if (eq > 0) {
      r.left = delete(r.left, t);
    } else {
      if (r.right == null) {
        r = r.left;
      } else if (r.left == null) {
        r = r.right;
      } else {
        Node<T> min = findMin(r.right);
        r.t = min.t;
        r.right = delete(r.right, min.t);
      }
    }

    recalcHeight(r);
    return balance(r);
  }

  private Node<T> balance(Node<T> r) {
    if (r == null) {
      return null;
    }

    if (Math.abs(r.balFact) <= 1) {
      return r;
    }

    if (r.balFact > 0) {
      if (r.left.balFact < 0) {
        r.left = leftRotate(r.left);
      }
      r = rightRotate(r);
    } else {
      if (r.right.balFact > 0) {
        r.right = rightRotate(r.right);
      }
      r = leftRotate(r);
    }

    return r;
  }

  private Node<T> leftRotate(Node<T> r) {
    Node<T> nR = r.right;
    r.right = nR.left;
    recalcHeight(r);
    nR.left = r;
    recalcHeight(nR);

    return nR;
  }

  private Node<T> rightRotate(Node<T> r) {
    Node<T> nR = r.left;
    r.left = nR.right;
    recalcHeight(r);
    nR.right = r;
    recalcHeight(nR);

    return nR;
  }

  private int recalcHeight(Node<T> r) {
    if (r == null) {
      return -1;
    }

    int hl = height(r.left);
    int hr = height(r.right);

    r.height = (hl > hr ? hl : hr) + 1;
    r.balFact = hl - hr;

    return r.height;
  }

  private int height(Node<T> r) {
    return r == null ? -1 : r.height;
  }

  boolean checkAVL(Node<T> r) {
    if (r == null) {
      return true;
    }

    if (r.balFact > 1 || r.balFact < -1) {
      return false;
    }

    return checkAVL(r.left) && checkAVL(r.right);
  }
}
