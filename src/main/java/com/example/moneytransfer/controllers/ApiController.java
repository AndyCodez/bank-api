package com.example.moneytransfer.controllers;

import com.example.moneytransfer.data.entities.Account;
import com.example.moneytransfer.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ApiController {

    @Autowired
    AccountService accountService;

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@RequestBody Account account) {
        return this.accountService.createAccount(account);
    }

}
