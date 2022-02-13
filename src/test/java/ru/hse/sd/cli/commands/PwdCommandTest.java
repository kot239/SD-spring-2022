package ru.hse.sd.cli.commands;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import ru.hse.sd.cli.enums.ReturnCode;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PwdCommandTest {
    @Test
    void testPwd() {
        PwdCommand pwd = new PwdCommand(
                new ByteArrayInputStream("".getBytes()));

        ReturnCode code = pwd.execute();
        assertEquals(ReturnCode.SUCCESS, code);

        ByteArrayOutputStream stream = (ByteArrayOutputStream) pwd.getOutputStream();
        String output = stream.toString(StandardCharsets.UTF_8);
        assertEquals(System.getProperty("user.dir") + System.getProperty("line.separator"), output);
    }


}