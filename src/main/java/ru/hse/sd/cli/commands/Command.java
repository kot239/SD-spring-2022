package ru.hse.sd.cli.commands;

import ru.hse.sd.cli.enums.ReturnCode;

public abstract class Command {
    protected String command;
    protected String inputStream;
    protected String outputStream = "";
    protected String errorStream;

    public abstract ReturnCode execute();

    public String getOutputStream() {
        return outputStream;
    }
}