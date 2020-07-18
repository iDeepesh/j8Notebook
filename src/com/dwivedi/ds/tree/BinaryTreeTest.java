package com.dwivedi.ds.tree;

public class BinaryTreeTest {
  public static void main(String[] args) {
    Node r = BinaryTreeTest.populateIntegerTree(3);
//    Node r = BinaryTreeTest.populateStringTree(3);
    BinaryTree.printTree(r);

    System.out.println();
    System.out.print("Pre order traversal: ");
    BinaryTree.preOrderTraversal(r);

    System.out.print("\nIn order traversal: ");
    BinaryTree.inOrderTraversal(r);

    System.out.print("\nPost order traversal: ");
    BinaryTree.postOrderTraversal(r);

    System.out.print("\nBreadth first traversal: ");
    BinaryTree.breadthFirstTraversal(r);
    System.out.println();
  }

  static Node<Integer> populateIntegerTree(int depth) {
    if (depth < 0) {
      return null;
    }

    Node<Integer> n = new Node<>();
    n.t = (int) (Math.random() * 1000);

    double d = Math.random();
    if (d > 0.2) {
      n.left = populateIntegerTree(depth - 1);
    }

    if (d < 0.8) {
      n.right = populateIntegerTree(depth - 1);
    }

    return n;
  }

  static Node<String> populateStringTree(int depth) {
    if (depth < 0) {
      return null;
    }

    Node<String> n = new Node<>();
    n.t = "S-" + String.valueOf((int) (Math.random() * 1000));

    double d = Math.random();
    if (d > 0.2) {
      n.left = populateStringTree(depth - 1);
    }

    if (d < 0.8) {
      n.right = populateStringTree(depth - 1);
    }

    return n;
  }
}
