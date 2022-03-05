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
     * Otherwise prints contents of each directory given as an argument or name of each file given an argument
     * If any file or directory was not found - prints a message about it and proceeds processing arguments
     *
     * @return ReturnCode
     */
    @Override
    public ReturnCode execute() {
        if (args.isEmpty()) {
            Path path = memory.getCurrentDirectory();
            try {
                processFiles(path);
            } catch (IOException e) {
                errorStream = e.getMessage();
                return ReturnCode.FAILURE;
            }
        }
        for (String arg : args) {
            if (Files.isDirectory(Path.of(arg))) {
                Path path;
                if (!Files.exists(Path.of(arg))) {
                    path = memory.resolveCurrentDirectory(arg);
                    if (!Files.exists(Path.of(String.valueOf(path)))) {
                        String err = "\nNo such directory " + arg + '\n';
                        try {
                            outputStream.write(err.getBytes(StandardCharsets.UTF_8));
                        } catch (IOException e) {
                            errorStream = e.getMessage();
                            return ReturnCode.FAILURE;
                        }
                        continue;
                    }
                } else {
                    path = Path.of(arg);
                }
                String directory = "\n" + path + ":\n";
                try {
                    outputStream.write(directory.getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    errorStream = e.getMessage();
                    return ReturnCode.FAILURE;
                }
                try {
                    processFiles(path);
                } catch (IOException e) {
                    errorStream = e.getMessage();
                    return ReturnCode.FAILURE;
                }
            } else {
                if (!Files.exists(Path.of(arg))) {
                    String err = "\nNo such file " + arg + '\n';
                    try {
                        outputStream.write(err.getBytes(StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        errorStream = e.getMessage();
                        return ReturnCode.FAILURE;
                    }
                    continue;
                }
                arg += '\n';
                try {
                    outputStream.write(arg.getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    errorStream = e.getMessage();
                    return ReturnCode.FAILURE;
                }
            }
        }
        return ReturnCode.SUCCESS;
    }
}
