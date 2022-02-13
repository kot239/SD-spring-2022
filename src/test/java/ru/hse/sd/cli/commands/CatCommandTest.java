package ru.hse.sd.cli.commands;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import ru.hse.sd.cli.enums.ReturnCode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CatCommandTest {
    @Test
    void testTooManyArgs() throws Exception {
        URL resource1 = getClass().getClassLoader().getResource("file.txt");
        URL resource2 = getClass().getClassLoader().getResource("file2.txt");
        assertNotNull(resource1);
        assertNotNull(resource2);

        CatCommand cat = new CatCommand(List.of(
                resource1.toURI().getPath(),
                resource2.toURI().getPath()
        ),
                new ByteArrayInputStream("".getBytes()), new ByteArrayOutputStream());

        ReturnCode code = cat.execute();
        assertEquals(code, ReturnCode.FAILURE);
    }

    @Test
    void testNoArgs() {
        CatCommand cat = new CatCommand(Collections.emptyList(),
                new ByteArrayInputStream("".getBytes()), new ByteArrayOutputStream());

        ReturnCode code = cat.execute();
        assertEquals(code, ReturnCode.SUCCESS);

        ByteArrayOutputStream stream = (ByteArrayOutputStream) cat.getOutputStream();
        String output = stream.toString(StandardCharsets.UTF_8);
        assertTrue(output.isEmpty());
    }

    @Test
    void testHappyPath() throws Exception {
        URL resource = getClass().getClassLoader().getResource("file.txt");
        assertNotNull(resource);

        CatCommand cat = new CatCommand(
                List.of(resource.toURI().getPath()),
                new ByteArrayInputStream("".getBytes()),
                new ByteArrayOutputStream());

        ReturnCode code = cat.execute();
        assertEquals(code, ReturnCode.SUCCESS);

        File expectedFile = new File(resource.toURI());

        String stream = cat.getOutputStream().toString();
        assertEquals(stream, String.join("\n", FileUtils.readLines(expectedFile, StandardCharsets.UTF_8)));
    }
}