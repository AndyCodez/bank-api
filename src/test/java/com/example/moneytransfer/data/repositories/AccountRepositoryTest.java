package com.example.moneytransfer.data.repositories;

import com.example.moneytransfer.data.entities.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    void create_shouldCreateANewAccountWithASetBalance() {
        Account newAccount = new Account("John Doe", new BigDecimal(100));

        testEntityManager.persistAndFlush(newAccount);

        Account retrievedAccount = accountRepository.findById(1L).get();

        assertEquals(newAccount.getName(), retrievedAccount.getName());
        assertEquals(newAccount.getBalance(), retrievedAccount.getBalance());
    }
}