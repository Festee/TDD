package com.ebanking;

public class Account {
    private final String ownerName;
    private long balance;

    public Account(String ownerName, long openingBalance) {
        if (ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException("Owner name must not be blank");
        }
        if (openingBalance < 0) {
            throw new IllegalArgumentException("Opening balance must not be negative");
        }
        this.ownerName = ownerName.trim();
        this.balance = openingBalance;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        return balance;
    }

    public void deposit(long depositAmount) {
        balance += depositAmount;
    }
}
