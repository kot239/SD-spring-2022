package main.java.ru.hse.sd.cli.commands;

import main.java.ru.hse.sd.cli.enums.ReturnCode;

public abstract class Command {
    public static final String CAT = "cat";
    public static final String ECHO ="echo";
    public static final String EXIT = "exit";
    public static final String PWD = "pwd";
    public static final String WC = "wc";

    protected String command;
    protected String inputStream;
    protected String outputStream = "";
    protected String errorStream;

    public abstract ReturnCode execute();

    public String getOutputStream() {
        return outputStream;
    }
}