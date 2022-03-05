package ru.hse.sd.cli.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.hse.sd.cli.Memory;
import ru.hse.sd.cli.enums.ReturnCode;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class LsCommandTest {

    private final Memory memory = new Memory();

    @TempDir
    File temporaryFolder;

    public void createTempFiles(File directory) {
        try {
            final File test_file1 = new File(directory, "test_file1.txt");
            final File test_file2 = new File(directory, "test_file2.txt");
            FileWriter fw1 = new FileWriter(test_file1);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            bw1.write("content of test file");
            bw1.close();
            FileWriter fw2 = new FileWriter(test_file2);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            bw2.write("long content of other test file");
            bw2.close();
        } catch (IOException e) {
            System.out.println("Problems with test files");
        }
        memory.changeCurrentDirectory(String.valueOf(temporaryFolder));
    }

    @Test
    public void testLsNoArgs() {
        createTempFiles(temporaryFolder);
        LsCommand ls = new LsCommand(Collections.emptyList(), new ByteArrayInputStream("".getBytes()), memory);
        ReturnCode result = ls.execute();
        Assertions.assertEquals(ReturnCode.SUCCESS, result);
        Assertions.assertEquals("test_file1.txt\n" + "test_file2.txt\n", ls.getOutputStream().toString());
    }

    @Test
    public void testLsMultipleArgs() throws IOException {
        Path dirFst = Path.of(temporaryFolder.getPath()).resolve("dir1");
        Files.createDirectory(dirFst);
        Path dirSnd = Path.of(temporaryFolder.getPath()).resolve("dir2");
        Files.createDirectory(dirSnd);
        createTempFiles(new File(String.valueOf(dirFst)));
        createTempFiles(new File(String.valueOf(dirSnd)));
        memory.changeCurrentDirectory(String.valueOf(temporaryFolder));
        LsCommand ls = new LsCommand(List.of(temporaryFolder + File.separator + "dir1",
                temporaryFolder + File.separator + "dir2"), new ByteArrayInputStream("".getBytes()), memory);
        ReturnCode result = ls.execute();
        Assertions.assertEquals(ReturnCode.SUCCESS, result);
        Assertions.assertEquals("\n" + temporaryFolder + File.separator + "dir1:\n" + "test_file1.txt\n" +
                "test_file2.txt\n\n" + temporaryFolder + File.separator + "dir2:\n" + "test_file1.txt\n" +
                "test_file2.txt\n", ls.getOutputStream().toString());
    }

    @Test
    public void testLsEmpty() throws IOException {
        Path emptyDir = Path.of(temporaryFolder.getPath()).resolve("emptyDir");
        Files.createDirectory(emptyDir);
        LsCommand ls = new LsCommand(List.of(emptyDir.toString()), new ByteArrayInputStream("".getBytes()), memory);
        ReturnCode result = ls.execute();
        Assertions.assertEquals(ReturnCode.SUCCESS, result);
        Assertions.assertEquals('\n' + emptyDir.toString() + ":\n", ls.getOutputStream().toString());
    }

    @Test
    public void testLsFiles() {
        createTempFiles(temporaryFolder);
        LsCommand ls = new LsCommand(List.of(temporaryFolder + File.separator + "test_file1.txt",
                temporaryFolder + File.separator + "test_file2.txt"), new ByteArrayInputStream("".getBytes()), memory);
        ReturnCode result = ls.execute();
        Assertions.assertEquals(ReturnCode.SUCCESS, result);
        Assertions.assertEquals(temporaryFolder + File.separator + "test_file1.txt\n" +
                temporaryFolder + File.separator + "test_file2.txt\n", ls.getOutputStream().toString());
    }
}
