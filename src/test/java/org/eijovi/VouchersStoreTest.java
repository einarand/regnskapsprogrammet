package org.eijovi;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ZERO;
import static org.testng.Assert.assertEquals;

public class VouchersStoreTest {

    @Test
    public void testForAccount() {
        Account bank = new Account("Bankinnskudd");
        Account mat = new Account("Mat");
        Account tobakk = new Account("Tobakk");
        Account husholdning = new Account("Husholdning");
        Account bensin = new Account("Bensin");
        Account lonn = new Account("Lonn");

        VouchersStore store = new VouchersStore.InMemoryVouchersStore();

        store.store(Voucher.builder()
                           .description("Lønn fra Cisco")
                           .addEntry(bank, 50000)
                           .addEntry(lonn, -50000)
                           .build(Instant.now()));

        store.store(Voucher.builder()
                           .description("Rema 1000")
                           .addEntry(bank, -300)
                           .addEntry(mat, 190)
                           .addEntry(tobakk, 90)
                           .addEntry(husholdning, 20)
                           .build(Instant.now()));

        store.store(Voucher.builder()
                           .description("Circle K")
                           .addEntry(bank, -900)
                           .addEntry(mat, 300)
                           .addEntry(bensin, 600)
                           .build(Instant.now()));

        Account account = bank;

        List<Voucher> vouchers = store.list(account.id());
        //assertEquals(vouchers.size(), 3);

        List<Voucher.Entry> entries = vouchers.stream()
                                              .flatMap(v -> v.entries().stream()).filter(e -> e.account().equals(account.id()))
                                              .collect(Collectors.toList());
        //assertEquals(entries.size(), 3);
        System.out.println("Description\t\tAmount");
        for (Voucher.Entry e : entries) {
            System.out.println(StringUtils.rightPad(store.get(e.voucherId()).get().description(), 15) + "\t" + e.amount());
        }

        store.listAll().forEach(v -> assertEquals(v.sum(), ZERO));
    }
}