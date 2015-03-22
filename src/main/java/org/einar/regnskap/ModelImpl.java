package org.einar.regnskap;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.einar.regnskap.receipts.Price;
import org.joda.time.Interval;

import java.util.Collection;

public class ModelImpl implements Model {

    private final Collection<Category> categories;
    private Multimap<Category, Transaction> categorizedTransactions = HashMultimap.create();

    public ModelImpl(Collection<Category> categories) {
        this.categories = categories;
        categories.add(Category.UNKNOWN);
        categories.add(Category.INNSKUDD);
    }

    public void addTransactions(Collection<Transaction> transactions) {
        categorize(transactions);
        //categorizedTransactions.removeAll(Category.INNSKUDD);
    }

    private void categorize(Collection<Transaction> transactions) {
        for (Transaction t : transactions) {
            Category c = categorize(t);
            boolean increased = categorizedTransactions.put(c, t);
            if (!increased) {
                System.err.println("Transaction " + t + " already exists");
            }
        }
    }

    private Category categorize(Transaction t) {
        for (Category c : categories) {
            for (String searchString : c.getStrings()) {
                if (t.getAmount().lessThan(Price.ZERO) && t.getDescription().toLowerCase().contains(searchString)
                        || (t.getAccount() != null && t.getAccount().equals(searchString))) {
                    return c;
                } else if (t.getAmount().greaterThan(Price.ZERO)) {
                    return Category.INNSKUDD;
                }
            }
        }
        return Category.UNKNOWN;
    }

    public Collection<Transaction> getAllTransactions() {
        return categorizedTransactions.values();
    }

    public Collection<Transaction> getTransactions(Category category) {
        return categorizedTransactions.get(category);
    }

    public Price getTransactionsSum(Category category, Interval interval) {
        Collection<Transaction> transactions = getTransactions(category);
        Price sum = Price.ZERO;
        for (Transaction t : transactions) {
            if (interval.contains(t.getDate())) {
                sum = sum.add(t.getAmount());
            }
        }
        return sum;
    }

    public Collection<Category> getCategories() {
        return categories;
    }
}
