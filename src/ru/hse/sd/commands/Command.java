package ru.hse.sd.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import ru.hse.sd.enums.ReturnCode;

public abstract class Command {
    protected String command;
    protected String inputStream;
    protected String outputStream;
    protected String errorStream;

    public abstract ReturnCode execute();

    public String getOutputStream() {
        return outputStream;
    }
}