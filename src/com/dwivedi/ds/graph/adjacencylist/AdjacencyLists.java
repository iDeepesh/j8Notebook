package com.dwivedi.ds.graph.adjacencylist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Consumer;


public class AdjacencyLists {
  private static class NodeInfo<T> {
    AdjacencyList.Node<T> n;
    boolean visited;

    // for determining edge type
    int arrival;
    int departure;

    // for finding shortest path
    int distance;
    AdjacencyList.Node<T> parent;

    @Override
    public String toString() {
      return "{arr=" + arrival + ", dep=" + departure + ", dist=" + distance + ", parent=" + (parent == null ? "None"
          : parent.toStringShort()) + '}';
    }
  }

  private static class Info<T> {
    Map<Integer, NodeInfo<T>> nodeInfo;
    Map<EdgeType, Map<String, AdjacencyList.Edge<T>>> edgesByType;

    Info() {
      nodeInfo = new HashMap<>();
      edgesByType = new HashMap<>();
      edgesByType.put(EdgeType.Tree, new HashMap<>());
      edgesByType.put(EdgeType.Forward, new HashMap<>());
      edgesByType.put(EdgeType.Backward, new HashMap<>());
      edgesByType.put(EdgeType.Cross, new HashMap<>());
    }
  }

  private static enum EdgeType {
    Tree, Forward, Backward, Cross
  }

  static <T> void printGraph(AdjacencyList<T> g) {
    System.out.println("\nPrint graph structure:");
    g.graph.stream().forEach(System.out::println);
  }

  private static <T> void printGraphWithInfo(AdjacencyList<T> g, Info<T> info) {
    System.out.println("\nPrint graph with info:");
    g.graph.stream().forEach(n -> {
      System.out.println(n.toStringShort() + ", info=" + info.nodeInfo.get(n.id));
    });
  }

  private static <T> void printGraphDetailed(AdjacencyList<T> g, Info<T> info) {
    printGraphWithInfo(g, info);

    System.out.println("\nPrint edge types:");
    info.edgesByType.entrySet().stream().forEach(e -> {
      System.out.print(e.getKey() + " Edges: [ ");
      e.getValue().entrySet().stream().forEach(e1 -> {
        System.out.print(e1.getKey() + ", ");
      });
      System.out.println("]");
    });
  }

  static <T> String nodeDescription(AdjacencyList.Node<T> n) {
    return "[" + n.id + "," + n.data + "], ";
  }

  static <T> void depthFirstTraversal(AdjacencyList<T> g) {
    System.out.print("Depth first traversal: ");
    Info<T> info = new Info<>();

    depthFirstTraversalInternal(g.graph.get(0), info, 0);
    segregateEdgesByType(g, info);

    System.out.println();
    printGraphDetailed(g, info);
  }

  private static <T> void segregateEdgesByType(AdjacencyList<T> g, Info<T> info) {
    Map<String, AdjacencyList.Edge<T>> t = info.edgesByType.get(EdgeType.Tree);
    Map<Integer, NodeInfo<T>> ni = info.nodeInfo;

    g.graph.stream().forEach(n -> {
      n.edgeList.stream().forEach(e -> {
        NodeInfo<T> ui = ni.get(e.u.id);
        NodeInfo<T> vi = ni.get(e.v.id);

        if (ui != null && vi != null) {
          if (ui.arrival < vi.arrival && vi.departure < ui.departure && !t.containsKey(e.getHashKey())) {
            info.edgesByType.get(EdgeType.Forward).put(e.getHashKey(), e);
          } else if (ui.arrival > vi.arrival && ui.departure < vi.departure) {
            info.edgesByType.get(EdgeType.Backward).put(e.getHashKey(), e);
          } else if (vi.departure < ui.arrival) { //ToDo: Is this check enough
            info.edgesByType.get(EdgeType.Cross).put(e.getHashKey(), e);
          } else if (!t.containsKey(e.getHashKey())) {
            System.out.println("\n\n\nFAILURE --Something is wrong!! Control should never reach here!!\n\n");
          }
        } else {
          System.out.print("Node not part of connected graph: ");
          System.out.print(ui == null ? e.u.id : "");
          System.out.println(vi == null ? e.v.id : "");
        }
      });
    });
  }

