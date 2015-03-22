package org.einar.regnskap.receipts.finders;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by
 * User: einahage
 * Date: 2/21/14
 * Time: 9:49 AM
 */
public class DateTimeFinderTest {

    @Test
    public void testFind() throws Exception {
        assertNotNull(DateTimeFinder.find("20.02.14 21:18"));
        //assertNull(DateTimeFinder.find("20/02/2014 21:18"));
    }
}
