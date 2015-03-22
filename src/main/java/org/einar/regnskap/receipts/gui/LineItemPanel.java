package org.einar.regnskap.receipts.gui;

import org.einar.regnskap.receipts.Item;
import org.einar.regnskap.receipts.LineItem;
import org.einar.regnskap.receipts.Price;
import org.einar.regnskap.receipts.PriceFormatException;
import org.einar.regnskap.receipts.model.Items;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static java.lang.Integer.*;

/**
 * Created by
 * User: einahage
 * Date: 2/20/14
 * Time: 7:44 PM
 */
public class LineItemPanel extends JPanel {

    private final Container parent;
    private final JTextField qtyTextField;
    private final UpperCaseTextField descTextField;
    private final JTextField priceTextField;
    private final JLabel totalPriceLabel;
    private final Items items;

    private int quantity;
    private Price itemPrice;

    private static final int cellHeight = 25;

    public LineItemPanel(Container parent, final Items items) {
        this.parent = parent;
        this.items = items;
        setLayout(new FlowLayout());

        qtyTextField = new JTextField("1");
        qtyTextField.setPreferredSize(new Dimension(30, cellHeight));
        qtyTextField.addKeyListener(new QuantityListener());
        verifyQuantity();
        add(qtyTextField);

        descTextField = new UpperCaseTextField();
        descTextField.setPreferredSize(new Dimension(400, cellHeight));
        add(descTextField);
        descTextField.addKeyListener(new KeyListener() {
            public void keyTyped(final KeyEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        final int caretPos = descTextField.getCaretPosition();

                        String textToCaretPos = descTextField.getText().substring(0, caretPos);
                        Item i = items.getBestSuggestion(textToCaretPos);
                        if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE || textToCaretPos.length() == 0 || i == null) {
                            descTextField.setText(textToCaretPos);
                            priceTextField.setText("");
                        } else {
                            descTextField.setText(i.getDescription());
                            priceTextField.setText(i.getPrice().toString());
                        }

                        descTextField.setCaretPosition(caretPos);
                        verifyPrice();
                        calculateTotals();
                    }
                });
            }

            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }
        });
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                descTextField.requestFocus();
            }
        });

        priceTextField = new JTextField();
        priceTextField.setPreferredSize(new Dimension(70, cellHeight));
        priceTextField.addKeyListener(new PriceListener());
        priceTextField.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {}

            public void focusLost(FocusEvent e) {
                if(verifyPrice()) {
                    saveAndUpdatePrice();
                }
            }
        });

        add(priceTextField);

        totalPriceLabel = new JLabel();
        totalPriceLabel.setPreferredSize(new Dimension(70, cellHeight));
        add(totalPriceLabel);

        JButton removeButton = new JButton("X");
        removeButton.setPreferredSize(new Dimension(cellHeight, cellHeight));
        removeButton.addActionListener(new RemoveButtonListener(parent, this));
        add(removeButton);

        JLabel l = new JLabel();
        l.setPreferredSize(new Dimension(10, cellHeight));
        add(l);
    }

    public LineItemPanel(LineItem lineItem, Container parent, Items items) {
        this(parent, items);
        qtyTextField.setText(String.valueOf(lineItem.getQuantity()));
        verifyQuantity();
        descTextField.setText(lineItem.getDescription());
        priceTextField.setText(lineItem.getItemPrice() != null ? lineItem.getItemPrice().toString() : "");
        verifyPrice();
        calculateTotals();
    }

    private class RemoveButtonListener implements ActionListener {

        final Container parent;
        final LineItemPanel lineItemPanel;

        public RemoveButtonListener(Container parent, LineItemPanel lineItemPanel) {
            this.parent = parent;
            this.lineItemPanel = lineItemPanel;
        }

        public void actionPerformed(ActionEvent e) {
            parent.remove(lineItemPanel);
            parent.revalidate();
        }
    }

    private boolean verifyQuantity() {
        try {
            quantity = parseInt(qtyTextField.getText());
            qtyTextField.setBackground(Color.GREEN);
            return true;
        } catch (NumberFormatException e) {
            qtyTextField.setBackground(Color.RED);
            return false;
        }
    }

    private void calculateTotals() {
        if (itemPrice != null) {
            totalPriceLabel.setText(itemPrice.multiply(quantity).toString());
        } else {
            totalPriceLabel.setText(Price.ZERO.toString());
        }
    }

    private boolean verifyPrice() {
        try {
            itemPrice = Price.parsePrice(priceTextField.getText());
            priceTextField.setBackground(Color.GREEN);
            return true;
        } catch (PriceFormatException e) {
            itemPrice = null;
            priceTextField.setBackground(Color.RED);
            return false;
        }
    }

    class QuantityListener implements KeyListener {

        public void keyTyped(KeyEvent e) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    if (verifyQuantity()) {
                        calculateTotals();
                    };
                }
            });
        }

        public void keyPressed(KeyEvent ignore) {}

        public void keyReleased(KeyEvent ignore) {}
    }

    class PriceListener implements KeyListener {

         public void keyTyped(KeyEvent e) {}

         public void keyPressed(KeyEvent ignore) {}

         public void keyReleased(final KeyEvent e) {
             SwingUtilities.invokeLater(new Runnable() {
                 public void run() {
                     if(verifyPrice()) {
                         calculateTotals();
                         if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                             saveAndUpdatePrice();
                         }
                     }

                 }
             });
         }
     }

    private void saveAndUpdatePrice() {
        String priceString = priceTextField.getText();
        priceTextField.setText(Price.parsePrice(priceString).toString());
    }

}
