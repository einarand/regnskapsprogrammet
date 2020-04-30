package org.einar.regnskap.rema1000.api;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.einar.regnskap.rema1000.model.FormatUtil;

public class TransactionHeads {
    private long purchaseDate;
    private double bonusTotal;
    private double discountTotal;
    private double purchaseTotal;
    private List<Transaction> transactions;

    public double getBonusTotal() {
        return this.bonusTotal;
    }

    public void setBonusTotal(double bonusTotal) {
        this.bonusTotal = bonusTotal;
    }

    public double getPurchaseTotal() {
        return this.purchaseTotal;
    }

    public void setPurchaseTotal(double purchaseTotal) {
        this.purchaseTotal = purchaseTotal;
    }

    public double getDiscountTotal() {
        return this.discountTotal;
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Instant purchaseDate() {
        return Instant.ofEpochMilli(purchaseDate);
    }
}
