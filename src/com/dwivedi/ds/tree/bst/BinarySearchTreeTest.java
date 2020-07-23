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

  public static void main(String[] args) {
    System.out.print("What type of tree you want - (i)nteger, (p)erson? ");
    Scanner s = new Scanner(System.in);
    char ch = s.nextLine().charAt(0);

    if(ch == 'i'){
      BinarySearchTree<Integer> bst = new BinarySearchTree<>(Integer::compare, () -> Integer.MIN_VALUE, () -> Integer.MAX_VALUE);
      executeTreeTest(bst, () -> (int) (Math.random() * 1000), Integer::valueOf);
    } else if (ch == 'p') {
      BinarySearchTree<Person> bst = new BinarySearchTree<>(Comparator.comparingInt(x -> x.age), () -> Person.youngest, () -> Person.oldest);
      executeTreeTest(bst, () -> (new Person((int) (Math.random() * 100))), (str) -> new Person(Integer.parseInt(str)));
    } else {
      System.out.println("Invalid selection!");
    }
  }

  static <T> void executeTreeTest(BinarySearchTree<T> bst, Supplier<T> rand, Function<String, T> converter){
    // create a tree
    // print
    // check if BST
    // delete a node
    // change a node with random value
    // again check if BST

    Node<T> r = populateTree(10, bst, rand);

    BinarySearchTree.printTree(r);

    System.out.print("\nIn order traversal: ");
    BinarySearchTree.inOrderTraversal(r);

    System.out.println();

    if (bst.checkBST(r)) {
      System.out.println("It's BST, yay!!");
    } else {
      System.out.println("It's not a BST :-(");
    }

    System.out.println("The min node is: " + bst.findMin(r));
    System.out.println("The max node is: " + bst.findMax(r));

    System.out.print("What node to delete: ");
    Scanner s = new Scanner(System.in);
    r = bst.delete(r, converter.apply(s.nextLine()));
    BinarySearchTree.printTree(r);

    System.out.print("Choose a node to update: ");
    Node<T> f = bst.find(r, converter.apply(s.nextLine()));
    f.t = rand.get();
    System.out.println("Replacing node value with: " + f.t);

    BinarySearchTree.printTree(r);

    if (bst.checkBST(r)) {
      System.out.println("It's still a BST, yay!!");
    } else {
      System.out.println("It's not a BST anymore :-(");
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
