package org.einar.regnskap.rema1000.model;

import java.util.List;

public class ProductItem {
    private double amount;
    private double deposit;
    private double discount;
    private String gtin;
    private byte[] imageByteArray;
    private String imageUrl;
    private boolean isBonusBased;
    private List<Offer> offers;
    private int pieces;
    private String productCode;
    private String productDescription;
    private String productGroupCode;
    private String productGroupDescription;
    private String productName;
    private String storedProductUnit;
    private double volume;

    public double realmGet$amount() {
        return this.amount;
    }

    public double realmGet$deposit() {
        return this.deposit;
    }

    public double realmGet$discount() {
        return this.discount;
    }

    public String realmGet$gtin() {
        return this.gtin;
    }

    public byte[] realmGet$imageByteArray() {
        return this.imageByteArray;
    }

    public String realmGet$imageUrl() {
        return this.imageUrl;
    }

    public boolean realmGet$isBonusBased() {
        return this.isBonusBased;
    }

    public List<Offer> realmGet$offers() {
        return this.offers;
    }

    public int realmGet$pieces() {
        return this.pieces;
    }

    public String realmGet$productCode() {
        return this.productCode;
    }

    public String realmGet$productDescription() {
        return this.productDescription;
    }

    public String realmGet$productGroupCode() {
        return this.productGroupCode;
    }

    public String realmGet$productGroupDescription() {
        return this.productGroupDescription;
    }

    public String realmGet$productName() {
        return this.productName;
    }

    public String realmGet$storedProductUnit() {
        return this.storedProductUnit;
    }

    public double realmGet$volume() {
        return this.volume;
    }

    public void realmSet$amount(double d) {
        this.amount = d;
    }

    public void realmSet$deposit(double d) {
        this.deposit = d;
    }

    public void realmSet$discount(double d) {
        this.discount = d;
    }

    public void realmSet$gtin(String str) {
        this.gtin = str;
    }

    public void realmSet$imageByteArray(byte[] bArr) {
        this.imageByteArray = bArr;
    }

    public void realmSet$imageUrl(String str) {
        this.imageUrl = str;
    }

    public void realmSet$isBonusBased(boolean z) {
        this.isBonusBased = z;
    }

    public void realmSet$offers(List<Offer> realmList) {
        this.offers = realmList;
    }

    public void realmSet$pieces(int i) {
        this.pieces = i;
    }

    public void realmSet$productCode(String str) {
        this.productCode = str;
    }

    public void realmSet$productDescription(String str) {
        this.productDescription = str;
    }

    public void realmSet$productGroupCode(String str) {
        this.productGroupCode = str;
    }

    public void realmSet$productGroupDescription(String str) {
        this.productGroupDescription = str;
    }

    public void realmSet$productName(String str) {
        this.productName = str;
    }

    public void realmSet$storedProductUnit(String str) {
        this.storedProductUnit = str;
    }

    public void realmSet$volume(double d) {
        this.volume = d;
    }

    public String getProductCode() {
        return realmGet$productCode();
    }

    public void setProductCode(String productCode) {
        realmSet$productCode(productCode);
    }

    public String getProductDescription() {
        return realmGet$productDescription();
    }

    public void setProductDescription(String productDescription) {
        realmSet$productDescription(productDescription);
    }

    public String getProductGroupCode() {
        return realmGet$productGroupCode();
    }

    public void setProductGroupCode(String productGroupCode) {
        realmSet$productGroupCode(productGroupCode);
    }

    public int getPieces() {
        return realmGet$pieces();
    }

    public void setPieces(int pieces) {
        realmSet$pieces(pieces);
    }

    public double getAmount() {
        return realmGet$amount();
    }

    public void setAmount(double amount) {
        realmSet$amount(amount);
    }

    public double getDiscount() {
        return realmGet$discount();
    }

    public void setDiscount(double discount) {
        realmSet$discount(discount);
    }

    public String getImageUrl() {
        return realmGet$imageUrl();
    }

    public void setImageUrl(String imageUrl) {
        realmSet$imageUrl(imageUrl);
    }

    public String getProductName() {
        return realmGet$productName();
    }

    public void setProductName(String productName) {
        realmSet$productName(productName);
    }

    public String getStoredProductUnit() {
        return realmGet$storedProductUnit();
    }

    public void setStoredProductUnit(String storedProductUnit) {
        realmSet$storedProductUnit(storedProductUnit);
    }

    public ProductUnit getProductUnit() {
        if (getStoredProductUnit() != null) {
            return ProductUnit.valueOf(getStoredProductUnit());
        }
        return null;
    }

    public void setProductUnit(ProductUnit productUnit) {
        setStoredProductUnit(productUnit.toString());
    }

    public String getGtin() {
        return realmGet$gtin();
    }

    public void setGtin(String gtin) {
        realmSet$gtin(gtin);
    }

    public List<Offer> getOffers() {
        return realmGet$offers();
    }

    public void setOffers(List<Offer> offers) {
        realmSet$offers(offers);
    }

    public void setDeposit(double deposit) {
        realmSet$deposit(deposit);
    }

    public double getVolume() {
        return realmGet$volume();
    }

    public void setVolume(double volume) {
        realmSet$volume(volume);
    }

    private double getTotalPrice() {
        return (realmGet$amount() - realmGet$deposit()) + Math.abs(realmGet$discount());
    }

    public Double getPricePerUnit() {
        double price;
        if (getProductUnit() == null || getProductUnit() != ProductUnit.KG) {
            price = realmGet$amount() / ((double) realmGet$pieces());
        } else {
            price = realmGet$amount() / realmGet$volume();
        }
        return Double.valueOf(price);
    }

    public String getPiecesOrVolume() {
        if (getProductUnit() == null || getProductUnit() != ProductUnit.KG) {
            return FormatUtil.formatPercent((double) realmGet$pieces());
        }
        return FormatUtil.formatKgDecimal(realmGet$volume());
    }
}
