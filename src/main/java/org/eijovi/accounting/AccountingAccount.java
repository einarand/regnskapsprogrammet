package org.eijovi.accounting;

import java.math.BigDecimal;

public class AccountingAccount implements Account {

    private final AccountId id;
    private final String name;

    public AccountingAccount(String name) {
        this.name = name;
        id = AccountId.randomId();
    }

    public AccountId id() {
        return id;
    }

    public BigDecimal balance() {
        return null;
    }

}
