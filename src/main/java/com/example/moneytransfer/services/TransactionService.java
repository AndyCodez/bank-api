package com.example.moneytransfer.services;

import com.example.moneytransfer.data.entities.Transaction;
import jakarta.transaction.Transactional;

public interface TransactionService {
    @Transactional
    Transaction transferMoney(Transaction transaction);
}
