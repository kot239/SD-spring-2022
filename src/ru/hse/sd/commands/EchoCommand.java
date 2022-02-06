package ru.hse.sd.commands;

import ru.hse.sd.enums.ReturnCode;

import java.util.List;

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