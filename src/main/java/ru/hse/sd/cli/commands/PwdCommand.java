package ru.hse.sd.cli.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import ru.hse.sd.cli.enums.ReturnCode;

/*
 * Implementation of Bash's pwd command
 */
public class PwdCommand extends Command {

    /*
     * Constructor for pwd command
     */
    public PwdCommand(InputStream inputStream, OutputStream outputStream) {
        this.command = "pwd";
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    /*
     * Execute pwd command
     */
    @Override
    public ReturnCode execute() {
        String curDirectory = Paths.get("").toAbsolutePath() + "\n";
        try {
            outputStream.write(curDirectory.getBytes(StandardCharsets.UTF_8));
        } catch(IOException e) {
            errorStream = e.getMessage();
            return ReturnCode.FAILURE;
        }
        return ReturnCode.SUCCESS;
    }
}