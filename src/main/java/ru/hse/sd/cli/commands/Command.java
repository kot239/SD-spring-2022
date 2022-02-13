package ru.hse.sd.cli.commands;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
    protected ByteArrayInputStream inputStream;
    protected ByteArrayOutputStream outputStream;
    protected String errorStream;

    public abstract ReturnCode execute();

    /*
     * Get name of command
     */
    public String getCommandName() {return command;}

    /*
     * Get error of commands
     */
    public String getErrorStream() {return errorStream;}

    /*
     * Get output of command
     */
    public ByteArrayOutputStream getOutputStream() {
        return outputStream;
    }

    /*
     * Set input of command
     */
    public void setInputStream(ByteArrayInputStream inputStream) {
        this.inputStream = inputStream;
    }
}