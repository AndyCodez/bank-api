package com.example.moneytransfer.controllers;

import com.example.moneytransfer.data.entities.Account;
import com.example.moneytransfer.data.entities.Transaction;
import com.example.moneytransfer.response.ResponseMessage;
import com.example.moneytransfer.services.AccountService;
import com.example.moneytransfer.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/")
public class ApiController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@RequestBody Account account) {
        return this.accountService.createAccount(account);
    }

    @GetMapping("/accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account findAccount(@PathVariable Long id) {
        return this.accountService.findAccount(id);
    }

    @PostMapping("/transfers")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction transferMoney(@RequestBody Transaction transaction) {
        return this.transactionService.transferMoney(transaction);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ResponseMessage> handleResponseStatusException(ResponseStatusException ex) {
        ResponseMessage responseMessage = new ResponseMessage(ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body(responseMessage);
    }
}
