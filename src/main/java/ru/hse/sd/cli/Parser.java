package ru.hse.sd.cli;

import java.util.List;

public class Parser {
    private final Tree tree;
    private final List<String> tokens;

    public Parser(Tree tree, List<String> tokens) {
        this.tree = tree;
        this.tokens = tokens;
    }

    public Tree parse() {
        return new Tree();
    }
}