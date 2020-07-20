package com.dwivedi.ds.tree;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;


public class BinaryTree <T> {

  private static String PRINT_PREFIX = "─────────";
  private static String PRINT_PREFIX_L = "─l───────";
  private static String PRINT_PREFIX_R = "─r───────";
  private static String EMPTY_TAB_SPACE = "         ";
  private static String FULL_VERTICAL = "|";
  private static String HALF_VERTICAL = "└";

  public static <T> void printTree(Node<T> n) {
    printTreeRecursive(Optional.of(n), "", "", PRINT_PREFIX);
  }

  private static <T> void printTreeRecursive(Optional<Node<T>> r, String pref, String vPref, String printPref) {
    if (!r.isPresent()) {
      return;
    }

    Node<T> n = r.get();

    System.out.println(pref + vPref + printPref + n);

    String fPref = pref + EMPTY_TAB_SPACE + (n.left == null ? "" : FULL_VERTICAL);
    String fVert = n.left == null ? HALF_VERTICAL : "";
    printTreeRecursive(Optional.ofNullable(n.right), fPref, fVert, PRINT_PREFIX_R);

    printTreeRecursive(Optional.ofNullable(n.left), pref + EMPTY_TAB_SPACE, HALF_VERTICAL, PRINT_PREFIX_L);
  }

  public static <T> void preOrderTraversal(Node<T> r) {
    if (r == null) {
      return;
    }

    System.out.print(r + ", ");
    preOrderTraversal(r.left);
    preOrderTraversal(r.right);
  }

  public static <T> void inOrderTraversal(Node<T> r) {
    if (r == null) {
      return;
    }

    inOrderTraversal(r.left);
    System.out.print(r + ", ");
    inOrderTraversal(r.right);
  }

  public static <T> void postOrderTraversal(Node<T> r) {
    if (r == null) {
      return;
    }

    postOrderTraversal(r.left);
    postOrderTraversal(r.right);
    System.out.print(r + ", ");
  }

  public static <T> void breadthFirstTraversal(Node<T> r) {
    Queue<Node> q = new LinkedList<>();
    q.add(r);

    while (q.peek() != null) {
      Node n = q.remove();
      System.out.print(n + ", ");
      if (n.left != null) {
        q.add(n.left);
      }
      if (n.right != null) {
        q.add(n.right);
      }
    }
  }
}
