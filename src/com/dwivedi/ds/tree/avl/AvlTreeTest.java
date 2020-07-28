package com.dwivedi.ds.tree.avl;

import com.dwivedi.common.Person;
import com.dwivedi.ds.tree.Node;
import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;


public class AvlTreeTest {
  public static void main(String[] args) {

    System.out.println("What is the data type for tree nodes - (i)nteger or (p)erson: ");
    Scanner s = new Scanner(System.in);
    char ch = s.nextLine().charAt(0);

    if (ch == 'i') {
      AvlTree<Integer> avl = new AvlTree<>(Integer::compare, () -> Integer.MIN_VALUE, () -> Integer.MAX_VALUE);
      Node<Integer> r = populateAvlTree(avl, () -> (int) (Math.random() * 1000));
      testAvlTree(avl, r, Function.identity());
    } else if (ch == 'p') {
      Comparator<Person> c = (o1, o2) -> {
        return Integer.compare(o1.age, o2.age);
      };
      AvlTree<Person> avl = new AvlTree<>(c, () -> Person.YOUNGEST, () -> Person.OLDEST);
      Node<Person> r = populateAvlTree(avl, () -> new Person((int) (Math.random() * 100)));
      testAvlTree(avl, r, i -> new Person(i));
    } else {
      System.out.println("You don't look this dumb but who am I to judge!");
    }
  }

  static <T> void testAvlTree(AvlTree<T> avl, Node<T> r, Function<Integer, T> convertor) {
    AvlTree.printTree(r);
    System.out.println(avl.checkBST(r) ? "It's BST, yay!!" : "Not a BST, better luck next time!");
    System.out.println(
        avl.checkAVL(r) ? "It's well balanced BST, yay!!" : "Not a balanced BST, better luck next time!");

    System.out.println("Pick a value to delete: ");
    Scanner s = new Scanner(System.in);
    T d = convertor.apply(s.nextInt());
    r = avl.delete(r, d);

    AvlTree.printTree(r);
    System.out.println(avl.checkBST(r) ? "It's still BST, yay!!" : "Not a BST anymore, better luck next time!");
    System.out.println(
        avl.checkAVL(r) ? "It's well balanced BST again, yay!!" : "Not a balanced BST anymore, better luck next time!");
  }

  static <T> Node<T> populateAvlTree(AvlTree<T> avl, Supplier<T> rand) {
    //java 10+ ==> use var type
    // var root = new Object(Node<T> value)
    // IntStream.range(0, 25).forEach(i -> root.value = avl.add(root.value, rand.get()));

    AtomicReference<Node<T>> root = new AtomicReference<>();
    IntStream.range(0, 25).forEach(i -> root.set(avl.add(root.get(), rand.get())));
    return root.get();
  }
}
