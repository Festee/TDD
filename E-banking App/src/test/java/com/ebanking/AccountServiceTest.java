package com.ebanking;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    @Test
    void depositsMoneyIntoAccountFoundById() {
        Account account = new Account("Arta", 100);
        AccountRepository repository = mock(AccountRepository.class);
        when(repository.findById("account-1")).thenReturn(Optional.of(account));
        AccountService service = new AccountService(repository);

        service.deposit("account-1", 25);

        assertEquals(125, account.getBalance());
    }

    @Test
    void rejectsWithdrawalThatExceedsAccountBalance() {
        Account account = new Account("Arta", 100);
        AccountRepository repository = mock(AccountRepository.class);
        when(repository.findById("account-1")).thenReturn(Optional.of(account));
        AccountService service = new AccountService(repository);

        assertThrows(IllegalArgumentException.class,
                () -> service.withdraw("account-1", 101));
    }

    @Test
    void transfersMoneyBetweenAccounts() {
        Account source = new Account("Arta", 100);
        Account destination = new Account("Blerina", 50);
        AccountRepository repository = mock(AccountRepository.class);
        when(repository.findById("source")).thenReturn(Optional.of(source));
        when(repository.findById("destination")).thenReturn(Optional.of(destination));
        AccountService service = new AccountService(repository);

        service.transfer("source", "destination", 40);

        assertEquals(60, source.getBalance());
        assertEquals(90, destination.getBalance());
    }
}
