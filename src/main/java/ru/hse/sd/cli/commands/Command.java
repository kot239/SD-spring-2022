package ru.hse.sd.cli.commands;

import ru.hse.sd.cli.enums.ReturnCode;

/*
 * Abstract class for Bash's commands
 */
public abstract class Command {
    protected String command;
    protected String inputStream;
    protected String outputStream = "";
    protected String errorStream;

    public abstract ReturnCode execute();

    /*
     * Get output of command
     */
    public String getOutputStream() {
        return outputStream;
    }

    /*
     * Set input of command
     */
    public void setInputStream(String inputStream) {
        this.inputStream = inputStream;
    }
}