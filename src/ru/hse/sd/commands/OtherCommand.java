package ru.hse.sd.commands;

import ru.hse.sd.commands.Command;

import java.utils.List;

public class OtherCommand extends Command {
  private final List<String> args;

  @Override
  public ReturnCode execute() {
    return ReturnCode();
  };
}