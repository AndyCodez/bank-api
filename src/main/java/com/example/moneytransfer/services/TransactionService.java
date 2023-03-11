package com.example.moneytransfer.services;

import com.example.moneytransfer.data.entities.Transaction;
import org.springframework.stereotype.Service;

public interface TransactionService {
    Transaction transferMoney(Transaction transaction);
}
