package ru.hse.sd.cli.commands;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;
import ru.hse.sd.cli.enums.ReturnCode;

import static org.junit.jupiter.api.Assertions.assertEquals;


class WcCommandTest {

    private final String separator = System.getProperty("line.separator");

    @Test
    void testNoSuchFile() {
        WcCommand wc = new WcCommand(List.of("not_exist.txt"),
                new ByteArrayInputStream("".getBytes()));

        ReturnCode code = wc.execute();
        assertEquals(ReturnCode.FAILURE, code);

    }

    @Test
    void testOneFile() {
        String filePath = "src/test/resources/file.txt";

        WcCommand wc = new WcCommand(List.of(filePath),
                new ByteArrayInputStream("".getBytes()));

        ReturnCode code = wc.execute();
        assertEquals(ReturnCode.SUCCESS, code);

        ByteArrayOutputStream stream = wc.getOutputStream();
        String output = stream.toString(StandardCharsets.UTF_8);

        String expected = "2 8 38" + separator;
        assertEquals(expected, output);
    }

    @Test
    void testMultipleFiles() {
        String filePath1 = "src/test/resources/file.txt";
        String filePath2 = "src/test/resources/file2.txt";

        WcCommand wc = new WcCommand(
                List.of(
                        filePath1,
                        filePath2
                ),
                new ByteArrayInputStream("".getBytes()));

        ReturnCode code = wc.execute();
        assertEquals(ReturnCode.SUCCESS, code);

        ByteArrayOutputStream stream = wc.getOutputStream();
        String output = stream.toString(StandardCharsets.UTF_8);

        String expected = "2 8 38" + separator + "3 10 49" + separator;
        assertEquals(expected, output);
    }

}