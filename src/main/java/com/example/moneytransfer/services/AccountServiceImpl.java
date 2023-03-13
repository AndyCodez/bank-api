package com.example.moneytransfer.services;

import com.example.moneytransfer.data.entities.Account;
import com.example.moneytransfer.data.repositories.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{
    private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(Account account) {
        logger.trace("Create account");

        return this.accountRepository.save(account);
    }

    @Override
    public Account findAccount(Long accountId) {
        logger.trace("Find account");

        Optional<Account> account = this.accountRepository.findById(accountId);
        if (account.isPresent()) {
            return account.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
    }
}
