package com.dwivedi.ds.tree.bst;

import com.dwivedi.ds.tree.Node;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.IntStream;


public class BinarySearchTreeTest {

  public static class Person {
    public static Person youngest = new Person(0);
    public static Person oldest = new Person(100);

    public int age;

    public Person(int age) {
      this.age = age;
    }

    public String toString() {
      return "[age:" + age + "]";
    }
  }

  //ToDo: Cleanup raw usages of generics
  private static Map<Character, BinarySearchTree> bstMap = new HashMap<>();
  private static Map<Character, Supplier> randMap = new HashMap<>();

  static {
    bstMap.put('i', new BinarySearchTree<>(Integer::compare, () -> Integer.MIN_VALUE, () -> Integer.MAX_VALUE));
    bstMap.put('p',
        new BinarySearchTree<>(Comparator.comparingInt(x -> x.age), () -> Person.youngest, () -> Person.oldest));

    randMap.put('i', () -> (int) (Math.random() * 1000));
    randMap.put('p', () -> (new Person((int) (Math.random() * 100))));
  }

  public static void main(String[] args) {
    System.out.print("What type of tree you want - (i)nteger, (p)erson? ");
    Scanner s = new Scanner(System.in);
    Character ch = s.next().charAt(0);

    //ToDo: Cleanup raw usage of generics
    Node r = populateTree(10, bstMap.get(ch), randMap.get(ch));

    BinarySearchTree.printTree(r);

    System.out.print("\nIn order traversal: ");
    BinarySearchTree.inOrderTraversal(r);

    System.out.println();

    if (bstMap.get(ch).checkBST(r)) {
      System.out.println("It's BST, yay!!");
    } else {
      System.out.println("It's not a BST ;-(");
    }

    System.out.println("The min node is: " + bstMap.get(ch).findMin(r));
    System.out.println("The max node is: " + bstMap.get(ch).findMax(r));

//    System.out.println("What node to delete: ");
//    Scanner s = new Scanner(System.in);
//    Integer t = Integer.valueOf(s.nextLine());
//    r = bst.delete(r, t);
//    BinarySearchTree.printTree(r);
  }

  static <T> Node<T> populateTree(int n, BinarySearchTree<T> bst, Supplier<T> rand) {
    if (n < 0) {
      return null;
    }

    Node<T> r = new Node<>();
    r.t = rand.get();

    IntStream.range(0, n).forEach(i -> {
      Node<T> node = new Node<>();
      node.t = rand.get();

      bst.add(r, node);
    });

    return r;
  }
}
