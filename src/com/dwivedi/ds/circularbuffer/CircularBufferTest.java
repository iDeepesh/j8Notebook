package com.dwivedi.ds.circularbuffer;

import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;


public class CircularBufferTest {
  public static void main(String[] args) {
    System.out.print("What data structure you need (f)ifo or (l)ifo? ");
    Scanner s = new Scanner(System.in);
    char c = s.next()
        .charAt(0);
    int size = 3;
    Circular cq;
    if (c == 'l') {
      System.out.println("You selected lifo so getting stack with size of " + size);
      cq = new CircularStack(size);
    } else {
      System.out.println("You selected fifo so getting queue with size of " + size);
      cq = new CircularQueue(size);
    }

    IntStream.range(0, 10)
        .forEach(i -> {
          double r = Math.random();
          if (r < 0.5) {
            int n = (int) (r * 1000);
            System.out.print("Pushed: " + (cq.push(n) ? n : "nothing") + ". ");
          } else {
            Optional<Integer> n = cq.pop();
            System.out.print("Popped: " + (n.isPresent() ? n.get() : "nothing") + ". ");
          }
          System.out.println(cq);
        });
  }
}
