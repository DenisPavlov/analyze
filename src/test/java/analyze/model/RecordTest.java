package analyze.model;

import analyze.exception.RecordCreateException;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static analyze.TestData.LINE;
import static org.junit.Assert.assertEquals;

public class RecordTest {

    @Test(expected = RecordCreateException.class)
    public void constructor(){
        new Record("123", 0);
    }

    @Test
    public void creaate(){
        Record expected = new Record(LINE, 0);

        assertEquals(0, expected.getIndex());
        assertEquals(200, expected.getCode());
        assertEquals(LocalDateTime.of(2017, Month.JUNE, 14, 16, 47, 2), expected.getDateTime());
        assertEquals(144.510983d, expected.getRequestTime(), 0.000001);
    }
}
