package ru.hse.sd.cli.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.hse.sd.cli.enums.ReturnCode;

/*
 * Class for unknown Bash's commands
 */
public class OtherCommand extends Command {
    private final List<String> args;
    private final Map<String, String> env;

    /*
     * Constructor with command name and arguments
     */
    public OtherCommand(String command, List<String> args, InputStream inputStream, OutputStream outputStream,
                        Map<String, String> env) {
        this.command = command;
        this.args = args;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.env = env;
    }

    /*
     * Execute command in bash
     */
    @Override
    public ReturnCode execute() {
        List<String> pbArgs = new ArrayList<>(List.of(command));
        pbArgs.addAll(args);
        ProcessBuilder pb = new ProcessBuilder(pbArgs);
        pb.environment().putAll(env);
        pb.directory(null);
        Process p;
        try {
            p = pb.start();
            try (var reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                StringBuilder result = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    result.append(line).append(System.getProperty("line.separator"));
                }
                if (result.length() != 0) {
                    result.setLength(result.length() - 1);
                }
                try {
                    outputStream.write(result.toString().getBytes(StandardCharsets.UTF_8));
                } catch(IOException e) {
                    errorStream = e.getMessage();
                    return ReturnCode.FAILURE;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ReturnCode.SUCCESS;
    }
}