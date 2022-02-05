package ru.hse.sd;

import java.util.List;

public class Tree {
  private enum NodeType {
    VARIABLES,
    EQUALS,
    COMMAND
  }

  private class Node {
    private final NodeType type;
    private final List<String> args;

    public void do() {
      return;
    };
  }
}