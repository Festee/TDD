package com.ebanking;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findById(String accountId);
}
