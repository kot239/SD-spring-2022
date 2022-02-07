package main.java.ru.hse.sd.cli.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import main.java.ru.hse.sd.cli.enums.ReturnCode;


public class CatCommand extends Command {
    private final List<String> args;

    public CatCommand(List<String> args) {
        this.command = "cat";
        this.args = args;
    }

    @Override
    public ReturnCode execute() {
        if (args.isEmpty()) {
            outputStream = inputStream;
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
            outputStream = Files.readString(Paths.get(filename));
        } catch (IOException e) {
            errorStream ="Couldn't read file " + filename + "\n";
            return ReturnCode.FAILURE;
        }

        return ReturnCode.SUCCESS;
    }
}