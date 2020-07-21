package com.dwivedi.ds.tree.bst;

import com.dwivedi.ds.tree.Node;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
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
  private static Map<Character, Function<String, Object>> converterMap = new HashMap<>();

  static {
    bstMap.put('i', new BinarySearchTree<>(Integer::compare, () -> Integer.MIN_VALUE, () -> Integer.MAX_VALUE));
    bstMap.put('p',
        new BinarySearchTree<>(Comparator.comparingInt(x -> x.age), () -> Person.youngest, () -> Person.oldest));

    randMap.put('i', () -> (int) (Math.random() * 1000));
    randMap.put('p', () -> (new Person((int) (Math.random() * 100))));

    converterMap.put('i', Integer::valueOf);
    converterMap.put('p', (s) -> new Person(Integer.valueOf(s)));
  }

  public static void main(String[] args) {
    // create a tree
    // print
    // check if BST
    // delete a node
    // change a node with random value
    // again check if BST

    System.out.print("What type of tree you want - (i)nteger, (p)erson? ");
    Scanner s = new Scanner(System.in);
    Character ch = s.nextLine().charAt(0);

    //ToDo: Cleanup raw usage of generics
    Node r = populateTree(10, bstMap.get(ch), randMap.get(ch));

    BinarySearchTree.printTree(r);

    System.out.print("\nIn order traversal: ");
    BinarySearchTree.inOrderTraversal(r);

    System.out.println();

    if (bstMap.get(ch).checkBST(r)) {
      System.out.println("It's BST, yay!!");
    } else {
      System.out.println("It's not a BST :-(");
    }

    System.out.println("The min node is: " + bstMap.get(ch).findMin(r));
    System.out.println("The max node is: " + bstMap.get(ch).findMax(r));

    System.out.print("What node to delete: ");
    r = bstMap.get(ch).delete(r, converterMap.get(ch).apply(s.nextLine()));
    BinarySearchTree.printTree(r);

    System.out.print("Choose a value to change: ");
    Node f = bstMap.get(ch).find(r, converterMap.get(ch).apply(s.nextLine()));
    f.t = randMap.get(ch).get();
    System.out.println("Replacing it with: " + f.t);

    BinarySearchTree.printTree(r);

    if (bstMap.get(ch).checkBST(r)) {
      System.out.println("It's BST, yay!!");
    } else {
      System.out.println("It's not a BST :-(");
    }
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
