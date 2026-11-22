package com.ebanking;

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void transfer(String sourceAccountId, String destinationAccountId, long amount) {
        Account source = findAccount(sourceAccountId);
        Account destination = findAccount(destinationAccountId);

        source.withdraw(amount);
        destination.deposit(amount);
    }

    public void deposit(String accountId, long amount) {
        findAccount(accountId).deposit(amount);
    }

    public void withdraw(String accountId, long amount) {
        findAccount(accountId).withdraw(amount);
    }

    private Account findAccount(String accountId) {
        return accountRepository.findById(accountId).orElseThrow();
    }

}
