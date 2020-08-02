package com.dwivedi.algos.backtracking;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.IntStream;


public class QueensTest {
  public static void main(String[] args) {

    System.out.println("Size of chess board to lay queens: ");
    Scanner s = new Scanner(System.in);
    int n = Integer.parseInt(s.nextLine());
    System.out.println("Any one solution or all valid solutions - y/n: ");
    char c = s.nextLine().charAt(0);

    if(c == 'y'){
      List<Map<Integer, Integer>> sol = Queens.getAllBoards(n);
      System.out.println("Total number of valid boards: " + sol.size());
      sol.stream().forEach(QueensTest::printSolution);
      System.out.println("Total number of valid boards: " + sol.size());
    } else {
      Map<Integer, Integer> q = Queens.placeQueens(n);
      printSolution(q);
    }
  }

  static void printSolution(Map<Integer, Integer> q) {
    System.out.println(q);
    int n = q.size();

    Consumer<Integer> printRow = (r) -> {
      StringBuilder sb = new StringBuilder();
      IntStream.range(0, n).forEach(i -> {
        char c = i == q.get(r) ? 'X' : '.';
        sb.append(c);
        sb.append(' ');
      });
      System.out.println(sb.toString());
    };

    IntStream.range(0, n).forEach(printRow::accept);
  }
}
