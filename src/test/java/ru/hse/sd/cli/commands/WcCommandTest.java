package test.java.ru.hse.sd.cli.commands;

import java.net.URL;
import java.util.List;

import main.java.ru.hse.sd.cli.commands.WcCommand;
import org.junit.jupiter.api.Test;
import main.java.ru.hse.sd.cli.enums.ReturnCode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class WcCommandTest {

    @Test
    void testNoSuchFile() {
        WcCommand wc = new WcCommand(List.of("not_exist.txt"));

        ReturnCode code = wc.execute();
        assertEquals(ReturnCode.FAILURE, code);

    }

    @Test
    void testOneFile() throws Exception {
        URL resource = getClass().getClassLoader().getResource("file.txt");
        assertNotNull(resource);
        WcCommand wc = new WcCommand(List.of(resource.toURI().getPath()));

        ReturnCode code = wc.execute();
        assertEquals(ReturnCode.SUCCESS, code);

        String stream = wc.getOutputStream();
        assertEquals("2 8 38\n", stream);
    }

    @Test
    void testMultipleFiles() throws Exception {
        URL resource1 = getClass().getClassLoader().getResource("file.txt");
        URL resource2 = getClass().getClassLoader().getResource("file2.txt");
        assertNotNull(resource1);
        assertNotNull(resource2);

        WcCommand wc = new WcCommand(List.of(
                resource1.toURI().getPath(),
                resource2.toURI().getPath()
        ));

        ReturnCode code = wc.execute();
        assertEquals(ReturnCode.SUCCESS, code);

        String stream = wc.getOutputStream();
        assertEquals("2 8 38\n3 10 49\n", stream);
    }

}