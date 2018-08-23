package org.einar.regnskap.rema1000.model;

public class Offer {
    private String description;
    private double discount;
    private int discountPercent;
    private String offerCode;

    public String realmGet$description() {
        return this.description;
    }

    public double realmGet$discount() {
        return this.discount;
    }

    public int realmGet$discountPercent() {
        return this.discountPercent;
    }

    public String realmGet$offerCode() {
        return this.offerCode;
    }

    public void realmSet$description(String str) {
        this.description = str;
    }

    public void realmSet$discount(double d) {
        this.discount = d;
    }

    public void realmSet$discountPercent(int i) {
        this.discountPercent = i;
    }

    public void realmSet$offerCode(String str) {
        this.offerCode = str;
    }

    public Offer(String offerCode, String description, double discount, int discountPercent) {
        realmSet$offerCode(offerCode);
        realmSet$description(description);
        realmSet$discount(discount);
        realmSet$discountPercent(discountPercent);
    }

    public String getOfferCode() {
        return realmGet$offerCode();
    }

    public void setOfferCode(String offerCode) {
        realmSet$offerCode(offerCode);
    }

    public String getDescription() {
        return realmGet$description();
    }

    public void setDescription(String description) {
        realmSet$description(description);
    }

    public double getDiscount() {
        return realmGet$discount();
    }

    public void setDiscount(double discount) {
        realmSet$discount(discount);
    }

    public int getDiscountPercent() {
        return realmGet$discountPercent();
    }

    public void setDiscountPercent(int discountPercent) {
        realmSet$discountPercent(discountPercent);
    }
}
