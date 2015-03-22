package org.einar.regnskap.gui;

import org.einar.regnskap.Transaction;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class TransactionTableModelTest {

    @Test
    public void testTable() throws Exception {

        DateTime date = DateTimeFormat.forPattern("dd.MM.yyyy").parseDateTime("01.01.2012");
        Transaction t = new Transaction(date, "Test", -500.0, 1234, null);
        Transaction t2 = new Transaction(date, "Test2", 500.0, 1235, "2030.02.12345");
        Collection<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(t);
        transactions.add(t2);

        JFrame f = new JFrame();
        f.setContentPane(createTransactionTablePanel(transactions));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.pack();
        Thread.sleep(3000);
    }

    private JPanel createTransactionTablePanel(Collection<Transaction> transactions) {
        final JTable allTable = new JTable(new TransactionTableModel(transactions));
        allTable.setPreferredScrollableViewportSize(new Dimension(1000, 500));
        allTable.setFillsViewportHeight(true);
        JPanel allPanel = new JPanel();
        allPanel.setLayout(new GridLayout(1, 0));
        allPanel.add(new JScrollPane(allTable));
        return allPanel;
    }

}