  private static <T> int depthFirstTraversalInternal(AdjacencyList.Node<T> n, Info<T> info, int arrival) {
    if (n == null) {
      return arrival;
    }

    if (!info.nodeInfo.containsKey(n.id)) {
      info.nodeInfo.put(n.id, new NodeInfo<>());
    }

    NodeInfo<T> ni = info.nodeInfo.get(n.id);

    if (ni.visited) {
      return arrival;
    }

    int time = arrival + 1;

    System.out.print(nodeDescription(n));
    ni.visited = true;
    ni.arrival = time;

    Map<String, AdjacencyList.Edge<T>> treeEdges = info.edgesByType.get(EdgeType.Tree);

    for (AdjacencyList.Edge<T> e : n.edgeList) {
      int dep = depthFirstTraversalInternal(e.v, info, time);
      if (dep > time && !treeEdges.containsKey(e.getHashKey())) {
        treeEdges.put(e.getHashKey(), e);
      }
      time = dep;
    }
    //ToDo: can it use lambda?
//    n.edgeList.stream().forEach(e -> {
//      time = depthFirstTraversalInternal(e.v, info, time);//time;
//    });

    ni.departure = ++time;
    return time;
  }

  static <T> void breadthFirstTraversal(AdjacencyList<T> g) {
    Queue<AdjacencyList.Node<T>> q = new LinkedList<>();
    q.add(g.graph.get(0));

    Map<Integer, NodeInfo<T>> info = new HashMap<>();

    System.out.print("Breadth first traversal: ");
    while (q.peek() != null) {
      AdjacencyList.Node<T> n = q.poll();
      if (!info.containsKey(n.id)) {
        info.put(n.id, new NodeInfo<>());
      }

      NodeInfo<T> ni = info.get(n.id);
      if (ni.visited) {
        continue;
      }

      n.edgeList.stream().forEach(e -> q.add(e.v));

      System.out.print("[" + n.id + "," + n.data + "], ");

      ni.visited = true;
    }
    System.out.println();
  }

  static <T> void dijkstraShortestPath(AdjacencyList<T> g, AdjacencyList.Node<T> start) {
    Info<T> info = new Info<>();
    Map<Integer, NodeInfo<T>> nodeInfo = info.nodeInfo;
    PriorityQueue<NodeInfo<T>> q = new PriorityQueue<>((o1, o2) -> o1.distance - o2.distance);

    Consumer<AdjacencyList.Node<T>> addNodeInfo = n -> {
      if (!nodeInfo.containsKey(n.id)) {
        NodeInfo<T> ni = new NodeInfo<>();
        ni.n = n;
        ni.distance = Integer.MAX_VALUE;
        nodeInfo.put(n.id, ni);
      }
    };

    addNodeInfo.accept(start);
    nodeInfo.get(start.id).distance = 0;
    q.add(nodeInfo.get(start.id));

    while (!q.isEmpty()) {
      NodeInfo<T> uNi = q.poll();

      uNi.n.edgeList.forEach(e -> addNodeInfo.accept(e.v));

      uNi.n.edgeList.stream().filter(e -> !nodeInfo.get(e.v.id).visited).forEach(e -> {
        NodeInfo<T> vNi = nodeInfo.get(e.v.id);

        if ((uNi.distance + e.weight) < vNi.distance) {
          vNi.distance = uNi.distance + e.weight;
          vNi.parent = e.u;
        }
        q.add(vNi);
      });

      uNi.visited = true;
    }

    printGraphWithInfo(g, info);
  }

  static <T> List<AdjacencyList.Edge<T>> travellingSalesmanBruteForce(AdjacencyList<T> g) {
    Map<Integer, NodeInfo<T>> nodeInfo = new HashMap<>();
    List<AdjacencyList.Edge<T>> path =
        recTravellingSalesmanBruteForce(g.graph, g.graph.get(0), g.graph.get(0), new ArrayList<>());
    System.out.println("Cheapest path: " + path);
    return path;
  }

