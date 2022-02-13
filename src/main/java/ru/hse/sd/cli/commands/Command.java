package ru.hse.sd.cli.commands;

import java.io.InputStream;
import java.io.OutputStream;

import ru.hse.sd.cli.enums.ReturnCode;

/*
 * Abstract class for Bash's commands
 */
public abstract class Command {
    public static final String CAT = "cat";
    public static final String ECHO ="echo";
    public static final String EXIT = "exit";
    public static final String PWD = "pwd";
    public static final String WC = "wc";

    protected String command;
    protected InputStream inputStream;
    protected OutputStream outputStream;
    protected String errorStream;

    public abstract ReturnCode execute();

    /*
     * Get output of command
     */
    public OutputStream getOutputStream() {
        return outputStream;
    }

    /*
     * Set input of command
     */
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}