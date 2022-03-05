package ru.hse.sd.cli.commands;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.hse.sd.cli.Memory;
import ru.hse.sd.cli.enums.ReturnCode;

/*
 * Class for unknown Bash's commands
 */
public class OtherCommand extends Command {
    private final List<String> args;
    private final Memory memory;

    /*
     * Constructor with command name and arguments
     */
    public OtherCommand(String command, List<String> args, ByteArrayInputStream inputStream, Memory memory) {
        this.command = command;
        this.args = args;
        this.inputStream = inputStream;
        this.outputStream = new ByteArrayOutputStream(inputStream.toString().getBytes().length);
        this.memory = memory;
    }

    /*
     * Execute command in bash
     */
    @Override
    public ReturnCode execute() {
        List<String> pbArgs;
        if (System.getProperty("os.name").contains("Windows")) {
            pbArgs = new ArrayList<>(List.of("cmd", "/c", command));
        } else {
            pbArgs = new ArrayList<>(List.of(command));
        }

        pbArgs.addAll(args);
        ProcessBuilder pb = new ProcessBuilder(pbArgs);
        pb.environment().putAll(memory.getAll());
        pb.directory(new File(String.valueOf(memory.getCurrentDirectory())));
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
                } catch (IOException e) {
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