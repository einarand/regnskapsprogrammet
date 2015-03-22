package org.einar.regnskap.receipts.gui;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class UpperCaseTextField extends JTextField {

    private final UpperCaseTextField _this = this;

    public UpperCaseTextField() {

    }

    protected Document createDefaultModel() {
        return new LimitedDocument();
    }

    static class LimitedDocument extends PlainDocument {

        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null){
                return;
            }
            // Set the text of this document to uppercase
            super.insertString(offs, str.toUpperCase(), a);

        }
    }

}  