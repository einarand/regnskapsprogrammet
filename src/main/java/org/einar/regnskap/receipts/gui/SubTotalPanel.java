package org.einar.regnskap.receipts.gui;

import org.einar.regnskap.receipts.Receipt;

import javax.swing.*;
import java.awt.*;

import static java.lang.Integer.parseInt;

/**
 * Created by
 * User: einahage
 * Date: 2/20/14
 * Time: 7:44 PM
 */
public class SubTotalPanel extends JPanel {

    private final Receipt receipt;
    private final Color GREEN = new Color(0, 150, 0);

    private static final int cellHeight = 25;

    public SubTotalPanel(Receipt receipt) {
        this.receipt = receipt;

        setLayout(new FlowLayout());

        JLabel nrOfItemsLabel = new JLabel();
        String text;
        if (receipt.getNrOfItems() != 0) {
            if (receipt.verifyNrOfItems()) {
                text = receipt.getCalculatedNrOfItems() + " items";
                nrOfItemsLabel.setForeground(GREEN);
            } else {
                text = receipt.getCalculatedNrOfItems() + " items (should be " + String.valueOf(receipt.getNrOfItems()) + ")";
                nrOfItemsLabel.setForeground(Color.RED);
            }

        } else {
            text = receipt.getCalculatedNrOfItems() + " items";
        }
        nrOfItemsLabel.setText(text);
        nrOfItemsLabel.setPreferredSize(new Dimension(290, cellHeight));
        add(nrOfItemsLabel);

        add(new JLabel("SubTotal:"));

        JLabel subTotalLabel = new JLabel();
        String subTotalText;
        if (receipt.getSubTotal() != null) {
            if (receipt.verifySubTotal()) {
                subTotalText = receipt.getCalculatedSubTotal() + " kr";
                subTotalLabel.setForeground(GREEN);
            } else {
                subTotalText = receipt.getCalculatedSubTotal() + " (should be: " + receipt.getSubTotal() + ")";
                subTotalLabel.setForeground(Color.RED);
            }
        } else {
            subTotalText = receipt.getCalculatedSubTotal().toString();
        }

        subTotalLabel.setText(subTotalText);
        subTotalLabel.setPreferredSize(new Dimension(190, cellHeight));
        add(subTotalLabel);

    }

}
