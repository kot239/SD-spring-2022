package ru.hse.sd.cli.commands;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ru.hse.sd.cli.enums.ReturnCode;

/*
 * Implementation of Bash's wc command
 */
public class WcCommand extends Command {
    private final List<String> args;

    /*
     * Constructor which takes arguments for wc command
     */
    public WcCommand(List<String> args, ByteArrayInputStream inputStream) {
        this.command = "wc";
        this.args = args;
        this.inputStream = inputStream;
        this.outputStream = new ByteArrayOutputStream(inputStream.toString().getBytes().length);
    }

    /*
     * Execute wc command with arguments from constructor
     */
    @Override
    public ReturnCode execute() {
        if (args.isEmpty()) {
            String text;
            text = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            List<String> fileLines = Arrays.stream(text.split("\\r?\\n")).collect(Collectors.toList());
            try {
                outputStream.write(countOneIteration(fileLines).getBytes(StandardCharsets.UTF_8));
            } catch(IOException e) {
                errorStream = e.getMessage();
                return ReturnCode.FAILURE;
            }
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
        try {
            outputStream.write(result.toString().getBytes(StandardCharsets.UTF_8));
        } catch(IOException e) {
            errorStream = e.getMessage();
            return ReturnCode.FAILURE;
        }
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