package org.einar.regnskap.receipts;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.DecimalFormat;

/**
 *
 * Should be on the format \\d+.\\d{2}
 */
public class Price {

    private final BigDecimal value;
    public static final Price ZERO = new Price("0");

    public Price(String string) {
        this(new BigDecimal(string));
    }

    public Price(BigDecimal price) {
        this.value = price;
    }

    public static Price parsePrice(String string) {
        try {
            String str = string.replace(',', '.');
            return new Price(new BigDecimal(str));
        } catch (NumberFormatException e) {
            throw new PriceFormatException("Price format error for \"" + string + "\"", e);
        }
    }

    private static final String regExp = "[, ^'´’‘]";

    public static Price parsePriceFromReceiptData(String string) { //TODO move to seperate parser class
        try {
            String str = string.replaceAll(regExp, ".");
            if (str.contains(".")) {
                return new Price(new BigDecimal(str));
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
        return price != null ? new Price(this.value.add(price.value)) : new Price(value);
    }

    public Price divide(int value) {
        return new Price(this.value.divide(new BigDecimal(value), new MathContext(1000)));
    }

    public Price multiply(int value) {
        return new Price(this.value.multiply(new BigDecimal(value)));
    }

    @Override
    public String toString() {
        return new DecimalFormat("0.00").format(value);
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof Price && value.compareTo(((Price) o).value) == 0;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public boolean lessThan(Price i) {
        return value.equals(value.min(i.value));
    }

    public boolean greaterThan(Price i) {
        return value.equals(value.max(i.value));
    }

    public Price subtract(Price other) {
        return new Price(value.subtract(other.value));
    }
}
