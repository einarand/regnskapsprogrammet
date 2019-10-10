package org.eijovi.accounting;

import org.eijovi.receipts.Receipt;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Voucher {

    private final VoucherId id;
    private final Instant instant;
    private final VoucherType type;
    private final String description;
    private final List<Entry> entries;
    private final Optional<Receipt> receipt;

    private Voucher(VoucherId id, Instant instant, VoucherType type, String description, List<Entry> entries, Optional<Receipt> receipt) {
        this.id = id;
        this.instant = instant;
        this.type = type;
        this.description = description;
        this.entries = entries;
        this.receipt = receipt;
    }

    public static Voucher.Builder builder() {
        return new Voucher.Builder();
    }

    public VoucherId getId() {
        return id;
    }

    public Instant getInstant() {
        return instant;
    }

    public VoucherType getType() {
        return type;
    }

    public String description() {
        return description;
    }

    public List<Entry> entries() {
        return entries;
    }

    public Optional<Receipt> getReceipt() {
        return receipt;
    }

    public BigDecimal sumDebit() {
        return entries.stream().map(e -> e.amount)
                      .filter(a -> a.signum() > 0)
                      .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal sumCredit() {
        return entries.stream().map(e -> e.amount)
                      .filter(a -> a.signum() < 0)
                      .reduce(BigDecimal.ZERO, BigDecimal::add)
                      .abs();
    }

    public BigDecimal sum() {
        return entries.stream().map(e -> e.amount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static class Entry {

        private VoucherId voucherId;
        private AccountId account;
        private BigDecimal amount;

        public Entry(VoucherId voucherId, AccountId account, BigDecimal amount) {
            this.voucherId = voucherId;
            this.account = account;
            this.amount = amount;
        }

        public AccountId account() {
            return account;
        }

        public BigDecimal amount() {
            return amount;
        }

        public String toString() {
            return account + " " + amount;
        }

        public VoucherId voucherId() {
            return voucherId;
        }
    }

    public static class Builder {

        private final VoucherId id;
        private VoucherType type;
        private String description;
        private List<Entry> entries = new ArrayList<>();
        private Optional<Receipt> receipt = Optional.empty();

        private Builder() {
            id = VoucherId.randomId();
        }

        public Builder type(VoucherType type) {
            this.type = type;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder addEntry(Entry entry) {
            entries.add(entry);
            return this;
        }

        public Builder addEntry(AccountingAccount account, BigDecimal amount) {
            entries.add(new Entry(id, account.id(), amount));
            return this;
        }

        public Builder addEntry(AccountingAccount account, double amount) {
            return addEntry(account, new BigDecimal(amount));
        }

        public Builder debet(AccountingAccount account, double amount) {
            if (amount < 0) {
                throw new IllegalArgumentException("Amount must be bigger than zero");
            }
            return addEntry(account, amount);
        }

        public Builder credit(AccountingAccount account, double amount) {
            if (amount < 0) {
                throw new IllegalArgumentException("Amount must be bigger than zero");
            }
            return addEntry(account, -amount);
        }

        public Voucher build(Instant instant) {
            return new Voucher(
                    id,
                    instant == null ? Instant.now() : instant,
                    type == null ? VoucherType.MANUAL : type,
                    description,
                    entries,
                    receipt
            );
        }
    }
}
