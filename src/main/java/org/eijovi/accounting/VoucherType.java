package org.eijovi.accounting;

import static com.google.common.base.Preconditions.checkNotNull;

public class VoucherType {

    public static VoucherType BANK_TRANSACTION_DEPOSIT = new VoucherType("bankTransactionDeposit");
    public static VoucherType BANK_TRANSACTION_WITHDRAWAL = new VoucherType("bankTransactionWithdrawal");
    public static VoucherType CUSTOMER_INVOICE_DEBIT = new VoucherType("customerInvoiceDebit");
    public static VoucherType MANUAL = new VoucherType("manual");
    public static VoucherType PURCHASE_RECEIPT = new VoucherType("purchaseReceipt");
    public static VoucherType SUPPLIER_INVOICE_DEBIT = new VoucherType("supplierInvoiceDebit");

    private String name;

    private VoucherType(String name) {
        this.name = checkNotNull(name);
    }

    @Override
    public String toString() {
        return name;
    }

}
