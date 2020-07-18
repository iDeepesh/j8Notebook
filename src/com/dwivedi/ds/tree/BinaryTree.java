package com.dwivedi.ds.tree;

import java.util.Optional;


public class BinaryTree {

  private static String PRINT_PREFIX =    "────────";
  private static String EMPTY_TAB_SPACE = "        ";
  private static String FULL_VERTICAL = "|";
  private static String HALF_VERTICAL = "└";

  static <T> void printTree(Node<T> n) {
    printTreeRecursive(Optional.of(n), "", "");
  }

  private static <T> void printTreeRecursive(Optional<Node<T>> r, String pref, String vPref) {
    if (!r.isPresent()) {
      return;
    }

    Node<T> n = r.get();

    System.out.println(pref + vPref + PRINT_PREFIX + n.t);

    String fPref = pref + EMPTY_TAB_SPACE + (n.left == null ? "" : FULL_VERTICAL);
    String fVert = n.left == null ? HALF_VERTICAL : "";
    printTreeRecursive(Optional.ofNullable(n.right), fPref, fVert);

    printTreeRecursive(Optional.ofNullable(n.left),pref + EMPTY_TAB_SPACE, HALF_VERTICAL);
  }

  static void preOrderTraversal(Node r) {
    if (r == null) {
      return;
    }

    System.out.print(r.t.toString() + ", ");
    preOrderTraversal(r.left);
    preOrderTraversal(r.right);
  }

  static void inOrderTraversal(Node r) {
    if (r == null) {
      return;
    }

    inOrderTraversal(r.left);
    System.out.print(r.t.toString() + ", ");
    inOrderTraversal(r.right);
  }

  static void postOrderTraversal(Node r) {
    if (r == null) {
      return;
    }

    postOrderTraversal(r.left);
    postOrderTraversal(r.right);
    System.out.print(r.t.toString() + ", ");
  }

  static void breadthFirstTraversal(Node r){
    System.out.println("To be implemented");
  }

  //Add - add a new node to tree
  //Delete - delete a node from tree
  //CheckBST - checks if the tree is bst or not
  //Find minimum value
  //Find maximum value
}
