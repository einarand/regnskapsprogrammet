package org.einar.regnskap.receipts;

import org.joda.time.DateTime;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by
 * User: einahage
 * Date: 2/16/14
 * Time: 8:15 PM
 */
public class Receipt {

    private final Store store;
    private final DateTime dateTime;
    private Price subTotal;
    private int nrOfItems;
    private LinkedList<LineItem> lineItems;

    public Receipt(Store store, DateTime dateTime) {
        this.store = store;
        this.dateTime = dateTime;
        this.lineItems = new LinkedList<LineItem>();
    }

    public Receipt(Store store, DateTime dateTime, Price subTotal, int nrOfItems) {
        this.store = store;
        this.dateTime = dateTime;
        this.subTotal = subTotal;
        this.nrOfItems = nrOfItems;
        this.lineItems = new LinkedList<LineItem>();
    }

    public void setLineItems(LinkedList<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public void addItem(LineItem lineItem) {
        lineItems.add(lineItem);
    }

    public Store getStore() {
        return store;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public boolean verifySubTotal() {
        return getCalculatedSubTotal().equals(this.subTotal);
    }

    public Price getCalculatedSubTotal() {
        Price subTotal = Price.ZERO;
        for (LineItem i : lineItems) {
            subTotal = subTotal.add(i.getTotal());
        }
        return subTotal;
    }

    public int getCalculatedNrOfItems() {
         int nrOfItems = 0;
         for (LineItem i : lineItems) {
             nrOfItems += i.getQuantity();
         }
         return nrOfItems;
     }

    public boolean verifyNrOfItems() {
        return getCalculatedNrOfItems() == nrOfItems;
    }

    public Price getSubTotal() {
        return subTotal;
    }

    public Collection<LineItem> getLineItems() {
        return lineItems;
    }

    public int getNrOfItems() {
        return nrOfItems;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "store=" + store +
                ", dateTime=" + (dateTime == null ? null : dateTime.toString("dd.MM.yyyy HH:mm")) +
                ", lineItems=" + nrOfItems +
                ", amount=" + getSubTotal() +
                '}';
    }

}
