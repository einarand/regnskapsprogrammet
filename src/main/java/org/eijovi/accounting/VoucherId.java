package org.eijovi.accounting;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class VoucherId {

    private String value;

    private VoucherId(String value) {
        this.value = checkNotNull(value);
    }

    public static VoucherId randomId() {
        return new VoucherId(UUID.randomUUID().toString());
    }

    public VoucherId fromString(String string) {
        return new VoucherId(string);
    }

    public String toString() {
        return value.toString();
    }


}
