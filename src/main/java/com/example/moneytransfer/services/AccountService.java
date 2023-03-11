package com.example.moneytransfer.services;

import com.example.moneytransfer.data.entities.Account;

public interface AccountService {
    Account createAccount(Account account);
    Account findAccount(Long accountId);
}
