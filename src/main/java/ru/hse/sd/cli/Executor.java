package ru.hse.sd.cli;

import java.io.InputStream;
import java.util.List;

import ru.hse.sd.cli.commands.Command;

public class Executor {
    private Parser parser;
    private Memory memory;
    private InputStream inputStream;
    private List<Command> commands;

    public void execute(String input) {
        return;
    }
}