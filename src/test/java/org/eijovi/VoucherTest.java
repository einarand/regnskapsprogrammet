package org.eijovi;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.time.Instant.now;
import static org.testng.Assert.assertEquals;

public class VoucherTest {

    @Test
    public void test() {
        Account kundefordringer = new Account("Kundefordringer");
        Account salg = new Account("Salgsinntekter");
        Account driftskonto = new Account("Driftskonto");

        Voucher v1 = Voucher.builder()
                            .addEntry(kundefordringer, 1000)
                            .addEntry(salg, -1000)
                            .build(now());

        Voucher v2 = Voucher.builder()
                            .debet(driftskonto, 1000)
                            .credit(kundefordringer, 1000)
                            .build(now());

        assertEquals(v1.sum(), ZERO);
        assertEquals(v1.sumCredit(), new BigDecimal(1000));
        assertEquals(v1.sumDebit(), new BigDecimal(1000));

        assertEquals(kundefordringer.saldo(), ZERO);
    }

}