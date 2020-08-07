package com.dwivedi.ds.trie;

import java.util.Optional;


public class Retrieve {
  public static class Node {
    boolean leaf;
    char key;
    Node[] children = new Node[26];

    Node(char c) {
      this.key = c;
    }

    private Node() {
    }

    Optional<Node> getChild(char c) {
      return Optional.ofNullable(children[c - 'a']);
    }

    boolean hasChildren() {
      for (Node n : children) {
        if (n != null) {
          return true;
        }
      }
      return false;
    }

    static Node getNewRoot() {
      return new Node();
    }
  }

  static void remove(Node r, String s) {
    if (r == null) {
      return;
    }

    recRemove(r, s.toCharArray(), 0);
  }

  static boolean recRemove(Node r, char[] a, int i) {
    if (r == null) {
      return false;
    }

    if (i == a.length) {
      r.leaf = false;
      return r.hasChildren();
    }

    boolean b = recRemove(r.getChild(a[i]).get(), a, i + 1);
    if (b) {
      return true;
    } else {
      r.children[a[i] - 'a'] = null;
      return r.leaf || r.hasChildren();
    }
  }

  static void add(Node r, String s) {
    if (r == null) {
      return;
    }

    Node n = r;
    for (char c : s.toCharArray()) {
      if (!n.getChild(c).isPresent()) {
        n.children[c - 'a'] = new Node(c);
      }

      n = n.getChild(c).get();
    }

    n.leaf = true;
  }

  static boolean isMember(Node r, String s) {
    if (r == null) {
      return false;
    }

    Node n = r;
    for (char c : s.toCharArray()) {
      if (!n.getChild(c).isPresent()) {
        return false;
      }
      n = n.getChild(c).get();
    }

    return n.leaf;
  }
}
