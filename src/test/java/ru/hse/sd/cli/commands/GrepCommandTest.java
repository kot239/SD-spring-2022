package ru.hse.sd.cli.commands;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import ru.hse.sd.cli.enums.ReturnCode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrepCommandTest {

    @Test
    void testNoFlagsInputStream() {
        String text = "Море, море, море, океан";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
        GrepCommand grep = new GrepCommand(List.of("море"), inputStream);

        ReturnCode code = grep.execute();
        assertEquals(ReturnCode.SUCCESS, code);

        String stream = grep.getOutputStream().toString();
        assertEquals(text, stream);
    }

    @Test
    void testNoFlagsFile() {
        String filePath = "src/test/resources/grep/sea.txt";
        GrepCommand grep = new GrepCommand(List.of("море", filePath),
                new ByteArrayInputStream("".getBytes()));

        ReturnCode code = grep.execute();
        assertEquals(ReturnCode.SUCCESS, code);

        String stream = grep.getOutputStream().toString();
        String expected = "море, океан";
        assertEquals(expected, stream);
    }

    @Test
    void testNoFlagsEverythingMatches() throws IOException {
        String filePath = "src/test/resources/grep/sea.txt";
        GrepCommand grep = new GrepCommand(List.of("океан", filePath),
                new ByteArrayInputStream("".getBytes()));

        ReturnCode code = grep.execute();
        assertEquals(ReturnCode.SUCCESS, code);

        String stream = grep.getOutputStream().toString();

        File expectedFile = new File(filePath);
        String expected = String.join(System.getProperty("line.separator"), FileUtils.readLines(expectedFile, StandardCharsets.UTF_8));

        assertEquals(expected, stream);
    }

}
