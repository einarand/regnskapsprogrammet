package org.einar.regnskap.receipts.verifiers;

import java.text.DecimalFormat;

/**
 *
 * Should be on the format \\d+.\\d{2}
 */
public class Price {

    private final double value;

    public Price(double price) {
        this.value = price;
    }

    public static boolean verify(String string) {
        try {
            parsePrice(string);
            return true;
        } catch (PriceFormatException ignored) {}
        return false;
    }

    public static Price parsePrice(String string) {
        try {
            String str = string.replace(',', '.');
            return new Price(Double.parseDouble(str));
        } catch (NumberFormatException e) {
            throw new PriceFormatException("Price format error for \"" + string + "\"", e);
        }
    }

    private static final String regExp = "[, ^'´’‘]";

    public static Price parsePriceFromReceiptData(String string) {
        try {
            String str = string.replaceAll(regExp, ".");
            if (str.contains(".")) {
                return new Price(Double.parseDouble(str));
            } else {
                System.err.println("Price must contain \".\" for \"" + string + "\"");
                return null;
            }
        } catch (NumberFormatException e) {
            System.err.println("Price format error for \"" + string + "\"");
            return null;
        }
    }

    public Price add(Price price) {
        return price != null ? new Price(this.value + price.value) : new Price(value);
    }

    public Price dividedBy(int value) {
        return new Price(this.value / value);
    }

    public double value() {
        return value;
    }

    @Override
    public String toString() {
        return new DecimalFormat("0.00").format(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Math.abs(price.value - value) <= 0.01;
    }
      
    public Price rounding() {
        return new Price((double) (int) value); //A bit hacked way to round down
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(value);
        return (int) (temp ^ (temp >>> 32));
    }
}
