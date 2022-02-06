package ru.hse.sd.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ru.hse.sd.enums.ReturnCode;


public class OtherCommand extends Command {
    private final List<String> args;

    public OtherCommand(String command, List<String> args) {
        this.command = command;
        this.args = args;
    }

    @Override
    public ReturnCode execute() {
        List<String> pbArgs = new ArrayList<>(List.of(command));
        pbArgs.addAll(args);
        ProcessBuilder pb = new ProcessBuilder(pbArgs);
        pb.directory(null);
        Process p;
        try {
            p = pb.start();
            try (var reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                StringBuilder result = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    result.append(line).append("\n");
                }
                result.setLength(result.length() - 1);
                outputStream = result.toString();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ReturnCode.SUCCESS;
    }
}