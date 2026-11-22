package com.ebanking;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceTest {

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
