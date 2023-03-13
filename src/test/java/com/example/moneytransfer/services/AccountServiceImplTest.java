package com.example.moneytransfer.services;

import com.example.moneytransfer.data.entities.Account;
import com.example.moneytransfer.data.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class AccountServiceImplTest {

    @Autowired
    AccountServiceImpl accountServiceImpl;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    void createAccount_createsANewAccount() {
        Account account = new Account("John Doe", new BigDecimal(200));

        given(accountRepository.save(account)).willReturn(account);

        assertEquals(account, accountServiceImpl.createAccount(account));
        Mockito.verify(accountRepository, Mockito.times(1)).save(account);
    }
}