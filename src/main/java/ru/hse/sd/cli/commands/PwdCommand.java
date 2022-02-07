package ru.hse.sd.cli.commands;

import java.nio.file.Paths;

import ru.hse.sd.cli.enums.ReturnCode;

/*
 * Implementation of Bash's pwd command
 */
public class PwdCommand extends Command {

    /*
     * Constructor for pwd command
     */
    public PwdCommand() {
        this.command = "pwd";
    }

    /*
     * Execute pwd command
     */
    @Override
    public ReturnCode execute() {
        outputStream = Paths.get("").toAbsolutePath() + "\n";
        return ReturnCode.SUCCESS;
    }
}