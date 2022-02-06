package ru.hse.sd.cli.commands;

import java.nio.file.Paths;

import ru.hse.sd.cli.enums.ReturnCode;


public class PwdCommand extends Command {

    public PwdCommand() {
        this.command = "pwd";
    }

    @Override
    public ReturnCode execute() {
        outputStream = Paths.get("").toAbsolutePath().toString();
        return ReturnCode.SUCCESS;
    }
}