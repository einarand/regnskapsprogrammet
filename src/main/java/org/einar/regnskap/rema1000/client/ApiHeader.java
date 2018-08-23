package org.einar.regnskap.rema1000.client;

public class ApiHeader {
    private String key;
    private String value;

    public ApiHeader(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
