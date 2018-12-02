package analyze.util;

import org.junit.Test;

import static analyze.TestData.RECORD_1;
import static analyze.util.Util.isRecordValid;
import static org.junit.Assert.*;

public class UtilTest {
    @Test
    public void isRecordValidTest() {
        assertTrue(isRecordValid(RECORD_1, 100));
        assertFalse(isRecordValid(RECORD_1, 10));
    }
}
