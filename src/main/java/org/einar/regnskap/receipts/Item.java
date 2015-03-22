package org.einar.regnskap.receipts;

import org.einar.regnskap.receipts.Price;

/**
 * Created by einahage on 12/04/14.
 */
public class Item {

    private final String description;
    private final Price price;

    public Item(String description, Price price) {
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public Price getPrice() {
        return price;
    }
}
