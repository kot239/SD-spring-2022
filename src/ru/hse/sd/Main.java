package ru.hse.sd;

import java.util.List;

import ru.hse.sd.commands.CatCommand;
import ru.hse.sd.commands.EchoCommand;
import ru.hse.sd.commands.PwdCommand;
import ru.hse.sd.enums.ReturnCode;

public class Main {

    public static void main(String[] args) {
        /*
        EchoCommand echo = new EchoCommand(List.of("hello"));
        ReturnCode code = echo.execute();
        String stream = echo.getOutputStream();
        System.out.println(stream);

        CatCommand cat = new CatCommand(List.of(".gitignore"));
        code = cat.execute();
        stream = cat.getOutputStream();
        System.out.println(stream);

        PwdCommand pwd = new PwdCommand();
        code = pwd.execute();
        stream = pwd.getOutputStream();
        System.out.println(stream);
        */

        String input = "echo '\"$HOME | kek\"' | cat nya.txt";
        Parser parser = new Parser();
        List<List<RawArg>> kek = parser.parse(input);
        for (List<RawArg> command: kek) {
            for (RawArg arg: command) {
                System.out.println(arg.arg);
            }
            System.out.println("###");
        }
    }

}