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
        OtherCommand other;
        if (System.getProperty("os.name").contains("Windows")) {
            other = new OtherCommand("cd", emptyList(),
                    new ByteArrayInputStream("".getBytes()), emptyMap());
        } else {
            other = new OtherCommand("pwd", emptyList(),
                    new ByteArrayInputStream("".getBytes()), emptyMap());
        }

        ReturnCode code = other.execute();
        assertEquals(ReturnCode.SUCCESS, code);

        ByteArrayOutputStream stream = other.getOutputStream();
        String output = stream.toString(StandardCharsets.UTF_8).trim();
        assertEquals(System.getProperty("user.dir"), output);
    }
}