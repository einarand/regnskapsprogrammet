package org.einar.regnskap;

import org.einar.regnskap.receipts.Price;
import org.einar.regnskap.transactions.Transaction;
import org.joda.time.Interval;

import java.util.Collection;

public interface Model {

    void addTransactions(Collection<Transaction> transactions);

    Collection<Transaction> getAllTransactions();

    Collection<Transaction> getTransactions(Category category);

    Price getTransactionsSum(Category category, Interval interval);

    Collection<Category> getCategories();

}
