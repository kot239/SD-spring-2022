package ru.hse.sd.cli.commands;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import ru.hse.sd.cli.enums.ReturnCode;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OtherCommandTest {
    @Test
    void testPwd() {
        OtherCommand other = new OtherCommand("pwd", emptyList(),
                new ByteArrayInputStream("".getBytes()), emptyMap());
        ReturnCode code = other.execute();
        assertEquals(ReturnCode.SUCCESS, code);

        ByteArrayOutputStream stream = (ByteArrayOutputStream) other.getOutputStream();
        String output = stream.toString(StandardCharsets.UTF_8);
        assertEquals(System.getProperty("user.dir"), output);
    }
}