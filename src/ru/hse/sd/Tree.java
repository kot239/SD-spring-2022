package ru.hse.sd;

import java.util.List;

public class Tree {
  private enum NodeType {
    VARIABLES,
    EQUALS,
    COMMAND
  }

  private static class Node {
    private final NodeType type;
    private final List<String> args;

    private Node(NodeType type, List<String> args) {
      this.type = type;
      this.args = args;
    }
  }
}