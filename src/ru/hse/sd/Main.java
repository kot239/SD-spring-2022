package ru.hse.sd;

import java.util.List;

import ru.hse.sd.commands.CatCommand;
import ru.hse.sd.commands.EchoCommand;
import ru.hse.sd.commands.PwdCommand;
import ru.hse.sd.commands.WcCommand;
import ru.hse.sd.enums.ReturnCode;

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

    }

}