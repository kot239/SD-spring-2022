package ru.hse.sd.cli.commands;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import ru.hse.sd.cli.enums.ReturnCode;

/*
 * Implementation of Bash's grep command
 */
public class GrepCommand extends Command {
    private final List<String> args;
    /*
     * Constructor which takes arguments for cat command
     */
    public GrepCommand(List<String> args, ByteArrayInputStream inputStream) {
        this.command = "grep";
        this.args = args;
        this.inputStream = inputStream;
        this.outputStream = new ByteArrayOutputStream(inputStream.toString().getBytes().length);
    }

    @Override
    public ReturnCode execute() {
        return null;
    }
}
