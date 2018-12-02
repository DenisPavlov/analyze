package analyze;

import analyze.service.InputOutputService;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AnalizatorTest {

    private ByteArrayOutputStream out;
    ClassLoader classLoader;
    Analizator analizator;

    @Before
    public void setUp() throws Exception {
        out = new ByteArrayOutputStream();
        classLoader = getClass().getClassLoader();
    }

    // TODO: 02.12.18 add description, has 3 invalid records podryad
    @Test
    public void analyze201() throws IOException {
        InputOutputService ioService = new InputOutputService(new FileInputStream(classLoader.getResource("201_records.log").getFile()), out);
        analizator = new Analizator(98, 400, ioService);
        analizator.analyze();
        String expected = out.toString();
        assertEquals("04:47:02 04:47:02 0,0\n", expected);
    }

    @Test
    public void name() {

    }
}
