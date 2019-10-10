package org.eijovi;

import org.eijovi.accounting.Account;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Transaction {

    private UUID id;
    private Instant instant;
    private Account debetAccount;
    private Account creditAccount;
    private BigDecimal amount;

}
