package org.einar.regnskap.parser;

import org.einar.regnskap.transactions.Transaction;
import org.einar.regnskap.receipts.Price;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;

public class SSFTransactionParser implements TransactionParser {

	public Transaction parse(String transactionString) throws ParseException {
        String[] elements = transactionString.split("\\t");
        DateTime date = DateTimeFormat.forPattern("dd.MM.yyyy").parseDateTime(elements[0]);

		//Skip rentedato [1]
		//Skip tekstkode [2]
		String description = elements[3];
		Price amount = new Price(elements[4].replace(',', '.'));
		long reference = Long.parseLong(elements[5]);
		String account = null;
		if (elements.length > 6) {
			account = elements[6];
		}
		return new Transaction(date, description, amount, reference, account);
	}
}
