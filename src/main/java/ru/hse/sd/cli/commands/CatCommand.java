package ru.hse.sd.cli.commands;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import ru.hse.sd.cli.enums.ReturnCode;

/*
 * Implementation of Bash's cat command
 */
public class CatCommand extends Command {
    private final List<String> args;

    /*
     * Constructor which takes arguments for cat command
     */
    public CatCommand(List<String> args,
                      ByteArrayInputStream inputStream) {
        this.command = "cat";
        this.args = args;
        this.inputStream = inputStream;
        this.outputStream = new ByteArrayOutputStream(inputStream.toString().getBytes().length);
    }

    /*
     * Execute cat command with arguments from constructor
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
        } else if (args.size() > 1) {
            errorStream = "Too many arguments for cat command\n";
            return ReturnCode.FAILURE;
        }
        String filename = args.get(0);
        File f = new File(filename);
        if (!f.exists()) {
            errorStream = filename + ": No such file or directory\n";
        }
        try {
            String readString = Files.readString(Paths.get(filename));
            try {
                outputStream.write(readString.getBytes(StandardCharsets.UTF_8));
            } catch(IOException e) {
                errorStream = e.getMessage();
                return ReturnCode.FAILURE;
            }
        } catch (IOException e) {
            errorStream = "Couldn't read file " + filename + "\n";
            return ReturnCode.FAILURE;
        }

        return ReturnCode.SUCCESS;
    }
}