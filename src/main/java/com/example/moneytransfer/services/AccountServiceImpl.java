package com.example.moneytransfer.services;

import com.example.moneytransfer.data.entities.Account;
import com.example.moneytransfer.data.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public Account createAccount(Account account) {
        return this.accountRepository.save(account);
    }

    @Override
    public Account findAccount(Long accountId) {
        return this.accountRepository.findById(accountId).get();
    }
}
