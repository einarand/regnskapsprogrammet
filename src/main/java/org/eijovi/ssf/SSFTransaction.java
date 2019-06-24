package org.eijovi.ssf;

import java.math.BigDecimal;
import java.time.Instant;

public class SSFTransaction {

    private final int sequenceNumber;               // 572416748112214544
    private final int fromAccountNumber;            // "37051798423"
    private final int toAccountNumber;              // null
    private final Instant dateBooked;               // "2019-05-27T10:00:00.000+0000"
    private final String description;               // "25.05 GYNGEHESTEN AS  NAUSTDALSVEG FØRDE"
    private final BigDecimal amountlocalCurrency;   // -2198
    private final BigDecimal amountForeigncurrency; // null
    private final Double exchangeRate;              // null
    private final String archiveReference;          // "17017075013"
    private final String transactionType;           // "PURCHASE"
    private final String currency;                  // null
    private final boolean posted;                   // true
    private final String sign;                      // "DEBIT"
    private final boolean archived;                 // true
    private final String evryTransactionType;       // "RK"
    private final Instant dateValued;               // "2019-05-26T22:00:00.000+0000"


    public SSFTransaction(int sequenceNumber,
                          int fromAccountNumber,
                          int toAccountNumber,
                          Instant dateBooked,
                          String description,
                          BigDecimal amountlocalCurrency,
                          BigDecimal amountForeigncurrency,
                          Double exchangeRate,
                          String archiveReference,
                          String transactionType,
                          String currency,
                          boolean posted,
                          String sign,
                          boolean archived,
                          String evryTransactionType,
                          Instant dateValued) {
        this.sequenceNumber = sequenceNumber;
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.dateBooked = dateBooked;
        this.description = description;
        this.amountlocalCurrency = amountlocalCurrency;
        this.amountForeigncurrency = amountForeigncurrency;
        this.exchangeRate = exchangeRate;
        this.archiveReference = archiveReference;
        this.transactionType = transactionType;
        this.currency = currency;
        this.posted = posted;
        this.sign = sign;
        this.archived = archived;
        this.evryTransactionType = evryTransactionType;
        this.dateValued = dateValued;
    }
}
