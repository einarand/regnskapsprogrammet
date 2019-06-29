package org.eijovi;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Voucher {

    private final VoucherId id;
    private final Instant instant;
    private final Type type;
    private final String description;
    private final List<Entry> entries;
    private final Optional<Receipt> receipt;

    public Voucher(VoucherId id, Instant instant, Type type, String description, List<Entry> entries, Optional<Receipt> receipt) {
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

    public Type getType() {
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

    public static class Type {

        private String name;

        public Type(String name) {
            this.name = name;
        }
    }

    public static class Builder {

        private final VoucherId id;
        private Type type;
        private String description;
        private List<Entry> entries = new ArrayList<>();
        private Optional<Receipt> receipt = Optional.empty();

        private Builder() {
            id = VoucherId.randomId();
        }

        public Builder type(Type type) {
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

        public Builder addEntry(Account account, BigDecimal amount) {
            entries.add(new Entry(id, account.id(), amount));
            return this;
        }

        public Builder addEntry(Account account, double amount) {
            return addEntry(account, new BigDecimal(amount));
        }

        public Builder debet(Account account, double amount) {
            if (amount < 0) {
                throw new IllegalArgumentException("Amount cannot be less than zero");
            }
            return addEntry(account, amount);
        }

        public Builder credit(Account account, double amount) {
            if (amount < 0) {
                throw new IllegalArgumentException("Amount cannot be less than zero");
            }
            return addEntry(account, -amount);
        }

        public Voucher build(Instant instant) {
            return new Voucher(
                    id,
                    instant == null ? Instant.now() : instant,
                    type == null ? new Type("Manual") : type,
                    description,
                    entries,
                    receipt
            );
        }
    }
}
