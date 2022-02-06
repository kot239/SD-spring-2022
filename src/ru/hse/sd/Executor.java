package ru.hse.sd;

import ru.hse.sd.commands.Command;

import java.io.InputStream;
import java.util.List;

public class Executor {
  private Parser parser;
  private Memory memory;
  private InputStream inputStream;
  private List<Command> commands;
  
  public void execute(String input) {
    return;
  };
}