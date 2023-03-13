package com.example.moneytransfer.services;

import com.example.moneytransfer.data.entities.Account;
import com.example.moneytransfer.data.entities.Transaction;
import com.example.moneytransfer.data.repositories.AccountRepository;
import com.example.moneytransfer.data.repositories.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction transferMoney(Transaction transaction) {
        logger.trace("transferMoney");

        BigDecimal amount = transaction.getAmount();

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer amount must be greater than zero");
        }

        Optional<Account> optionalSourceAccount = this.accountRepository.findById(transaction.getSourceAccountId());
        Optional<Account> optionalTargetAccount = this.accountRepository.findById(transaction.getTargetAccountId());

        if (optionalSourceAccount.isPresent() && optionalTargetAccount.isPresent()) {
            Account sourceAccount = optionalSourceAccount.get();
            Account targetAccount = optionalTargetAccount.get();

            synchronized (sourceAccount) {
                if (sourceAccount.getBalance().compareTo(amount) >= 0) {
                    sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
                    targetAccount.setBalance(targetAccount.getBalance().add(amount));

                    this.accountRepository.save(sourceAccount);
                    this.accountRepository.save(targetAccount);

                    transaction.setDateTime(LocalDateTime.now());
                    return transactionRepository.save(transaction);
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds");
                }
            }

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }

    }
}
