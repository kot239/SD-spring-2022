package ru.hse.sd.cli.commands;

import org.junit.jupiter.api.Test;
import ru.hse.sd.cli.enums.ReturnCode;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PwdCommandTest {
    @Test
    void testPwd() {
        PwdCommand pwd = new PwdCommand();
        ReturnCode code = pwd.execute();
        assertEquals(ReturnCode.SUCCESS, code);

        String stream = pwd.getOutputStream();
        assertEquals(System.getProperty("user.dir") + "\n", stream);
    }


}