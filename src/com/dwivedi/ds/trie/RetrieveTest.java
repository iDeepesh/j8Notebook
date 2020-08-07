package com.dwivedi.ds.trie;

public class RetrieveTest {
  public static void main(String[] args) {
    Retrieve.Node r = Retrieve.Node.getNewRoot();
    Retrieve.add(r, "deepesh");
    Retrieve.add(r, "dwivedi");
    Retrieve.add(r, "deep");
    System.out.println("isMember - deepesh: " + Retrieve.isMember(r, "deepesh"));
    System.out.println("isMember - deep: " + Retrieve.isMember(r, "deep"));
    System.out.println("isMember - dee: " + Retrieve.isMember(r, "dee"));
    Retrieve.remove(r, "deep");
    System.out.println("isMember - deepesh: " + Retrieve.isMember(r, "deepesh"));
    System.out.println("isMember - deepes: " + Retrieve.isMember(r, "deepes"));
    System.out.println("isMember - deep: " + Retrieve.isMember(r, "deep"));
    System.out.println("isMember - dee: " + Retrieve.isMember(r, "dee"));
  }
}
