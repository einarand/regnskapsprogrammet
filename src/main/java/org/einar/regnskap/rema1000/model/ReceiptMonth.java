package org.einar.regnskap.rema1000.model;

import java.util.List;

public class ReceiptMonth {
    private List<ReceiptSummary> receiptSummaries;
    private String stripeLabel;
    private double totalSaved;
    private double totalSpent;

    public List realmGet$receiptSummaries() {
        return this.receiptSummaries;
    }

    public String realmGet$stripeLabel() {
        return this.stripeLabel;
    }

    public double realmGet$totalSaved() {
        return this.totalSaved;
    }

    public double realmGet$totalSpent() {
        return this.totalSpent;
    }

    public void realmSet$receiptSummaries(List realmList) {
        this.receiptSummaries = realmList;
    }

    public void realmSet$stripeLabel(String str) {
        this.stripeLabel = str;
    }

    public void realmSet$totalSaved(double d) {
        this.totalSaved = d;
    }

    public void realmSet$totalSpent(double d) {
        this.totalSpent = d;
    }

    public String getStripeLabel() {
        return realmGet$stripeLabel();
    }

    public void setStripeLabel(String stripeLabel) {
        realmSet$stripeLabel(stripeLabel);
    }

    public double getTotalSpent() {
        return realmGet$totalSpent();
    }

    public void setTotalSpent(double totalSpent) {
        realmSet$totalSpent(totalSpent);
    }

    public double getTotalSaved() {
        return realmGet$totalSaved();
    }

    public void setTotalSaved(double totalSaved) {
        realmSet$totalSaved(totalSaved);
    }

    public List<ReceiptSummary> getReceiptSummaries() {
        return realmGet$receiptSummaries();
    }

    public void setReceiptSummaries(List<ReceiptSummary> receiptSummaries) {
        realmSet$receiptSummaries(receiptSummaries);
    }

    public long getReceiptMonthYear() {
        if (realmGet$receiptSummaries() == null || realmGet$receiptSummaries().size() <= 0) {
            return 0;
        }
        return ((ReceiptSummary) realmGet$receiptSummaries().get(0)).getPurchaseDate();
    }
}
