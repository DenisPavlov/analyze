package analyze.model;

import org.junit.Before;
import org.junit.Test;

import static analyze.TestData.*;
import static org.junit.Assert.assertEquals;

public class IntervalTest {

    @Test
    public void create() {
        Interval expected = new Interval(RECORD_0, 100);
        assertEquals(RECORD_0.getDateTime(), expected.getStart());
        assertEquals(RECORD_0.getDateTime(), expected.getEnd());
        assertEquals(0, expected.getGoodCount());
        assertEquals(1, expected.getBadCount());
        assertEquals(0, expected.getAvailabilityLevel(), 0.01);
    }

    @Test
    public void addRecord(){
        Interval expected = new Interval(RECORD_0, 100);
        expected.addRecord(RECORD_1, 100);

        assertEquals(RECORD_0.getDateTime(), expected.getStart());
        assertEquals(RECORD_1.getDateTime(), expected.getEnd());
        assertEquals(1, expected.getGoodCount());
        assertEquals(1, expected.getBadCount());
        assertEquals(50.0d, expected.getAvailabilityLevel(), 0.01);
    }

    @Test
    public void addInterval() {
        Interval expected = new Interval(RECORD_0, 100);
        Interval interval = new Interval(RECORD_1, 100);
        expected.addInterval(interval);

        assertEquals(RECORD_0.getDateTime(), expected.getStart());
        assertEquals(RECORD_1.getDateTime(), expected.getEnd());
        assertEquals(1, expected.getGoodCount());
        assertEquals(1, expected.getBadCount());
        assertEquals(50.0d, expected.getAvailabilityLevel(), 0.01);
    }
}
