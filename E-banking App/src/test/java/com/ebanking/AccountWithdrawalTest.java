package com.ebanking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountWithdrawalTest {

    @Test
    void withdrawReducesAccountBalance() {
        Account account = new Account("Arta", 100);

        account.withdraw(40);

        assertEquals(60, account.getBalance());
    }

    @ParameterizedTest
    @ValueSource(longs = {0, -1})
    void rejectsZeroOrNegativeWithdrawal(long amount) {
        Account account = new Account("Arta", 100);

        assertThrows(IllegalArgumentException.class, () -> account.withdraw(amount));
    }
}
