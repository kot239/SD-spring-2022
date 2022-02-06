package ru.hse.sd.commands;

import ru.hse.sd.enums.ReturnCode;

import java.util.List;

public class WcCommand extends Command {
  private final List<String> args;

  public WcCommand(List<String> args) {
    this.args = args;
  }

  @Override
  public ReturnCode execute() {
    return ReturnCode.SUCCESS;
  };
}