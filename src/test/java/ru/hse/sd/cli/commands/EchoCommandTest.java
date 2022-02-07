package ru.hse.sd.cli.commands;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import ru.hse.sd.cli.enums.ReturnCode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class EchoCommandTest {
    @Test
    void testNoArgs() {
        EchoCommand echo = new EchoCommand(Collections.emptyList());

        ReturnCode code = echo.execute();
        assertEquals(code, ReturnCode.SUCCESS);

        String stream = echo.getOutputStream();
        assertNull(stream);
    }

    @Test
    void testSimpleEcho() {
        EchoCommand echo = new EchoCommand(List.of("friend"));

        ReturnCode code = echo.execute();
        assertEquals(code, ReturnCode.SUCCESS);

        String stream = echo.getOutputStream();
        assertEquals("friend", stream);
    }

    @Test
    void testFewArgumentsEcho() {
        EchoCommand echo = new EchoCommand(List.of("hello", "my", "friend"));

        ReturnCode code = echo.execute();
        assertEquals(code, ReturnCode.SUCCESS);

        String stream = echo.getOutputStream();
        assertEquals("hello my friend", stream);
    }

}