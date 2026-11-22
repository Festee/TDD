package com.ebanking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountDepositTest {

    @Test
    void depositIncreasesAccountBalance() {
        Account account = new Account("Arta", 100);

        account.deposit(50);

        assertEquals(150, account.getBalance());
    }

    @ParameterizedTest
    @ValueSource(longs = {0, -1})
    void rejectsZeroOrNegativeDeposit(long amount) {
        Account account = new Account("Arta", 100);

        assertThrows(IllegalArgumentException.class, () -> account.deposit(amount));
    }
}
