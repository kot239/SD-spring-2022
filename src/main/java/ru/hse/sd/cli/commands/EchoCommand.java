package ru.hse.sd.cli.commands;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import ru.hse.sd.cli.enums.ReturnCode;

/*
 * Implementation of Bash's echo command
 */
public class EchoCommand extends Command {
    private final List<String> args;

    /*
     * Constructor which takes arguments for echo command
     */
    public EchoCommand(List<String> args,
                       ByteArrayInputStream inputStream) {
        this.command = "echo";
        this.args = args;
        this.inputStream = inputStream;
        this.outputStream = new ByteArrayOutputStream(inputStream.toString().getBytes().length);
    }

    /*
     * Execute echo command with arguments from constructor
     */
    @Override
    public ReturnCode execute() {
        if (args.isEmpty()) {
            try {
                inputStream.transferTo(outputStream);
            } catch(IOException e) {
                errorStream = e.getMessage();
                return ReturnCode.FAILURE;
            }
            return ReturnCode.SUCCESS;
        }
        try {
            outputStream.write(String.join(" ", args).getBytes(StandardCharsets.UTF_8));
        } catch(IOException e) {
            errorStream = e.getMessage();
            return ReturnCode.FAILURE;
        }
        return ReturnCode.SUCCESS;
    }
}