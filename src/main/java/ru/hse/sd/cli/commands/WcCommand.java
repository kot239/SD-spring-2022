package main.java.ru.hse.sd.cli.commands;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import main.java.ru.hse.sd.cli.enums.ReturnCode;

public class WcCommand extends Command {
    private final List<String> args;

    public WcCommand(List<String> args) {
        this.command = "wc";
        this.args = args;
    }

    @Override
    public ReturnCode execute() {
        if (args.isEmpty()) {
            List<String> fileLines = Arrays.stream(inputStream.split("\\r?\\n")).collect(Collectors.toList());
            outputStream = countOneIteration(fileLines);
            return ReturnCode.SUCCESS;
        }
        StringBuilder result = new StringBuilder();
        for (String filename : args) {
            try {
                List<String> fileLines = Files.lines(Paths.get(filename)).collect(Collectors.toList());
                result.append(countOneIteration(fileLines));
            } catch (IOException e) {
                errorStream = e.getMessage();
                return ReturnCode.FAILURE;
            }
        }
        outputStream = result.toString();
        return ReturnCode.SUCCESS;
    }

    private String countOneIteration(List<String> fileLines) {
        int lines = 0;
        int words = 0;
        int bytes = 0;
        for (String line : fileLines) {
            lines++;

            String trim = line.trim();
            if (!trim.isEmpty()) {
                words += trim.split("\\s+").length;
            }
            bytes += line.getBytes(StandardCharsets.UTF_8).length;
        }
        lines--;
        bytes += lines;
        return lines + " " + words + " " + bytes + "\n";
    }
}