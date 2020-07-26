package com.dwivedi.ds.graph.adjacencylist;

import java.util.ArrayList;
import java.util.List;


public class AdjacencyList<T> {

  public static class Edge<T> {
    Node<T> u;
    Node<T> v;

    @Override
    public String toString() {
      return u.id + "-" + v.id;
    }

    public String getHashKey() {
      return String.valueOf(u.id) + "-" + String.valueOf(v.id);
    }
  }

  public static class Node<T> {
    int id;
    T data;
    List<Edge<T>> edgeList;

    @Override
    public String toString() {
      return id + " ==> data={" + data + "}, Edges=" + edgeList;
    }

    public String toStringShort() {
      return id + " ==> data={" + data + "}";
    }

    Node() {
      edgeList = new ArrayList<>();
    }
  }

  List<Node<T>> graph;

  AdjacencyList() {
    graph = new ArrayList<>();
  }
}
