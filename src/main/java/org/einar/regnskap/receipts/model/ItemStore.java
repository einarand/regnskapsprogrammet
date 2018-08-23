package org.einar.regnskap.receipts.model;


import org.einar.regnskap.receipts.Item;

public interface ItemStore {

    Item getBestSuggestion(String description);

    void storeItem(Item item);
}
