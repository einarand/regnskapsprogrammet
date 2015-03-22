package org.einar.regnskap.dbupdater;

import org.einar.regnskap.Transaction;
import org.einar.regnskap.parser.TransactionParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class TransactionReader {

    public static Collection<Transaction> read(String filename, TransactionParser parser) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
            String line;
            Collection<Transaction> transactions = new ArrayList<Transaction>();
            while ((line = br.readLine()) != null) {
                try {
                    Transaction transaction = parser.parse(line);
                    transactions.add(transaction);
                } catch (Exception e) {
                    System.err.println("Error parsing line: " + line);
                }
            }
            return transactions;
        } catch (IOException e) {
            throw new RuntimeException("Could not load file, " + filename, e);
        }

    }

}
