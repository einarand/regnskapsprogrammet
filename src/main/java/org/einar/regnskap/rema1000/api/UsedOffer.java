package org.einar.regnskap.rema1000.api;

public class UsedOffer {
    private double discount;
    private int discountPercent;
    private String offerCode;
    private String offerDesc;

    public String getOfferCode() {
        return this.offerCode;
    }

    public void setOfferCode(String offerCode) {
        this.offerCode = offerCode;
    }

    public String getOfferDesc() {
        return this.offerDesc;
    }

    public void setOfferDesc(String offerDesc) {
        this.offerDesc = offerDesc;
    }

    public double getDiscount() {
        return this.discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getDiscountPercent() {
        return this.discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }
}
