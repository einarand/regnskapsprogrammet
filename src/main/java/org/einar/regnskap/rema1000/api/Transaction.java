package org.einar.regnskap.rema1000.api;

import java.util.List;

public class Transaction {
    private double amount;
    private double bonusPoints;
    private double discount;
    private long id;
    private long purchaseDate;
    private String storeId;
    private String storeName;
    private List<TransactionPayment> transactionPayments;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPurchaseDate() {
        return this.purchaseDate;
    }

    public void setPurchaseDate(long purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getStoreId() {
        return this.storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBonusPoints() {
        return this.bonusPoints;
    }

    public void setBonusPoints(double bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    public double getDiscount() {
        return this.discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public List<TransactionPayment> getTransactionPayments() {
        return this.transactionPayments;
    }

    public void setTransactionPayments(List<TransactionPayment> transactionPayments) {
        this.transactionPayments = transactionPayments;
    }
}
