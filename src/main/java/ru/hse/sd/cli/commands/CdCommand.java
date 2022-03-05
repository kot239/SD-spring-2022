package ru.hse.sd.cli.commands;

import ru.hse.sd.cli.Memory;
import ru.hse.sd.cli.enums.ReturnCode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CdCommand extends Command {
    private final List<String> args;
    private Memory memory;

    public CdCommand(List<String> args,
                     ByteArrayInputStream inputStream, Memory memory) {
        this.command = "cd";
        this.args = args;
        this.inputStream = inputStream;
        this.outputStream = new ByteArrayOutputStream(inputStream.toString().getBytes().length);
        this.memory = memory;
    }

    @Override
    public ReturnCode execute() {
        if (args.isEmpty()) {
            memory.resetCurrentDirectory();
            return ReturnCode.SUCCESS;
        } else if (args.size() == 1) {
            Path newPath = memory.resolveCurrentDirectory(args.get(0));
            if (newPath == null || !Files.isDirectory(newPath)) {
                errorStream = "No such directory\n";
                return ReturnCode.FAILURE;
            }
            memory.changeCurrentDirectory(args.get(0));
            return ReturnCode.SUCCESS;
        } else {
            errorStream = "Too many arguments for cd command\n";
            return ReturnCode.FAILURE;
        }
    }
}
