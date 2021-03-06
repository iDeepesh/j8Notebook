package com.dwivedi.ds.graph.adjacencylist;

import com.dwivedi.common.Person;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class AdjacencyListTest {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    System.out.println("Do you need a complete graph y/n: ");
    char type = s.nextLine().charAt(0);
    System.out.println("What type of node - (i)nteger or (p)erson: ");
    char node = s.nextLine().charAt(0);

    if (node == 'i') {
      Supplier<Integer> rand = () -> (int) (Math.random() * 1000);
      AdjacencyList<Integer> g = type == 'n' ? populateRandomGraph(10, rand) : populateCompleteGraph(10, rand);
      testGraph(g);
      if (type == 'y') {
        testTravellingSalesman(g, type);
      }
    } else if (node == 'p') {
      Supplier<Person> pRand = () -> new Person((int) (Math.random() * 100));
      AdjacencyList<Person> g = type == 'n' ? populateRandomGraph(10, pRand) : populateCompleteGraph(10, pRand);
      testGraph(g);
      if (type == 'y') {
        testTravellingSalesman(g, type);
      }
    } else {
      System.out.println("Choose wisely!!");
    }
  }

  static <T> void testTravellingSalesman(AdjacencyList<T> g, char choice) {
    if (choice == 'n') {
      return;
    }

    Function<List<AdjacencyList.Edge<T>>, Integer> cost = edges -> {
      int c = 0;
      for(AdjacencyList.Edge<T> e: edges){
        c += e.weight;
      }
      return c;
    };

    BiFunction<Function<AdjacencyList<T>, List<AdjacencyList.Edge<T>>>, AdjacencyList<T>, List<AdjacencyList.Edge<T>>>
        timed = (f, graph) -> {
      long s = System.nanoTime();
      List<AdjacencyList.Edge<T>> path = f.apply(graph);
      long e = System.nanoTime();
      System.out.println("Total cost: " + cost.apply(path));
      System.out.println("Time taken: " + (e - s) / 1000 + " micro seconds");
      return path;
    };

    System.out.println("\nTravelling salesman - Brute force:");
    timed.apply(AdjacencyLists::travellingSalesmanBruteForce, g);
    System.out.println("\nTravelling salesman - Branch and bound:");
    timed.apply(AdjacencyLists::travellingSalesmanBranchAndBound, g);
  }

  static <T> void testGraph(AdjacencyList<T> g) {
    AdjacencyLists.printGraph(g);
    AdjacencyLists.breadthFirstTraversal(g);
    AdjacencyLists.depthFirstTraversal(g);
    AdjacencyLists.dijkstraShortestPath(g, g.graph.get(0));
  }

  static <T> AdjacencyList<T> populateRandomGraph(int count, Supplier<T> rand) {
    AdjacencyList<T> g = createGraph(count, rand);

    List<Integer> index = IntStream.range(0, count).boxed().collect(Collectors.toList());

    g.graph.stream().forEach(n -> {
      int mark = (int) (Math.random() * count);
      mark = mark > 2 ? mark : 2;
      Collections.shuffle(index);

      IntStream.range(0, mark).forEach(i -> {
        if (g.graph.get(index.get(i)) != n) {
          AdjacencyList.Edge<T> e = new AdjacencyList.Edge<>();
          e.u = n;
          e.v = g.graph.get(index.get(i));
          e.weight = (int) (Math.random() * 10);
          n.edgeList.add(e);
        }
      });
    });

    return g;
  }

  static <T> AdjacencyList<T> populateCompleteGraph(int count, Supplier<T> rand) {
    AdjacencyList<T> g = createGraph(count, rand);

    g.graph.stream().forEach(u -> {
      g.graph.stream().filter(v -> u != v).forEach(v -> {
        AdjacencyList.Edge<T> e = new AdjacencyList.Edge<>();
        e.u = u;
        e.v = v;
        e.weight = (int) (Math.random() * 10);
        u.edgeList.add(e);
      });
    });

    return g;
  }

  static <T> AdjacencyList<T> createGraph(int count, Supplier<T> rand) {
    AdjacencyList<T> g = new AdjacencyList<>();

    IntStream.range(0, count).forEach(i -> {
      AdjacencyList.Node<T> n = new AdjacencyList.Node<>();
      n.id = i;
      n.data = rand.get();
      g.graph.add(n);
    });

    return g;
  }
}
