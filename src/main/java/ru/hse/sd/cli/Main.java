package ru.hse.sd.cli;

import java.util.Collections;
import java.util.List;

import ru.hse.sd.cli.commands.CatCommand;
import ru.hse.sd.cli.commands.EchoCommand;
import ru.hse.sd.cli.commands.OtherCommand;
import ru.hse.sd.cli.commands.PwdCommand;
import ru.hse.sd.cli.commands.WcCommand;
import ru.hse.sd.cli.enums.ReturnCode;

public class Main {

    public static void main(String[] args) {
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

        WcCommand wc = new WcCommand(List.of("README.md"));
        code = wc.execute();
        stream = wc.getOutputStream();
        System.out.println(stream);

        OtherCommand other = new OtherCommand("ls", Collections.emptyList());
        code = other.execute();
        stream = other.getOutputStream();
        System.out.println(stream);

    }

}