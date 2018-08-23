package org.einar.regnskap.rema1000.model;

import java.util.List;

import org.einar.regnskap.rema1000.api.TransactionPayment;

public class ReceiptSummary  {
    private double amount;
    private double bonusPoints;
    private double discount;
    private boolean hasViewed;
    private long id;
    private long purchaseDate;
    private String storeId;
    private String storeName;
    private List<TransactionPayment> transactionPayments;

    public double realmGet$amount() {
        return this.amount;
    }

    public double realmGet$bonusPoints() {
        return this.bonusPoints;
    }

    public double realmGet$discount() {
        return this.discount;
    }

    public boolean realmGet$hasViewed() {
        return this.hasViewed;
    }

    public long realmGet$id() {
        return this.id;
    }

    public long realmGet$purchaseDate() {
        return this.purchaseDate;
    }

    public String realmGet$storeId() {
        return this.storeId;
    }

    public String realmGet$storeName() {
        return this.storeName;
    }

    public List<TransactionPayment> realmGet$transactionPayments() {
        return this.transactionPayments;
    }

    public void realmSet$amount(double d) {
        this.amount = d;
    }

    public void realmSet$bonusPoints(double d) {
        this.bonusPoints = d;
    }

    public void realmSet$discount(double d) {
        this.discount = d;
    }

    public void realmSet$hasViewed(boolean z) {
        this.hasViewed = z;
    }

    public void realmSet$id(long j) {
        this.id = j;
    }

    public void realmSet$purchaseDate(long j) {
        this.purchaseDate = j;
    }

    public void realmSet$storeId(String str) {
        this.storeId = str;
    }

    public void realmSet$storeName(String str) {
        this.storeName = str;
    }

    public void realmSet$transactionPayments(List<TransactionPayment> realmList) {
        this.transactionPayments = realmList;
    }

    public long getId() {
        return realmGet$id();
    }

    public void setId(long id) {
        realmSet$id(id);
    }

    public long getPurchaseDate() {
        return realmGet$purchaseDate();
    }

    public void setPurchaseDate(long purchaseDate) {
        realmSet$purchaseDate(purchaseDate);
    }

    public String getStoreId() {
        return realmGet$storeId();
    }

    public void setStoreId(String storeId) {
        realmSet$storeId(storeId);
    }

    public String getStoreName() {
        return realmGet$storeName();
    }

    public void setStoreName(String storeName) {
        realmSet$storeName(storeName);
    }

    public double getAmount() {
        return realmGet$amount();
    }

    public void setAmount(double amount) {
        realmSet$amount(amount);
    }

    public double getBonusPoints() {
        return realmGet$bonusPoints();
    }

    public void setBonusPoints(double bonusPoints) {
        realmSet$bonusPoints(bonusPoints);
    }

    public double getDiscount() {
        return realmGet$discount();
    }

    public void setDiscount(double discount) {
        realmSet$discount(discount);
    }

    public boolean hasViewed() {
        return realmGet$hasViewed();
    }

    public void setHasViewed(boolean hasViewed) {
        realmSet$hasViewed(hasViewed);
    }

    public boolean equals(Object obj) {
        return (obj instanceof ReceiptSummary) && ((ReceiptSummary) obj).getId() == getId();
    }

    public List<TransactionPayment> getTransactionPayments() {
        return realmGet$transactionPayments();
    }

    public void setTransactionPayments(List<TransactionPayment> transactionPayments) {
        realmSet$transactionPayments(transactionPayments);
    }
}
