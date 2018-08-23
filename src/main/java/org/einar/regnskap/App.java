package org.einar.regnskap;

import org.einar.regnskap.dbupdater.CategoryReader;
import org.einar.regnskap.dbupdater.TransactionReader;
import org.einar.regnskap.gui.MainFrame;
import org.einar.regnskap.mongodb.MongoDBModel;
import org.einar.regnskap.parser.SSFTransactionParser;
import org.einar.regnskap.parser.TransactionParser;

import java.awt.Dimension;
import java.io.IOException;
import java.util.Collection;

public class App {

    public App() throws IOException {

        Model model = initialize();
        new MainFrame(model).setSize(new Dimension(1400,800));
    }

    private Model initialize() {
        TransactionParser transactionParser = new SSFTransactionParser();
        Collection<Category> categories = CategoryReader.read("categories.properties");
        Model model = new ModelImpl(categories);
        model.addTransactions(TransactionReader.parseFilesInFolder("transactions/felles/", transactionParser));
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
