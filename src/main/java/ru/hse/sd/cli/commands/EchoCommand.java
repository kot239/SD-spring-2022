package ru.hse.sd.cli.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    public EchoCommand(List<String> args, InputStream inputStream, OutputStream outputStream) {
        this.command = "echo";
        this.args = args;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
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