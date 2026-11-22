package com.ebanking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountWithdrawalTest {

    @Test
    void withdrawReducesAccountBalance() {
        Account account = new Account("Arta", 100);

        account.withdraw(40);

        assertEquals(60, account.getBalance());
    }
}
