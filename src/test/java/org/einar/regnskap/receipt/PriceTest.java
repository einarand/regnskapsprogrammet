package org.einar.regnskap.receipt;

import org.einar.regnskap.receipts.Price;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by
 * User: einahage
 * Date: 2/20/14
 * Time: 11:06 AM
 */
public class PriceTest {

    @Test
    public void testVerify() {
        assertEquals(Price.parsePriceFromReceiptData("10,50"), new Price("10.50"));
        assertEquals(Price.parsePriceFromReceiptData("10.50"), new Price("10.50"));
        assertEquals(Price.parsePriceFromReceiptData("10 50"), new Price("10.50"));
        assertEquals(Price.parsePriceFromReceiptData("10^50"), new Price("10.50"));
    }

}
