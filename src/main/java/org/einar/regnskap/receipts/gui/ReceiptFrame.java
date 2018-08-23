package org.einar.regnskap.receipts.gui;

import net.miginfocom.swing.MigLayout;
import org.einar.regnskap.receipts.LineItem;
import org.einar.regnskap.receipts.Receipt;
import org.einar.regnskap.receipts.Store;
import org.einar.regnskap.receipts.db.ItemsInMemoryStore;
import org.einar.regnskap.receipts.model.ItemStore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by
 * User: einahage
 * Date: 2/20/14
 * Time: 7:42 PM
 *
 *
 */
public class ReceiptFrame extends JFrame {

    private static final int cellHeight = 25;
    private final JPanel container = new JPanel();
    private final JScrollPane scrollPane;
    private String storeName;
    private String department;
    private String date;

    public ReceiptFrame(Receipt receipt) {

        final ItemStore items = new ItemsInMemoryStore();

        container.setLayout(new MigLayout("wrap 1","[center]", "[center]"));

        JPanel storePanel = new JPanel();
        Store store = receipt.getStore();
        storePanel.add(new JTextField(store != null ? store.getChainName() : "store"));
        storePanel.add(new JTextField(store != null ? store.getStoreName() : "department"));
        storePanel.add(new JTextField(receipt.getDateTime() != null ? receipt.getDateTime().toString("dd.MM.yyyy HH:mm") : "date"));
        container.add(storePanel, "span");

        final JPanel itemsPanel = new JPanel();

        for (LineItem i : receipt.getLineItems()) {
            itemsPanel.add(new LineItemPanel(i, itemsPanel, items));
            items.storeItem(i.getItem());
        }

        itemsPanel.setLayout(new GridLayout(0, 1));
        scrollPane = new JScrollPane(itemsPanel);
        scrollPane.setHorizontalScrollBar(null);
        scrollPane.setPreferredSize(new Dimension(650, 500));
        container.add(scrollPane);

        JButton newItemButton = new JButton("Add line item");
        container.add(newItemButton);

        newItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                itemsPanel.add(new LineItemPanel(itemsPanel, items));
                itemsPanel.revalidate();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        JScrollBar b = scrollPane.getVerticalScrollBar();
                        b.setValue(b.getMaximum());
                    }
                });
            }
        });

        container.add(new SubTotalPanel(receipt));
        add(container);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();

    }

}
