package com.example.moneytransfer.services;

import com.example.moneytransfer.data.entities.Account;
import com.example.moneytransfer.data.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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
        Optional<Account> account = this.accountRepository.findById(accountId);
        if (account.isPresent()) {
            return account.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
    }
}
