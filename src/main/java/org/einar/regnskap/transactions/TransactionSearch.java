package org.einar.regnskap.transactions;

import org.einar.regnskap.Category;
import org.einar.regnskap.receipts.Price;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class TransactionSearch {

	public static List<Transaction> containsDescription(Collection<Transaction> transactions, Collection<String> strings) {
		List<Transaction> subset = new ArrayList<Transaction>();
		for (Transaction transaction : transactions) {
			for (String searchString : strings) {
				if (transaction.getDescription().toLowerCase().contains(searchString.toLowerCase())) {
					subset.add(transaction);
				}
			}
		}
		return subset;
	}
	
	public static List<Transaction> containsAccount(Collection<Transaction> transactions, String accountNumber) {
		List<Transaction> subset = new ArrayList<Transaction>();
		for (Transaction transaction : transactions) {
			if (transaction.getDescription().toLowerCase().contains(accountNumber.toLowerCase())) {
				subset.add(transaction);
			}
		}
		return subset;
	}

    public static Collection<Transaction> findTransactionsWithPositiveAmount(Collection<Transaction> transactions) {
        Collection<Transaction> innskuddTransactions = new ArrayList<Transaction>();
        for (Transaction t : transactions) {
            if (t.getAmount().greaterThan(Price.ZERO)) {
                innskuddTransactions.add(t);
            }
        }
        return innskuddTransactions;
    }

	public static List<Transaction> findTransactionsByCategory(Collection<Transaction> transactions, Category category) {
		List<Transaction> subset = new ArrayList<Transaction>();
		for (Transaction transaction : transactions) {
			for (String searchString : category.getStrings()) {
				if (transaction.getAmount().lessThan(Price.ZERO) && transaction.getDescription().toLowerCase().contains(searchString)
						|| (transaction.getAccount() != null && transaction.getAccount().equals(searchString))) {
					subset.add(transaction);
					break;
				}				
			}
		}
		return subset;
	}

}