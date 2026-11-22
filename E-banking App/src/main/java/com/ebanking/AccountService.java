package com.ebanking;

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void transfer(String sourceAccountId, String destinationAccountId, long amount) {
        Account source = accountRepository.findById(sourceAccountId).orElseThrow();
        Account destination = accountRepository.findById(destinationAccountId).orElseThrow();

        source.withdraw(amount);
        destination.deposit(amount);
    }
}
