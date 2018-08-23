package org.einar.regnskap.rema1000.client;

public class ClientException extends RuntimeException {

    private final int statusCode;

    public ClientException(int statusCode, String message, Throwable causedBy) {
        super(message, causedBy);
        this.statusCode = statusCode;
    }

    public ClientException(int statusCode, String message) {
        this(statusCode, message, null);
    }

    public ClientException(String message, Throwable causedBy) {
        this(0, message, causedBy);
    }

    public int statusCode() {
        return statusCode;
    }
}
