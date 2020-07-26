package com.dwivedi.ds.graph.adjacencylist;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


public class AdjacencyLists {
  private static class NodeInfo<T> {
    AdjacencyList.Node<T> n;
    boolean visited;
    int arrival;
    int departure;

    @Override
    public String toString() {
      return "{arr=" + arrival + ", dep=" + departure + '}';
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

  static <T> void printTree(AdjacencyList<T> g) {
    System.out.println("\nPrint graph structure:");
    g.graph.stream()
        .forEach(System.out::println);
  }

  private static <T> void printTreeDetailed(AdjacencyList<T> g, Info<T> info) {
    System.out.println("\nPrint graph traversal:");
    g.graph.stream()
        .forEach(n -> {
          System.out.println(n.toStringShort() + ", info=" + info.nodeInfo.get(n.id));
        });

    info.edgesByType.entrySet()
        .stream()
        .forEach(e -> {
          System.out.print(e.getKey() + " Edges: [ ");
          e.getValue()
              .entrySet()
              .stream()
              .forEach(e1 -> {
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
    printTreeDetailed(g, info);
  }

  private static <T> void segregateEdgesByType(AdjacencyList<T> g, Info<T> info) {
    Map<String, AdjacencyList.Edge<T>> t = info.edgesByType.get(EdgeType.Tree);
    Map<Integer, NodeInfo<T>> ni = info.nodeInfo;

    g.graph.stream()
        .forEach(n -> {
          n.edgeList.stream()
              .forEach(e -> {
                NodeInfo<T> ui = ni.get(e.u.id);
                NodeInfo<T> vi = ni.get(e.v.id);

                if (ui != null && vi != null) {
                  if (ui.arrival < vi.arrival && vi.departure < ui.departure && !t.containsKey(e.getHashKey())) {
                    info.edgesByType.get(EdgeType.Forward).put(e.getHashKey(), e);
                  } else if (ui.arrival > vi.arrival && ui.departure < vi.departure) {
                    info.edgesByType.get(EdgeType.Backward).put(e.getHashKey(), e);
                  } else if (vi.departure < ui.arrival) {
                    info.edgesByType.get(EdgeType.Cross).put(e.getHashKey(), e);
                  } else if (!t.containsKey(e.getHashKey())) {
                    System.out.println("\n\n\nFAILURE --Something is wrong!! Control should never reach here!!\n\n");
                  }
                } else {
                  System.out.print("Node not part of connected graph: ");
                  System.out.print(ui == null? e.u.id: "");
                  System.out.println(vi == null? e.v.id: "");
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
    //ToDo: can it use Lambda?
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

      n.edgeList.stream()
          .forEach(e -> q.add(e.v));

      System.out.print("[" + n.id + "," + n.data + "], ");

      ni.visited = true;
    }
    System.out.println();
  }
}
