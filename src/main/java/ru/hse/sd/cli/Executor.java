package ru.hse.sd.cli;

import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import ru.hse.sd.cli.enums.ReturnCode;
import ru.hse.sd.cli.commands.*;

/**
 * Class that do the main flow
 */
public class Executor {
    /**
     * Field with ability to save information about parameters
     * (It will be helpful in the next step)
     */
    private final Memory memory = new Memory();
    private Command previousCommand;
    private String errorStream;

    private void doCommand(List<String> argsWithCom) {
        if (argsWithCom.isEmpty()) {
            return;
        }
        Command command;
        List<String> args = argsWithCom.subList(1, argsWithCom.size());
        var inputStream = new ByteArrayInputStream(new byte[0]);
        if (previousCommand != null) {
            var outputStream = previousCommand.getOutputStream();
            inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        }
        switch (argsWithCom.get(0)) {
            case Command.CAT:
                command = new CatCommand(args, inputStream, memory);
                break;
            case Command.ECHO:
                command = new EchoCommand(args, inputStream);
                break;
            case Command.EXIT:
                command = new ExitCommand();
                System.exit(0);
                break;
            case Command.GREP:
                command = new GrepCommand(args, inputStream, memory);
                break;
            case Command.PWD:
                command = new PwdCommand(inputStream, memory);
                break;
            case Command.WC:
                command = new WcCommand(args, inputStream, memory);
                break;
            case Command.CD:
                command = new CdCommand(args, inputStream, memory);
                break;
            default:
                command = new OtherCommand(argsWithCom.get(0), args, inputStream, memory);
                break;
        }

        previousCommand = command;
        var code = command.execute();
        if (code == ReturnCode.FAILURE) {
            if (errorStream == null) {
                errorStream = "";
            } else {
                errorStream = errorStream + "\n";
            }
            errorStream = errorStream + "Error in command: " + command.getCommandName();
        }
        if (command.getErrorStream() != null) {
            if (errorStream == null) {
                errorStream = "";
            } else {
                errorStream = errorStream + "\n";
            }
            errorStream = errorStream + command.getErrorStream();
        }
    }

    /**
     * Method receives input that was written in the console
     * and pushes through the main flow
     *
     * @param input String from the console
     */
    public void execute(String input) {
        var parser = new Parser();
        List<List<RawArg>> raw_commands = parser.parse(input);
        previousCommand = null;
        for (List<RawArg> command: raw_commands) {
            List<String> args = new LinkedList<>();
            for (RawArg arg: command) {
                String word = arg.prepareArg(this.memory);
                if (!word.isEmpty()) {
                    args.add(word);
                }
            }
            doCommand(args);
        }
        if (errorStream != null) {
            System.out.print(errorStream);
            errorStream = null;
        } else {
            if (previousCommand != null && previousCommand.getOutputStream() != null) {
                if (Objects.equals(previousCommand.getCommandName(), "echo")) {
                    System.out.println(previousCommand.getOutputStream().toString());
                } else {
                    System.out.print(previousCommand.getOutputStream().toString());
                }
            }
        }
    }
}