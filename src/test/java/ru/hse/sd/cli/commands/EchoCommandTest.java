package ru.hse.sd.cli.commands;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import ru.hse.sd.cli.enums.ReturnCode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EchoCommandTest {
    @Test
    void testNoArgs() {
        EchoCommand echo = new EchoCommand(Collections.emptyList(),
                new ByteArrayInputStream("".getBytes()));

        ReturnCode code = echo.execute();
        assertEquals(code, ReturnCode.SUCCESS);

        ByteArrayOutputStream stream = (ByteArrayOutputStream) echo.getOutputStream();
        String output = stream.toString(StandardCharsets.UTF_8);
        assertTrue(output.isEmpty());
    }

    @Test
    void testSimpleEcho() {
        EchoCommand echo = new EchoCommand(List.of("friend"),
                new ByteArrayInputStream("".getBytes()));

        ReturnCode code = echo.execute();
        assertEquals(code, ReturnCode.SUCCESS);

        ByteArrayOutputStream stream = (ByteArrayOutputStream) echo.getOutputStream();
        String output = stream.toString(StandardCharsets.UTF_8);
        assertEquals("friend", output);
    }

    @Test
    void testFewArgumentsEcho() {
        EchoCommand echo = new EchoCommand(List.of("hello", "my", "friend"),
                new ByteArrayInputStream("".getBytes()));

        ReturnCode code = echo.execute();
        assertEquals(code, ReturnCode.SUCCESS);

        ByteArrayOutputStream stream = (ByteArrayOutputStream) echo.getOutputStream();
        String output = stream.toString(StandardCharsets.UTF_8);
        assertEquals("hello my friend", output);
    }

}