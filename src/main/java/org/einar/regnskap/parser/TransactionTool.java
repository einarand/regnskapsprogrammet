package org.einar.regnskap.parser;

import org.einar.regnskap.Transaction;
import org.einar.regnskap.receipts.Price;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class TransactionTool {

    public static Collection<Transaction> filterOnPeriod(DateTime fromDate, DateTime toDate, Collection<Transaction> transactions) {
        Collection<Transaction> filtered = new ArrayList<Transaction>();
        for (Transaction t : transactions) {
            if (isWithinPeriod(fromDate, toDate, t)) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    public static boolean isWithinPeriod(DateTime fromDate, DateTime toDate, Transaction t) {
        return t.getDate().compareTo(fromDate) >= 0 && t.getDate().compareTo(toDate) < 0;
    }

    public static Price getAmount(Collection<Transaction> transactions) {
        Price sum = Price.ZERO;
        for (Transaction t : transactions) {
            sum = sum.add(t.getAmount());
        }
        return sum;
    }

}
