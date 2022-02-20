package ru.hse.sd.cli.commands;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.IOUtils;
import ru.hse.sd.cli.enums.ReturnCode;

/*
 * Implementation of Bash's grep command
 */
public class GrepCommand extends Command {
    private final List<String> args;
    /*
     * Constructor which takes arguments for grep command
     */
    public GrepCommand(List<String> args, ByteArrayInputStream inputStream) {
        this.command = "grep";
        this.args = args;
        this.inputStream = inputStream;
        this.outputStream = new ByteArrayOutputStream(inputStream.toString().getBytes().length);
    }

    private Options createOptions() {
        Options options = new Options();
        options.addOption("w", false, "word regexp");
        options.addOption("i", false,
                "Ignore  case  distinctions,  so that characters that differ only in case match each other.");
        options.addOption("A", true,
                "Print NUM lines of trailing context after  matching  lines");

        return options;
    }

    @Override
    public ReturnCode execute() {
        CommandLineParser parser = new DefaultParser();
        Options options = createOptions();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args.toArray(new String[0]));
        } catch (ParseException e) {
            errorStream = e.getMessage();
            return ReturnCode.FAILURE;
        }

        // get not flags
        List<String> commandArgs = cmd.getArgList();

        if (commandArgs.isEmpty()) {
            errorStream = "At least one argument should be passed (regexp)";
            return ReturnCode.FAILURE;
        }

        String regExpr = commandArgs.get(0);

        List<String> lines;
        if (commandArgs.size() == 1) {
            // read from inputStream
            try {
                lines = IOUtils.readLines(inputStream, StandardCharsets.UTF_8);
            } catch (IOException e) {
                errorStream = e.getMessage();
                return ReturnCode.FAILURE;
            }
        } else {
            // read from file
            String fileName = commandArgs.get(1);
            try {
                lines = Files.readAllLines(new File(fileName).toPath(),
                        Charset.defaultCharset());
            } catch (IOException e) {
                errorStream = e.getMessage();
                return ReturnCode.FAILURE;
            }
        }
        // todo учитывать флаги

        Pattern pattern;
        if (cmd.hasOption("i")) {
            pattern = Pattern.compile(regExpr, Pattern.CASE_INSENSITIVE);
        } else {
            pattern = Pattern.compile(regExpr);
        }

        List<String> result = new ArrayList<>();

        for (String line : lines) {
            Matcher matcher;
            if (cmd.hasOption("i")) {
                matcher = pattern.matcher(line.toLowerCase());
            } else {
                matcher = pattern.matcher(line);
            }
            if (matcher.find()) {
                result.add(line);
            }
        }
        try {
            outputStream.write(String.join(System.getProperty("line.separator"), result).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            errorStream = e.getMessage();
            return ReturnCode.FAILURE;
        }
        return ReturnCode.SUCCESS;
    }
}
