package analyze.service;

import analyze.model.Record;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static analyze.TestData.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;

public class InputOutputServiceTest {

    private InputOutputService ioService;
    private ByteArrayOutputStream out;

    @Before
    public void before() throws IOException {
        out = new ByteArrayOutputStream();
        ClassLoader classLoader = getClass().getClassLoader();
        ioService = new InputOutputService(new FileInputStream(new File("src/test/java/resources/access.log")), out);
    }

    @Test
    public void readList() throws IOException {
        List<Record> records = ioService.read(0, 3);
        List<Record> actual = Arrays.asList(RECORD_0, RECORD_1, RECORD_2, RECORD_3);
        assertThat(actual, is(records));
    }

    @Test
    public void readByIndex() throws IOException {
        Record record = ioService.read(2);
        assertEquals(RECORD_2, record);
    }

    @Test
    public void write() throws IOException {
        ioService.write("Hello drom!!!");
        String expected = out.toString();
        assertEquals("Hello drom!!!\n", expected);
    }

}
