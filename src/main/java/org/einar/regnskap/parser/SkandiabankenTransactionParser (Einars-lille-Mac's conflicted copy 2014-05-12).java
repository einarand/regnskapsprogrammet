package org.einar.regnskap.parser;

import org.einar.regnskap.Transaction;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SkandiabankenTransactionParser implements TransactionParser {

	public Transaction parse(String transactionString) throws ParseException {
		String[] elements = transactionString.split("\\t");
        DateTime date = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime(removeQuotes(elements[0]));

		// Skip bokf�rt dato, 1
		long reference = Long.parseLong(removeQuotes(elements[2]));
		// Skip bokf�rt dato, 3
		String description = removeQuotes(elements[4]);
		double out = 0.0;
		double in = 0.0;

		try {
			out = Double.parseDouble(elements[5].replace(',', '.'));
		} catch (Exception e) {

		}

		if (elements.length >= 7) {
			try {
				in = Double.parseDouble(elements[6].replace(',', '.'));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return new Transaction(date, description, in - out, reference, null);
	}

	private static String removeQuotes(String str) {
		return str.substring(1, str.length() - 1);
	}

}
