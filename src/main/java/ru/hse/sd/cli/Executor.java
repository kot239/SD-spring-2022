package main.java.ru.hse.sd.cli;

import java.util.LinkedList;
import java.util.List;

import main.java.ru.hse.sd.cli.commands.*;

public class Executor {
    private final Memory memory = new Memory();

    private void do_command(List<String> args_with_com) {
        if (args_with_com.isEmpty()) {
            return;
        }
        Command command;
        List<String> args = args_with_com.subList(1, args_with_com.size());
        switch (args_with_com.get(0)) {
            case Command.CAT:
                command = new CatCommand(args);
                break;
            case Command.ECHO:
                command = new EchoCommand(args);
                break;
            case Command.EXIT:
                command = new ExitCommand();
                break;
            case Command.PWD:
                command = new PwdCommand();
                break;
            case Command.WC:
                command = new WcCommand(args);
                break;
            default:
                command = new OtherCommand(args_with_com.get(0), args);
                break;
        }
        command.execute();
    }

    public void execute(String input) {
        Parser parser = new Parser();
        List<List<RawArg>> raw_commands = parser.parse(input);
        for (List<RawArg> command: raw_commands) {
            List<String> args = new LinkedList<>();
            for (RawArg arg: command) {
                String word = arg.fryArg(this.memory);
                if (!word.isEmpty()) {
                    args.add(word);
                }
            }
            do_command(args);
        }
    }
}