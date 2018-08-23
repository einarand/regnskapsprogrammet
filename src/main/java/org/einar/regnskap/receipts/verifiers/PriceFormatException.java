package org.einar.regnskap.receipts.verifiers;

/**
 * Created by
 * User: einahage
 * Date: 2/20/14
 * Time: 12:13 PM
 */
public class PriceFormatException extends RuntimeException {

    public PriceFormatException() {
        super();
    }

    public PriceFormatException(String s) {
        super(s);
    }

    public PriceFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public PriceFormatException(Throwable cause) {
        super(cause);
    }
}
