package org.einar.regnskap.rema1000.model;

import java.util.List;

public class Receipt {
    private long id;
    private List<ProductItem> products;

    public List<ProductItem> getProducts() {
        return products;
    }

    public void setProducts(List<ProductItem> products) {
        this.products = products;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
