package com.ebanking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

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

    @ParameterizedTest
    @CsvSource({"99, 1", "100, 0"})
    void acceptsWithdrawalAtAndBelowAvailableBalance(long amount, long expectedBalance) {
        Account account = new Account("Arta", 100);
        AccountRepository repository = mock(AccountRepository.class);
        when(repository.findById("account-1")).thenReturn(Optional.of(account));
        AccountService service = new AccountService(repository);

        service.withdraw("account-1", amount);

        assertEquals(expectedBalance, account.getBalance());
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

    @Test
    void rejectsTransferWhenSourceHasInsufficientFunds() {
        Account source = new Account("Arta", 100);
        Account destination = new Account("Blerina", 50);
        AccountRepository repository = mock(AccountRepository.class);
        when(repository.findById("source")).thenReturn(Optional.of(source));
        when(repository.findById("destination")).thenReturn(Optional.of(destination));
        AccountService service = new AccountService(repository);

        assertThrows(IllegalArgumentException.class,
                () -> service.transfer("source", "destination", 101));
        assertEquals(100, source.getBalance());
        assertEquals(50, destination.getBalance());
    }

    @ParameterizedTest
    @ValueSource(longs = {0, -1})
    void rejectsZeroOrNegativeTransfer(long amount) {
        Account source = new Account("Arta", 100);
        Account destination = new Account("Blerina", 50);
        AccountRepository repository = mock(AccountRepository.class);
        when(repository.findById("source")).thenReturn(Optional.of(source));
        when(repository.findById("destination")).thenReturn(Optional.of(destination));
        AccountService service = new AccountService(repository);

        assertThrows(IllegalArgumentException.class,
                () -> service.transfer("source", "destination", amount));
        assertEquals(100, source.getBalance());
        assertEquals(50, destination.getBalance());
    }

    @Test
    void rejectsTransferToMissingAccountWithoutChangingBalances() {
        Account source = new Account("Arta", 100);
        AccountRepository repository = mock(AccountRepository.class);
        when(repository.findById("source")).thenReturn(Optional.of(source));
        when(repository.findById("missing")).thenReturn(Optional.empty());
        AccountService service = new AccountService(repository);

        assertThrows(java.util.NoSuchElementException.class,
                () -> service.transfer("source", "missing", 40));
        assertEquals(100, source.getBalance());
    }
}
