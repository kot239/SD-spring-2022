package ru.hse.sd;

import ru.hse.sd.commands.Command;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Executor {
    private Parser parser;
    private Memory memory;
    private InputStream inputStream;
    private List<Command> commands;

    public void execute(String input) {
        this.parser = new Parser();
        return;
    }
}