package org.eijovi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface VouchersStore {

    void store(Voucher voucher);

    List<Voucher> list(AccountId account);

    List<Voucher> listAll();

    Optional<Voucher> get(VoucherId voucherId);

    class InMemoryVouchersStore implements VouchersStore {

        private final List<Voucher> vouchers = new ArrayList<>();

        @Override
        public void store(Voucher voucher) {
            vouchers.add(voucher);
        }

        @Override
        public List<Voucher> list(AccountId account) {
            return vouchers.stream()
                           .filter(v -> v.entries().stream().anyMatch(e -> e.account().equals(account)))
                           .collect(Collectors.toList());
        }

        @Override
        public List<Voucher> listAll() {
            return vouchers;
        }

        @Override
        public Optional<Voucher> get(VoucherId voucherId) {
            return vouchers.stream().filter(v -> v.getId().equals(voucherId)).findAny();
        }
    }

}
