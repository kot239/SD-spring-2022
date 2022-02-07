package main.java.ru.hse.sd.cli.commands;

import main.java.ru.hse.sd.cli.enums.ReturnCode;


public class ExitCommand extends Command {
    @Override
    public ReturnCode execute() {
        return ReturnCode.SUCCESS;
    }
}