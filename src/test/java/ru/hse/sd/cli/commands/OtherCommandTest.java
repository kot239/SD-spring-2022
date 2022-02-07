package main.java.ru.hse.sd.cli.commands;


import org.junit.jupiter.api.Test;
import main.java.ru.hse.sd.cli.enums.ReturnCode;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OtherCommandTest {
    @Test
    void testPwd() {
        OtherCommand other = new OtherCommand("pwd", emptyList());
        ReturnCode code = other.execute();
        assertEquals(ReturnCode.SUCCESS, code);

        String stream = other.getOutputStream();
        assertEquals(System.getProperty("user.dir"), stream);
    }
}