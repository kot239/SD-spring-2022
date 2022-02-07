package ru.hse.sd.cli.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import ru.hse.sd.cli.enums.ReturnCode;

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