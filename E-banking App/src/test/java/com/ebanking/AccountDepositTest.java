package com.ebanking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountDepositTest {

    @Test
    void depositIncreasesAccountBalance() {
        Account account = new Account("Arta", 100);

        account.deposit(50);

        assertEquals(150, account.getBalance());
    }
}
