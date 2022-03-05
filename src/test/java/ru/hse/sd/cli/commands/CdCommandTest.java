package ru.hse.sd.cli.commands;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.hse.sd.cli.Memory;
import ru.hse.sd.cli.enums.ReturnCode;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CdCommandTest {
    @TempDir
    File temporaryFolder;

    @Test
    void testCdSimple() {
        Memory memory = new Memory();
        CdCommand cd = new CdCommand(Collections.emptyList(),
                new ByteArrayInputStream("".getBytes()), memory);

        ReturnCode code = cd.execute();
        assertEquals(ReturnCode.SUCCESS, code);
    }

    @Test
    void testCdChangeDirectory() {
        Memory memory = new Memory();
        CdCommand cd = new CdCommand(List.of(temporaryFolder.getPath()),
                new ByteArrayInputStream("".getBytes()), memory);

        ReturnCode code = cd.execute();
        assertEquals(ReturnCode.SUCCESS, code);
        assertEquals(temporaryFolder.getAbsolutePath(), memory.getCurrentDirectory().toAbsolutePath().toString());
    }

    @Test
    void testCdGoBackAndReset() {
        Memory memory = new Memory();
        CdCommand cd = new CdCommand(List.of(temporaryFolder.getPath()),
                new ByteArrayInputStream("".getBytes()), memory);

        ReturnCode code = cd.execute();
        assertEquals(ReturnCode.SUCCESS, code);
        assertEquals(temporaryFolder.getAbsolutePath(), memory.getCurrentDirectory().toAbsolutePath().toString());
        cd = new CdCommand(List.of(".."),
                new ByteArrayInputStream("".getBytes()), memory);
        code = cd.execute();
        assertEquals(ReturnCode.SUCCESS, code);
        cd = new CdCommand(Collections.emptyList(),
                new ByteArrayInputStream("".getBytes()), memory);
        code = cd.execute();
        assertEquals(ReturnCode.SUCCESS, code);
        assertEquals(System.getProperty("user.dir"), memory.getCurrentDirectory().toAbsolutePath().toString());
    }

    @Test
    void testCdNoFile() {
        Memory memory = new Memory();
        CdCommand cd = new CdCommand(List.of("smt"),
                new ByteArrayInputStream("".getBytes()), memory);

        ReturnCode code = cd.execute();
        assertEquals(ReturnCode.FAILURE, code);
    }

}
