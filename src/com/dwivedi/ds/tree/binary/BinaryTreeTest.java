package com.dwivedi.ds.tree.binary;

import com.dwivedi.common.Person;
import com.dwivedi.ds.tree.Node;
import java.util.Scanner;
import java.util.function.Supplier;


public class BinaryTreeTest {
  public static void main(String[] args) {
    System.out.println("What type of tree you need - (i)nteger, (p)erson or (s)tring: ");
    Scanner s = new Scanner(System.in);
    char ch = s.nextLine().charAt(0);

    if (ch == 'i') {
      Node<Integer> r = BinaryTreeTest.populateTree(3, () -> (int) (Math.random() * 100));
      testTree(r);
    } else if (ch == 'p') {
      Node<Person> r = BinaryTreeTest.populateTree(3, () -> new Person((int) (Math.random() * 100)));
      testTree(r);
    } else if (ch == 's') {
      Node<String> r = BinaryTreeTest.populateTree(3, () -> "S-" + String.valueOf((int) (Math.random() * 1000)));
      testTree(r);
    } else {
      System.out.println("You don't look that dumb but who am I to judge!");
    }
  }

  private static <T> void testTree(Node<T> r) {

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

  private static <T> Node<T> populateTree(int depth, Supplier<T> rand) {
    if (depth < 0) {
      return null;
    }

    Node<T> n = new Node<>();
    n.t = rand.get();

    double d = Math.random();
    if (d > 0.2) {
      n.left = populateTree(depth - 1, rand);
    }

    if (d < 0.8) {
      n.right = populateTree(depth - 1, rand);
    }

    return n;
  }
}
