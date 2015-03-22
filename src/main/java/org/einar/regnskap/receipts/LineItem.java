package org.einar.regnskap.receipts;

public class LineItem {

    private final int index;
    private final Item item;
    private final int quantity;

    public LineItem(String description, Price total, int index) {
        this(1, description, total, index);
    }

    public LineItem(int quantity, String description, Price total, int index) {
        this(quantity, new Item(description, total.divide(quantity)), index);
    }

    public LineItem(int quantity, Item item, int index) {
        this.quantity = quantity;
        this.item = item;
        this.index = index;
    }

    public String getDescription() {
        return item.getDescription();
    }

    public Price getTotal() {
        return item.getPrice().multiply(quantity);
    }

    public Price getItemPrice() {
        return item.getPrice();
    }

    public int getQuantity() {
        return quantity;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "LineItem{" +
                "idx='" + getIndex() + '\'' +
                ", qty='" + getQuantity() + '\'' +
                ", desc='" + getDescription() + '\'' +
                ", price='" + getItemPrice() + '\'' +
                ", total=" + getTotal() +
                '}';
    }

    public Item getItem() {
        return item;
    }
}