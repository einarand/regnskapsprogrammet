package org.einar.regnskap.receipts.db;

import org.einar.regnskap.receipts.Item;
import org.einar.regnskap.receipts.model.Items;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by einahage on 12/04/14.
 */
public class ItemsInMemoryStore implements Items {

    private final Set<Item> items = new HashSet<Item>();

    public Item getBestSuggestion(String description) {
        for (Item i : items) {
            if(i.getDescription().startsWith(description)) {
                return i;
            }
        }
        return null;
    }

    public void storeItem(Item item) {
        items.add(item);
    }
}
