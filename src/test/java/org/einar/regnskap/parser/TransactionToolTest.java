package org.einar.regnskap.parser;

import org.einar.regnskap.transactions.Transaction;
import org.einar.regnskap.receipts.Price;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TransactionToolTest {

    @Test
    public void testFilterOnPeriod() throws Exception {

    }

    @Test
    public void testIsWithinPeriod() throws Exception {
        Transaction t = new Transaction(parseDate("02.02.2012"), "Test", Price.ZERO, 0, null);
        assertTrue(TransactionTool.isWithinPeriod(parseDate("01.02.2012"), parseDate("03.02.2012"), t));
        assertTrue(TransactionTool.isWithinPeriod(parseDate("02.02.2012"), parseDate("03.02.2012"), t));
        //assertTrue(TransactionTool.isWithinPeriod(parseDate("02.02.2012"), parseDate("02.02.2012"), t));
        assertFalse(TransactionTool.isWithinPeriod(parseDate("03.02.2012"), parseDate("01.02.2012"), t));
    }

    private static DateTime parseDate(String dateString) {
        return DateTimeFormat.forPattern("dd.MM.yyyy").parseDateTime(dateString);
    }
}
