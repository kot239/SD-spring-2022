package ru.hse.sd.commands;

import java.io.InputStream;
import java.io.OutputStream;

import ru.hse.sd.enums.ReturnCode;

public abstract class Command {
  protected String command;
  protected InputStream inputStream;
  protected OutputStream outputStream;

  public abstract ReturnCode execute();
}