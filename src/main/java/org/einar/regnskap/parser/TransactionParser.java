package org.einar.regnskap.parser;

import org.einar.regnskap.Transaction;

import java.text.ParseException;

public interface TransactionParser {

	Transaction parse(String string) throws ParseException;
	
}
