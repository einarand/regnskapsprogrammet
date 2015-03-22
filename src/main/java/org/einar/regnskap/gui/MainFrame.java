package org.einar.regnskap.gui;

import org.einar.regnskap.Category;
import org.einar.regnskap.Model;
import org.einar.regnskap.Transaction;
import org.einar.regnskap.receipts.Price;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame {

    public MainFrame(Model model) {

        int year = 2014;

        JTabbedPane tabbedPane = new JTabbedPane();
        setContentPane(tabbedPane);

        JPanel overviewPanel = new JPanel();
        tabbedPane.add("Overview", overviewPanel);
        Collection<Category> categories = model.getCategories();

        overviewPanel.setLayout(new GridLayout(0, categories.size() + 3));
        overviewPanel.add(new JLabel("Måned"));

        Map<Category, ArrayList<Price>> averages = new HashMap<Category, ArrayList<Price>>();
        for (Category c : categories) {
            overviewPanel.add(new JLabel(c.getName()));

            Collection<Transaction> transactions = model.getTransactions(c);
            JPanel panel = createTransactionTablePanel(transactions);
            tabbedPane.addTab(c.getName(), panel);

            averages.put(c, new ArrayList<Price>());
        }
        overviewPanel.add(new JLabel("Sum"));
        overviewPanel.add(new JLabel("Result"));


        DateTime date = DateTimeFormat.forPattern("dd.MM.yyyy").parseDateTime("01.01." + year);
        for (int i = 1; i <= 12; i++) {
            overviewPanel.add(new JLabel(year + "-" + i));
            Price sum = Price.ZERO;
            Price result = Price.ZERO;
            for (Category c : categories) {

                Interval interval = new Interval(date, Months.ONE);
                Price amount = model.getTransactionsSum(c, interval);

                result = result.add(amount);
                if (amount.lessThan(Price.ZERO)) {
                    sum = sum.add(amount);
                }

                ArrayList<Price> values = averages.get(c);
                values.add(amount);

                overviewPanel.add(new JLabel("" + amount));
            }
            date = date.plus(Months.ONE);
            overviewPanel.add(new JLabel("" + sum));
            overviewPanel.add(new JLabel("" + result));
        }

        overviewPanel.add(new JLabel("Avg"));
        Price sumAvg = Price.ZERO;
        for (Category c : model.getCategories()) {
            ArrayList<Price> values = averages.get(c);
            Price sum = Price.ZERO;
            for (Price value : values) {
                sum = sum.add(value);
            }
            if (values.size() > 0) {
                Price avg = sum.divide(values.size());
                sumAvg = sumAvg.add(avg);
                overviewPanel.add(new JLabel("" + avg));
            } else {
                overviewPanel.add(new JLabel("NA"));
            }
        }
        overviewPanel.add(new JLabel("" + sumAvg));

        JPanel allPanel = createTransactionTablePanel(model.getAllTransactions());
        tabbedPane.addTab("Alle", allPanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    private JPanel createTransactionTablePanel(Collection<Transaction> transactions) {
        TableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {

            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus,
                    int row, int column) {
                if( value instanceof DateTime) {
                    value = ((DateTime)value).toString("dd.MM.yyyy");
                }
                return super.getTableCellRendererComponent(table, value, isSelected,
                        hasFocus, row, column);
            }
        };

        TransactionTableModel model = new TransactionTableModel(transactions);
        final JTable allTable = new JTable(model);
        allTable.getColumnModel().getColumn(0).setCellRenderer(tableCellRenderer);
        allTable.setAutoCreateRowSorter(true);

        allTable.setPreferredScrollableViewportSize(new Dimension(1000, 500));
        allTable.setFillsViewportHeight(true);
        JPanel allPanel = new JPanel();

        allPanel.setLayout(new GridLayout(0, 1));
        allPanel.add(new JScrollPane(allTable));

        return allPanel;
    }



}
