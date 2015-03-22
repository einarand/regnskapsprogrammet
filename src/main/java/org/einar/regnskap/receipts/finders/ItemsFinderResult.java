package org.einar.regnskap.receipts.finders;

import org.einar.regnskap.receipts.LineItem;
import org.einar.regnskap.receipts.Price;

import java.util.LinkedList;

/**
 * Created by
 * User: einahage
 * Date: 2/19/14
 * Time: 11:15 PM
 */
public class ItemsFinderResult {

    private final LinkedList<LineItem> lineItems;
    private final int nrOfItems;
    private final Price subTotal;

    public ItemsFinderResult(LinkedList<LineItem> lineItems, int nrOfItems, Price subTotal) {
        this.lineItems = lineItems;
        this.nrOfItems = nrOfItems;
        this.subTotal = subTotal;
    }

    public LinkedList<LineItem> getLineItems() {
        return lineItems;
    }

    public int getNrOfItems() {
        return nrOfItems;
    }

    public Price getSubTotal() {
        return subTotal;
    }
}
