package org.einar.regnskap;

import junit.framework.TestCase;
import org.einar.regnskap.parser.SSFTransactionParser;
import org.einar.regnskap.parser.TransactionParser;
import org.einar.regnskap.receipts.Price;

public class SSFTransactionParserTest extends TestCase {

    public void testParse() throws Exception {
        TransactionParser p = new SSFTransactionParser();
        Transaction t = p.parse("03.09.2010\t03.09.2010\tVARER\t02.09 KREMMERHUSET LINDERUD OSLO\t-490,00\t17017433811\t");
        assertEquals("02.09 KREMMERHUSET LINDERUD OSLO", t.getDescription());
        assertEquals(new Price("-490.0"), t.getAmount());
        //TODO reference assertEquals(, t.getReference());
        assertNull(t.getAccount());

    }
}