  private static <T> List<AdjacencyList.Edge<T>> recTravellingSalesmanBruteForce(List<AdjacencyList.Node<T>> graph,
      AdjacencyList.Node<T> s, AdjacencyList.Node<T> f, List<AdjacencyList.Edge<T>> traversed) {
    List<AdjacencyList.Edge<T>> path = new ArrayList<>();

    //base case
    if (traversed.size() == graph.size() - 1) {
      path.add(s.edgeList.stream().filter(e -> e.v.id == f.id).findFirst().get());
      return path;
    }

    //recursion
    int minRemainingCost = Integer.MAX_VALUE;

    for (AdjacencyList.Edge<T> e : s.edgeList) {
      if ((e.v.id == f.id) || (traversed.stream().anyMatch(e1 -> e1.v.id == e.v.id))) {
        continue;
      }

      traversed.add(e);
      List<AdjacencyList.Edge<T>> remainingPath = recTravellingSalesmanBruteForce(graph, e.v, f, traversed);
      traversed.remove(traversed.size() - 1);

      int remainingCost = getPathCost(remainingPath) + e.weight;

      if (remainingCost < minRemainingCost) {
        minRemainingCost = remainingCost;

        path.clear();
        path.add(e);
        path.addAll(remainingPath);
      }
    }

    return path;
  }

  static <T> List<AdjacencyList.Edge<T>> travellingSalesmanBranchAndBound(AdjacencyList<T> g) {
    Map<Integer, NodeInfo<T>> nodeInfo = new HashMap<>();
    List<AdjacencyList.Edge<T>> path =
        recTravellingSalesmanBranchAndBound(g.graph, g.graph.get(0), g.graph.get(0), new ArrayList<>(),
            new ArrayList<>());
    System.out.println("Cheapest path: " + path);
    return path;
  }

  private static <T> int getPathCost(List<AdjacencyList.Edge<T>> path) {
    int cost = 0;
    for (AdjacencyList.Edge<T> e : path) {
      cost = cost + e.weight;
    }

    return cost;
  }

  private static <T> List<AdjacencyList.Edge<T>> recTravellingSalesmanBranchAndBound(List<AdjacencyList.Node<T>> graph,
      AdjacencyList.Node<T> s, AdjacencyList.Node<T> f, List<AdjacencyList.Edge<T>> traversed,
      List<AdjacencyList.Edge<T>> cheapest) {
    List<AdjacencyList.Edge<T>> path = new ArrayList<>();

    //base case
    if (traversed.size() == graph.size() - 1) {
      path.add(s.edgeList.stream().filter(e -> e.v.id == f.id).findFirst().get());
      if (cheapest.isEmpty() || (getPathCost(traversed) + getPathCost(path) < getPathCost(cheapest))) {
        cheapest.clear();
        cheapest.addAll(traversed);
        cheapest.addAll(path);
      }
      return path;
    }

    //recursion
    int minRemainingCost = Integer.MAX_VALUE;
    int traversedCost = getPathCost(traversed);

    for (AdjacencyList.Edge<T> e : s.edgeList) {
      int cheapestCost = getPathCost(cheapest);
      if ((e.v.id == f.id) || (traversed.stream().anyMatch(e1 -> e1.v.id == e.v.id)) || (!cheapest.isEmpty()
          && traversedCost + e.weight > cheapestCost)) {
        continue;
      }

      traversed.add(e);
      List<AdjacencyList.Edge<T>> remainingPath =
          recTravellingSalesmanBranchAndBound(graph, e.v, f, traversed, cheapest);
      traversed.remove(traversed.size() - 1);

      if (!remainingPath.isEmpty()) {
        int remainingCost = getPathCost(remainingPath) + e.weight;

        if (remainingCost < minRemainingCost) {
          minRemainingCost = remainingCost;

          path.clear();
          path.add(e);
          path.addAll(remainingPath);
        }
      }
    }

    return path;
  }
}
