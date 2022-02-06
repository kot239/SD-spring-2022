package ru.hse.sd.commands;

import java.util.List;

import ru.hse.sd.enums.ReturnCode;


public class CatCommand extends Command {
  private final List<String> args;

  public CatCommand(List<String> args) {
    this.args = args;
  }

  @Override
  public ReturnCode execute() {
    return ReturnCode.SUCCESS;
  };
}