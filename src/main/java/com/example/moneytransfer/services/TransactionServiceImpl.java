package com.example.moneytransfer.services;

import com.example.moneytransfer.data.entities.Account;
import com.example.moneytransfer.data.entities.Transaction;
import com.example.moneytransfer.data.repositories.AccountRepository;
import com.example.moneytransfer.data.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public Transaction transferMoney(Transaction transaction) {
        BigDecimal amount = transaction.getAmount();
        Optional<Account> optionalSourceAccount = this.accountRepository.findById(transaction.getSourceAccountId());
        Optional<Account> optionalTargetAccount = this.accountRepository.findById(transaction.getTargetAccountId());

        if (optionalSourceAccount.isPresent() && optionalTargetAccount.isPresent()) {
            Account sourceAccount = optionalSourceAccount.get();
            Account targetAccount = optionalTargetAccount.get();

            if (sourceAccount.getBalance().compareTo(amount) >= 0) {
                sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
                targetAccount.setBalance(targetAccount.getBalance().add(amount));

                this.accountRepository.save(sourceAccount);
                this.accountRepository.save(targetAccount);

                return transactionRepository.save(transaction);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds");
            }

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }

    }
}
