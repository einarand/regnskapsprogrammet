package org.eijovi.bank;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public interface BankTransaction {

    UUID id();

    Instant accountingDate();

    Instant interestDate();

    BigDecimal amount();

    String description();

    TransactionType transactionType();

}
