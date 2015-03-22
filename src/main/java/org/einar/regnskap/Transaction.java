package org.einar.regnskap;

import org.einar.regnskap.receipts.Price;
import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class Transaction implements Comparable {

	private final DateTime date;
	private final String description;
	private final Price amount;
	private final Long reference;
	private final String account;
	
	public Transaction(DateTime date, String description, Price amount,
			long reference, String account) {
		if(amount == null) throw new IllegalArgumentException("Amount cannot be null");
        this.date = date;
		this.description = description;
		this.amount = amount;
		this.reference = reference;
		this.account = account;
	}
	
	public DateTime getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public Price getAmount() {
		return amount;
	}

	public long getReference() {
		return reference;
	}

	public String getAccount() {
		return account;
	}
	
	@Override
	public String toString() {
		return date.toString("dd.MM.yy") + ",\"" + description + "\"," + amount;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (!amount.equals(that.amount)) return false;
        if (account != null ? !account.equals(that.account) : that.account != null) return false;
        if (!date.equals(that.date)) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (reference != null ? !reference.equals(that.reference) : that.reference != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    public int compareTo(Object o) {
        return 0;
    }
}
