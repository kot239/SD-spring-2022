package ru.hse.sd.cli.commands;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import ru.hse.sd.cli.enums.ReturnCode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CatCommandTest {
    @Test
    void testTooManyArgs() {
        String filePath1 = "src/test/resources/file.txt";
        String filePath2 = "src/test/resources/file2.txt";

        CatCommand cat = new CatCommand(List.of(
                filePath1,
                filePath2
        ),
                new ByteArrayInputStream("".getBytes()));

        ReturnCode code = cat.execute();
        assertEquals(code, ReturnCode.FAILURE);
    }

    @Test
    void testNoArgs() {
        CatCommand cat = new CatCommand(Collections.emptyList(),
                new ByteArrayInputStream("".getBytes()));

        ReturnCode code = cat.execute();
        assertEquals(code, ReturnCode.SUCCESS);

        ByteArrayOutputStream stream = cat.getOutputStream();
        String output = stream.toString(StandardCharsets.UTF_8);
        assertTrue(output.isEmpty());
    }

    @Test
    void testHappyPath() throws Exception {
        String filePath = "src/test/resources/file.txt";

        CatCommand cat = new CatCommand(
                List.of(filePath),
                new ByteArrayInputStream("".getBytes()));

        ReturnCode code = cat.execute();
        assertEquals(code, ReturnCode.SUCCESS);

        File expectedFile = new File(filePath);

        String stream = cat.getOutputStream().toString();
        String expected = String.join(System.getProperty("line.separator"), FileUtils.readLines(expectedFile, StandardCharsets.UTF_8));
        assertEquals(expected, stream);
    }

}