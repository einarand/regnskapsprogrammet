package org.einar.regnskap.gui;

import org.einar.regnskap.transactions.Transaction;
import org.einar.regnskap.receipts.Price;
import org.joda.time.DateTime;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class TransactionTableModel extends AbstractTableModel {

    private final static String[] columnNames = { "Dato", "Beskrivelse", "Beløp", "Konto"};
	private final List<Transaction> transactions;

	public TransactionTableModel(Collection<Transaction> transactions) {
		this.transactions = new ArrayList<Transaction>(transactions);
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return transactions.size();
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return DateTime.class;
            case 2:
                return double.class;
            case 4:
                return Price.class;
            default:
                return String.class;
        }
    }

    public Object getValueAt(int row, int col) {
		Transaction transaction = transactions.get(row);
		switch (col) {
		case 0:
			return transaction.getDate();
		case 1:
			return transaction.getDescription();
		case 2:
			return transaction.getAmount();
		case 3:
			return transaction.getAccount();
		case 4:
			return transaction.getReference();
		default:
			break;
		}
		return null;
	}

}
