package org.einar.regnskap;

import junit.framework.TestCase;
import org.einar.regnskap.parser.CategoryParser;

public class CategoryParserTest extends TestCase {

    public void testParse() {
        Category c = CategoryParser.parse("Dagligvarer,FFFFFF,rimi ,rema ,kiwi ,meny ,ica ");
        assertEquals("Dagligvarer", c.getName());
        assertEquals(5 ,c.getStrings().size());
        assertTrue(c.getStrings().contains("rimi "));
    }


}
