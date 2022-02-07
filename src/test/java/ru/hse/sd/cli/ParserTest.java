package test.java.ru.hse.sd.cli;

import main.java.ru.hse.sd.cli.Parser;
import main.java.ru.hse.sd.cli.RawArg;
import main.java.ru.hse.sd.cli.commands.WcCommand;
import main.java.ru.hse.sd.cli.enums.ReturnCode;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    private void checkParsedInput(List<List<RawArg>> expected, List<List<RawArg>> actual) {
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).size(), actual.get(i).size());
            for (int j = 0; j < expected.get(i).size(); j++) {
                assertEquals(expected.get(i).get(j).arg, actual.get(i).get(j).arg);
            }
        }
    }

    @Test
    void testOneWord() {
        Parser parser = new Parser();
        String input = "echo";

        List<List<RawArg>> ans = new LinkedList<>();
        List<RawArg> command = new LinkedList<>();
        command.add(new RawArg("echo", false));
        ans.add(command);

        List<List<RawArg>> res = parser.parse(input);
        checkParsedInput(ans, res);
    }

    @Test
    void testManyWords() {
        Parser parser = new Parser();
        String input = "echo $SD";

        List<List<RawArg>> ans = new LinkedList<>();
        List<RawArg> command = new LinkedList<>();
        command.add(new RawArg("echo", false));
        command.add(new RawArg("$SD", false));
        ans.add(command);

        List<List<RawArg>> res = parser.parse(input);
        checkParsedInput(ans, res);
    }

    @Test
    void testDoubleQuotes() {
        Parser parser = new Parser();
        String input = "echo \"$SD $SE\"";

        List<List<RawArg>> ans = new LinkedList<>();
        List<RawArg> command = new LinkedList<>();
        command.add(new RawArg("echo", false));
        command.add(new RawArg("$SD $SE", false));
        ans.add(command);

        List<List<RawArg>> res = parser.parse(input);
        checkParsedInput(ans, res);
    }

    @Test
    void testSingleQuotes() {
        Parser parser = new Parser();
        String input = "echo '$SD $SE'";

        List<List<RawArg>> ans = new LinkedList<>();
        List<RawArg> command = new LinkedList<>();
        command.add(new RawArg("echo", false));
        command.add(new RawArg("$SD $SE", false));
        ans.add(command);

        List<List<RawArg>> res = parser.parse(input);
        checkParsedInput(ans, res);
    }

    @Test
    void testDifferentQuotes() {
        Parser parser = new Parser();
        String input = "echo '\"$SD $SE\"'";

        List<List<RawArg>> ans = new LinkedList<>();
        List<RawArg> command = new LinkedList<>();
        command.add(new RawArg("echo", false));
        command.add(new RawArg("\"$SD $SE\"", false));
        ans.add(command);

        List<List<RawArg>> res = parser.parse(input);
        checkParsedInput(ans, res);
    }
}
