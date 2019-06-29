package org.eijovi;

import java.math.BigDecimal;

public class Account {

    private final AccountId accountId;
    private final String name;

    public Account(String name) {
        this.name = name;
        accountId = AccountId.randomId();
    }

    public BigDecimal saldo() {
        return null;
    }

    public AccountId id() {
        return accountId;
    }
}
