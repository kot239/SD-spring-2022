package ru.hse.sd.cli.commands;

import java.util.List;

import ru.hse.sd.cli.enums.ReturnCode;

public class EchoCommand extends Command {
    private final List<String> args;

    public EchoCommand(List<String> args) {
        this.command = "echo";
        this.args = args;
    }

    @Override
    public ReturnCode execute() {
        outputStream = String.join(", ", args);
        return ReturnCode.SUCCESS;
    }
}