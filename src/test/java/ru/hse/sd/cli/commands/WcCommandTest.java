package ru.hse.sd.cli.commands;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;
import ru.hse.sd.cli.enums.ReturnCode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class WcCommandTest {

    @Test
    void testNoSuchFile() {
        WcCommand wc = new WcCommand(List.of("not_exist.txt"),
                new ByteArrayInputStream("".getBytes()));

        ReturnCode code = wc.execute();
        assertEquals(ReturnCode.FAILURE, code);

    }

    @Test
    void testOneFile() throws Exception {
        URL resource = getClass().getClassLoader().getResource("file.txt");
        assertNotNull(resource);
        WcCommand wc = new WcCommand(List.of(resource.toURI().getPath()),
                new ByteArrayInputStream("".getBytes()));

        ReturnCode code = wc.execute();
        assertEquals(ReturnCode.SUCCESS, code);

        ByteArrayOutputStream stream = (ByteArrayOutputStream) wc.getOutputStream();
        String output = stream.toString(StandardCharsets.UTF_8);
        assertEquals("2 8 38\n", output);
    }

    @Test
    void testMultipleFiles() throws Exception {
        URL resource1 = getClass().getClassLoader().getResource("file.txt");
        URL resource2 = getClass().getClassLoader().getResource("file2.txt");
        assertNotNull(resource1);
        assertNotNull(resource2);

        WcCommand wc = new WcCommand(
                List.of(
                        resource1.toURI().getPath(),
                        resource2.toURI().getPath()
                ),
                new ByteArrayInputStream("".getBytes()));

        ReturnCode code = wc.execute();
        assertEquals(ReturnCode.SUCCESS, code);

        ByteArrayOutputStream stream = (ByteArrayOutputStream) wc.getOutputStream();
        String output = stream.toString(StandardCharsets.UTF_8);
        assertEquals("2 8 38\n3 10 49\n", output);
    }

}