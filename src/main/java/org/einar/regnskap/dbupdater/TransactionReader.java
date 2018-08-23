package org.einar.regnskap.dbupdater;

import org.einar.regnskap.transactions.Transaction;
import org.einar.regnskap.parser.TransactionParser;
import org.einar.regnskap.receipts.Price;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class TransactionReader {

    public static Collection<Transaction> parseFilesInFolder(String folderName, TransactionParser parser) {
        File dir = new File(folderName);
        File[] directoryListing = dir.listFiles();
        Collection<Transaction> transactions = new ArrayList<Transaction>();
        if (directoryListing == null) { throw new RuntimeException("Folder " + folderName + " does not exsists"); }
        for (File f : directoryListing) {
            transactions.addAll(parseFile(f, parser));
        }
        return transactions;
    }

    public static Collection<Transaction> parseFile(File file, TransactionParser parser) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line;
            Collection<Transaction> transactions = new ArrayList<Transaction>();
            while ((line = br.readLine()) != null) {
                try {
                    Transaction transaction = parser.parse(line);
                    if (!(transaction.getAmount().greaterThan(new Price(BigDecimal.valueOf(100000))) || //Hack to filter out high amounts
                            transaction.getAmount().lessThan(new Price(BigDecimal.valueOf(-100000))))) {
                        transactions.add(transaction);
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing line: " + line);
                }
            }
            return transactions;
        } catch (IOException e) {
            throw new RuntimeException("Could not load file, " + file.getAbsolutePath(), e);
        }

    }

}
