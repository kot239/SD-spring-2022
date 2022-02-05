package ru.hse.sd.commands;

public abstract class Command {
  protected final String command;
  protected final InputStream inputStream;
  protected final OutputStream outputStream;

  public abstract ReturnCode execute();
}