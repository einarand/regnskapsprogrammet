package org.einar.regnskap.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.einar.regnskap.Category;
import org.einar.regnskap.transactions.Transaction;

import java.util.Collection;

public class DBUpdater {

    public void insertCategories(Collection<Category> categories, DB db) {
        DBCollection collection = db.getCollection("categories");
        for (Category c : categories) {
            BasicDBObject o = new BasicDBObject();
            o.put("name", c.getName());
            o.put("strings", c.getStrings());
            collection.save(o);
        }
    }

    public void insertTransactions(Collection<Transaction> transactions, DB db) {
        DBCollection collection = db.getCollection("transactions");

        for (Transaction t : transactions) {
            BasicDBObject o = new BasicDBObject();

            System.out.println(t.getReference());
            //o.put("_id", "" + t.getDate() + t.getAmount());
            o.put("date", t.getDate());
            o.put("description", t.getDescription());
            o.put("amount",t.getAmount());
            o.put("reference", t.getReference());
            o.put("account", t.getAccount());
            collection.save(o);
        }
    }

}
