package org.einar.regnskap.rema1000.api;

import java.util.ArrayList;

public class TransactionRow {
    private double amount;
    private boolean bonusBased;
    private double deposit;
    private double discount;
    private int pieces;
    private String prodtxt1;
    private String prodtxt2;
    private String prodtxt3;
    private String productCode;
    private String productDescription;
    private String productGroupCode;
    private String unit;
    private ArrayList<UsedOffer> usedOffers;
    private double volume;

    public String getProductCode() {
        return this.productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductDescription() {
        return this.productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProdtxt1() {
        return this.prodtxt1;
    }

    public void setProdtxt1(String prodtxt1) {
        this.prodtxt1 = prodtxt1;
    }

    public String getProdtxt2() {
        return this.prodtxt2;
    }

    public void setProdtxt2(String prodtxt2) {
        this.prodtxt2 = prodtxt2;
    }

    public String getProdtxt3() {
        return this.prodtxt3;
    }

    public void setProdtxt3(String prodtxt3) {
        this.prodtxt3 = prodtxt3;
    }

    public String getProductGroupCode() {
        return this.productGroupCode;
    }

    public void setProductGroupCode(String productGroupCode) {
        this.productGroupCode = productGroupCode;
    }

    public boolean isBonusBased() {
        return this.bonusBased;
    }

    public void setBonusBased(boolean bonusBased) {
        this.bonusBased = bonusBased;
    }

    public int getPieces() {
        return this.pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getDiscount() {
        return this.discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public ArrayList<UsedOffer> getUsedOffers() {
        return this.usedOffers;
    }

    public void setUsedOffers(ArrayList<UsedOffer> usedOffers) {
        this.usedOffers = usedOffers;
    }

    public double getDeposit() {
        return this.deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getVolume() {
        return this.volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
