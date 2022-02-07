package test.java.ru.hse.sd.cli.commands;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import main.java.ru.hse.sd.cli.commands.CatCommand;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import main.java.ru.hse.sd.cli.enums.ReturnCode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CatCommandTest {
    @Test
    void testTooManyArgs() throws Exception {
        URL resource1 = getClass().getClassLoader().getResource("file.txt");
        URL resource2 = getClass().getClassLoader().getResource("file2.txt");
        assertNotNull(resource1);
        assertNotNull(resource2);

        CatCommand cat = new CatCommand(List.of(
                resource1.toURI().getPath(),
                resource2.toURI().getPath()
        ));

        ReturnCode code = cat.execute();
        assertEquals(code, ReturnCode.FAILURE);
    }

    @Test
    void testNoArgs() {
        CatCommand cat = new CatCommand(Collections.emptyList());

        ReturnCode code = cat.execute();
        assertEquals(code, ReturnCode.SUCCESS);

        String stream = cat.getOutputStream();
        assertNull(stream);
    }

    @Test
    void testHappyPath() throws Exception {
        URL resource = getClass().getClassLoader().getResource("file.txt");
        assertNotNull(resource);

        CatCommand cat = new CatCommand(List.of(resource.toURI().getPath()));

        ReturnCode code = cat.execute();
        assertEquals(code, ReturnCode.SUCCESS);

        File expectedFile = new File(resource.toURI());
        String stream = cat.getOutputStream();
        assertEquals(stream, String.join("\n", FileUtils.readLines(expectedFile, StandardCharsets.UTF_8)));
    }
}