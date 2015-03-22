package org.einar.regnskap;

import org.einar.regnskap.dbupdater.CategoryReader;
import org.einar.regnskap.dbupdater.TransactionReader;
import org.einar.regnskap.gui.MainFrame;
import org.einar.regnskap.mongodb.MongoDBModel;
import org.einar.regnskap.parser.SSFTransactionParser;
import org.einar.regnskap.parser.TransactionParser;

import java.io.IOException;
import java.util.Collection;

public class App {

    public App() throws IOException {

        Model model = initialize();
        new MainFrame(model);
    }

    private Model initialize() {
        TransactionParser transactionParser = new SSFTransactionParser();
        Collection<Category> categories = CategoryReader.read("categories.properties");
        Model model = new ModelImpl(categories);

        //model.addTransactions(TransactionReader.read("transaksjoner 2013-1.csv", transactionParser));
        //model.addTransactions(TransactionReader.read("transaksjoner 2013-2.csv", transactionParser));
        //model.addTransactions(TransactionReader.read("transaksjoner 2013-3.csv", transactionParser));
        //model.addTransactions(TransactionReader.read("transaksjoner 2013-4.csv", transactionParser));
        model.addTransactions(TransactionReader.read("transactions/transaksjoner 2014-01-01_2014-04-08.csv", transactionParser));
        model.addTransactions(TransactionReader.read("transactions/transaksjoner 2014-04-08_2014-05-11.csv", transactionParser));

        return model;
    }

    private Model initializeDB() {
        return new MongoDBModel("192.168.10.103");
    }

    public static void main(String[] args) {
        try {
            new App();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
