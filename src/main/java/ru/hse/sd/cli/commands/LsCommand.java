package ru.hse.sd.cli.commands;

import ru.hse.sd.cli.Memory;
import ru.hse.sd.cli.enums.ReturnCode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LsCommand extends Command {

    private final List<String> args;
    private final Memory memory;

    public LsCommand(List<String> args, ByteArrayInputStream inputStream, Memory memory) {
        this.command = "ls";
        this.args = args;
        this.inputStream = inputStream;
        this.outputStream = new ByteArrayOutputStream(inputStream.toString().getBytes().length);
        this.memory = memory;
    }

    /**
     * Traverses files in a directory and prints them to output stream
     *
     * @param path directory in which files will be processed
     * @throws IOException if writing to output stream was not successful
     */
    private void processFiles(Path path) throws IOException {
        ArrayList<String> res = new ArrayList<>();
        Arrays.stream(Objects.requireNonNull(new File(String.valueOf(path))
                        .listFiles()))
                .map(f -> String.valueOf(f).substring(String.valueOf(path).length() + 1))
                .sorted()
                .forEach(f -> res.add(f + '\n'));
        for (String f : res) {
            outputStream.write(f.getBytes(StandardCharsets.UTF_8));
        }
    }

    /**
     * If args field is empty - prints contents of current directory
     * Otherwise prints contents of a directory given as an argument or name of a file given an argument
     * If file or directory was not found - prints a message about
     *
     * @return ReturnCode
     */
    @Override
    public ReturnCode execute() {
        if (args.size() > 1) {
            errorStream = "Too many arguments were provided, must be 1";
            return ReturnCode.FAILURE;
        }
        if (args.isEmpty()) {
            Path path = memory.getCurrentDirectory();
            try {
                processFiles(path);
            } catch (IOException e) {
                errorStream = e.getMessage();
                return ReturnCode.FAILURE;
            }
            return ReturnCode.SUCCESS;
        }
        String arg = args.get(0);
        if (Files.isDirectory(Path.of(arg))) {
            Path path;
            if (!Files.exists(Path.of(arg))) {
                path = memory.resolveCurrentDirectory(arg);
                if (!Files.exists(Path.of(String.valueOf(path)))) {
                    errorStream = "No such directory " + arg;
                }
            } else {
                path = Path.of(arg);
            }
            try {
                processFiles(path);
            } catch (IOException e) {
                errorStream = e.getMessage();
                return ReturnCode.FAILURE;
            }
        } else {
            if (!Files.exists(Path.of(arg))) {
                errorStream = "No such file " + arg;
            }
            try {
                outputStream.write(arg.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                errorStream = e.getMessage();
                return ReturnCode.FAILURE;
            }
        }
        return ReturnCode.SUCCESS;
    }
}
