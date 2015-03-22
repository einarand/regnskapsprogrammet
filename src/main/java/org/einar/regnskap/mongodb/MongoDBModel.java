package org.einar.regnskap.mongodb;

import com.mongodb.*;
import org.einar.regnskap.Category;
import org.einar.regnskap.Model;
import org.einar.regnskap.Transaction;
import org.einar.regnskap.TransactionSearch;
import org.einar.regnskap.parser.CategoryParser;
import org.einar.regnskap.receipts.Price;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.UnknownHostException;
import java.util.*;


public class MongoDBModel implements Model {

    private String hostName;
    private DB db;
    private Collection<Transaction> transactions = new ArrayList<Transaction>();
    private Map<Category, Collection<Transaction>> categorizedTransactions = new HashMap<Category, Collection<Transaction>>();
    private Collection<Category> categories = new ArrayList<Category>();

    public MongoDBModel(String hostName) {
        this.hostName = hostName;
        try {
            initialize();
        } catch (UnknownHostException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void initialize() throws UnknownHostException {
        Mongo mongo = new Mongo(hostName);
        db = mongo.getDB("regnskap");
        this.categories = loacCategoriesFromFile("categories.properties");
        this.transactions = loadTransactionsFromDB();

        extractCategorizedTransactions(new ArrayList<Transaction>(transactions));

    }

    private void extractCategorizedTransactions(Collection<Transaction> transactions) {
        Category innskuddCategory = new Category("Innskudd");
        categories.add(innskuddCategory);
        Collection<Transaction> innskuddTransactions = TransactionSearch.findTransactionsWithPositiveAmount(transactions);
        categorizedTransactions.put(innskuddCategory, innskuddTransactions);
        transactions.removeAll(innskuddTransactions);

        for (Category c : categories) {
            Collection<Transaction> transactionsInCategory = TransactionSearch.findTransactionsByCategory(transactions, c);
            transactions.removeAll(transactionsInCategory);
            if (!categorizedTransactions.containsKey(c)) {
                categorizedTransactions.put(c, transactionsInCategory);
            }
        }

        Category annetCategory = new Category("Annet");
        categories.add(annetCategory);
        categorizedTransactions.put(annetCategory, transactions);
    }

    public void addTransactions(Collection<Transaction> transactions) {

    }

    public Collection<Transaction> getAllTransactions() {
        return transactions;
    }

    public Collection<Transaction> getTransactions(Category category) {
        return categorizedTransactions.get(category);
    }

    public Price getTransactionsSum(Category category, Interval interval) {
        return Price.ZERO;
    }

    public Collection<Transaction> loadTransactionsFromDB() {
        List<Transaction> transactions = new ArrayList<Transaction>();
        DBCollection collection = db.getCollection("transactions");
        DBCursor cursor = collection.find().sort(new BasicDBObject("date", -1));
        while (cursor.hasNext()) {
            DBObject o = cursor.next();
            transactions.add(new Transaction((DateTime) o.get("date"), (String) o.get("description"),
                    new Price((String) o.get("amount")), (Long) o.get("reference"), (String) o.get("account")));
        }
        return transactions;
    }

    public Collection<Category> loadCategoriesFromDB() {
        List<Category> categories = new ArrayList<Category>();
        DBCollection collection = db.getCollection("categories");
        DBCursor cursor = collection.find();
        while (cursor.hasNext()) {
            DBObject o = cursor.next();
            categories.add(new Category((String) o.get("name"),
                    (Collection<String>) o.get("strings")));
        }
        return categories;
    }

    private Collection<Category> loacCategoriesFromFile(String fileName) {
        Collection<Category> categories = new ArrayList<Category>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = null;
            while ((line = br.readLine()) != null) {
                categories.add(CategoryParser.parse(new String(line.getBytes(),"UTF-8")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    public Collection<Category> getCategories() {
        return categories;
    }
}
