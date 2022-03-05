package ru.hse.sd.cli.commands;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import ru.hse.sd.cli.Memory;
import ru.hse.sd.cli.enums.ReturnCode;

/*
 * Implementation of Bash's cat command
 */
public class CatCommand extends Command {
    private final List<String> args;
    private Memory memory;

    /*
     * Constructor which takes arguments for cat command
     */
    public CatCommand(List<String> args,
                      ByteArrayInputStream inputStream, Memory memory) {
        this.command = "cat";
        this.args = args;
        this.inputStream = inputStream;
        this.outputStream = new ByteArrayOutputStream(inputStream.toString().getBytes().length);
        this.memory = memory;
    }

    /*
     * Execute cat command with arguments from constructor
     */
    @Override
    public ReturnCode execute() {
        if (args.isEmpty()) {
            try {
                inputStream.transferTo(outputStream);
            } catch (IOException e) {
                errorStream = e.getMessage();
                return ReturnCode.FAILURE;
            }
            return ReturnCode.SUCCESS;
        } else if (args.size() > 1) {
            errorStream = "Too many arguments for cat command\n";
            return ReturnCode.FAILURE;
        }
        Path path = memory.resolveCurrentDirectory(args.get(0));
        if (path == null) {
            errorStream = "Wrong file path\n";
            return ReturnCode.FAILURE;
        }
        File f = path.toFile();
        if (!f.exists()) {
            errorStream = path + ": No such file or directory\n";
            return ReturnCode.FAILURE;
        }
        try {
            String readString = Files.readString(path);
            try {
                outputStream.write(readString.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                errorStream = e.getMessage();
                return ReturnCode.FAILURE;
            }
        } catch (IOException e) {
            errorStream = "Couldn't read file " + path + "\n";
            return ReturnCode.FAILURE;
        }

        return ReturnCode.SUCCESS;
    }
}