package org.eijovi.accounting;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class AccountId {

    private String value;

    private AccountId(String value) {
        this.value = checkNotNull(value);
    }

    public static AccountId randomId() {
        return new AccountId(UUID.randomUUID().toString());
    }

    public AccountId fromString(String string) {
        return new AccountId(string);
    }

    public String toString() {
        return value;
    }

}
