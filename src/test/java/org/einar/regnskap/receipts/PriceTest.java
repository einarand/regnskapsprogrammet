package org.einar.regnskap.receipts;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by einahage on 02/05/14.
 */
public class PriceTest {
    @Test
    public void testLessThan() throws Exception {
        assertTrue(new Price("1").lessThan(new Price("2")));
        assertFalse(new Price("2").lessThan(new Price("1")));
        assertFalse(new Price("2").lessThan(new Price("2")));
    }

    @Test
    public void testGreaterThan() throws Exception {

    }
}
