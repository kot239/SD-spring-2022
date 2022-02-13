package ru.hse.sd.cli.commands;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;

import ru.hse.sd.cli.enums.ReturnCode;

/*
 * Implementation of Bash's pwd command
 */
public class PwdCommand extends Command {

    /*
     * Constructor for pwd command
     */
    public PwdCommand(ByteArrayInputStream inputStream) {
        this.command = "pwd";
        this.inputStream = inputStream;
        this.outputStream = new ByteArrayOutputStream(inputStream.toString().getBytes().length);
    }

    /*
     * Execute pwd command
     */
    @Override
    public ReturnCode execute() {
        String curDirectory = Paths.get("").toAbsolutePath() + System.getProperty("line.separator");
        try {
            outputStream.write(curDirectory.getBytes(StandardCharsets.UTF_8));
        } catch(IOException e) {
            errorStream = e.getMessage();
            return ReturnCode.FAILURE;
        }
        return ReturnCode.SUCCESS;
    }
}