package org.einar.regnskap.receipts;

/**
 * Created by
 * User: einahage
 * Date: 2/16/14
 * Time: 8:24 PM
 */
public interface ReceiptParser {

    Receipt parseFromFile(String filename);

}
