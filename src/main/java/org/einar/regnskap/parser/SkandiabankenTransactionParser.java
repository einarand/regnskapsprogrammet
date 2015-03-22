package org.einar.regnskap.parser;

import org.einar.regnskap.Transaction;
import org.einar.regnskap.receipts.Price;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;

public class SkandiabankenTransactionParser implements TransactionParser {

	public Transaction parse(String transactionString) throws ParseException {
		String[] elements = transactionString.split("\\t");
        DateTime date = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime(removeQuotes(elements[0]));

		// Skip bokf�rt dato, 1
		long reference = Long.parseLong(removeQuotes(elements[2]));
		// Skip bokf�rt dato, 3
		String description = removeQuotes(elements[4]);
		Price out = Price.ZERO;
		Price in = Price.ZERO;

		try {
			out = new Price(elements[5].replace(',', '.'));
		} catch (Exception e) {

		}

		if (elements.length >= 7) {
			try {
				in = new Price(elements[6].replace(',', '.'));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return new Transaction(date, description, in.subtract(out), reference, null);
	}

	private static String removeQuotes(String str) {
		return str.substring(1, str.length() - 1);
	}

}
