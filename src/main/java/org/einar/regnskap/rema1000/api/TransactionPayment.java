package org.einar.regnskap.rema1000.api;

public class TransactionPayment {
    private double amount;
    private String meansOfPaymentDesc;

    public double realmGet$amount() {
        return this.amount;
    }

    public String realmGet$meansOfPaymentDesc() {
        return this.meansOfPaymentDesc;
    }

    public void realmSet$amount(double d) {
        this.amount = d;
    }

    public void realmSet$meansOfPaymentDesc(String str) {
        this.meansOfPaymentDesc = str;
    }

    public TransactionPayment() {

    }

    public String getMeansOfPaymentDesc() {
        return realmGet$meansOfPaymentDesc();
    }

    public void setMeansOfPaymentDesc(String meansOfPaymentDesc) {
        realmSet$meansOfPaymentDesc(meansOfPaymentDesc);
    }

    public double getAmount() {
        return realmGet$amount();
    }

    public void setAmount(double amount) {
        realmSet$amount(amount);
    }
}
