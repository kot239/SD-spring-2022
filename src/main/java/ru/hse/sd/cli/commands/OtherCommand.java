package main.java.ru.hse.sd.cli.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import main.java.ru.hse.sd.cli.enums.ReturnCode;

/*
 * Class for unknown Bash's commands
 */
public class OtherCommand extends Command {
    private final List<String> args;

    /*
     * Constructor with command name and arguments
     */
    public OtherCommand(String command, List<String> args) {
        this.command = command;
        this.args = args;
    }

    /*
     * Execute command in bash
     */
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