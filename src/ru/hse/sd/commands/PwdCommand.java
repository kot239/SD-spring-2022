package ru.hse.sd.commands;

import java.nio.file.Paths;

import ru.hse.sd.enums.ReturnCode;


public class PwdCommand extends Command {

    public PwdCommand() {
        command = "pwd";
    }

    @Override
    public ReturnCode execute() {
        outputStream = Paths.get("").toAbsolutePath().toString();
        return ReturnCode.SUCCESS;
    }
}