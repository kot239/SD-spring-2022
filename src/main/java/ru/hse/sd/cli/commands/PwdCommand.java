package ru.hse.sd.cli.commands;

import java.io.*;
import java.nio.charset.StandardCharsets;

import ru.hse.sd.cli.Memory;
import ru.hse.sd.cli.enums.ReturnCode;

/*
 * Implementation of Bash's pwd command
 */
public class PwdCommand extends Command {
    private final Memory memory;

    /*
     * Constructor for pwd command
     */
    public PwdCommand(ByteArrayInputStream inputStream, Memory memory) {
        this.command = "pwd";
        this.inputStream = inputStream;
        this.outputStream = new ByteArrayOutputStream(inputStream.toString().getBytes().length);
        this.memory = memory;
    }

    /*
     * Execute pwd command
     */
    @Override
    public ReturnCode execute() {
        String curDirectory = memory.getCurrentDirectory().toAbsolutePath() + System.getProperty("line.separator");
        try {
            outputStream.write(curDirectory.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            errorStream = e.getMessage();
            return ReturnCode.FAILURE;
        }
        return ReturnCode.SUCCESS;
    }
}