package com.example.moneytransfer.controllers;

import com.example.moneytransfer.data.entities.Account;
import com.example.moneytransfer.data.entities.Transaction;
import com.example.moneytransfer.data.repositories.AccountRepository;
import com.example.moneytransfer.data.repositories.TransactionRepository;
import com.example.moneytransfer.services.AccountService;
import com.example.moneytransfer.services.AccountServiceImpl;
import com.example.moneytransfer.services.TransactionService;
import com.example.moneytransfer.services.TransactionServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    @Test
    void canCreateANewAccount() throws Exception {
        Account account = new Account("Jane Doe", new BigDecimal(300));

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(account);

        RequestBuilder request =
                MockMvcRequestBuilders
                        .post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Jane Doe"));

    }

    @Test
    void canRetrieveAccountInformation() throws Exception {
        this.accountRepository.deleteAll();
        Account account = new Account("Janet Doe", new BigDecimal(300));
        this.accountRepository.save(account);

        RequestBuilder request = MockMvcRequestBuilders.get("/accounts/"+ account.getId());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Janet Doe"))
                .andExpect(jsonPath("$.balance").value(300));
    }

    @Test
    void returnsA404ResponseIfRequestedResourceMissing() throws Exception {
        this.accountRepository.deleteAll();

        RequestBuilder request = MockMvcRequestBuilders.get("/accounts/"+ 1);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.name").doesNotExist())
                .andExpect(jsonPath("$.message").value("Account not found"));
    }

    @Test
    void can_TransferMoneyFromOneAccountToAnother() throws Exception {
        Account sourceAccount = new Account("James Bond", new BigDecimal(10000));
        Account targetAccount = new Account("Jill Bond", new BigDecimal(3000));
        this.accountRepository.save(sourceAccount);
        this.accountRepository.save(targetAccount);

        Transaction transaction = new Transaction(sourceAccount.getId(), targetAccount.getId(), new BigDecimal(2000));

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(transaction);

        RequestBuilder request =
                MockMvcRequestBuilders.post("/transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody);

        mockMvc.perform(request)
                .andExpect(status().isCreated());

        Account sourceAccountAfter = this.accountRepository.findById(sourceAccount.getId()).get();
        Account targetAccountAfter = this.accountRepository.findById(targetAccount.getId()).get();

        assertEquals(new BigDecimal("8000.00"), sourceAccountAfter.getBalance());
        assertEquals(new BigDecimal("5000.00"), targetAccountAfter.getBalance());
    }

    @Test
    void transferAmountMustBeGreaterThanZero () throws Exception {
        Account sourceAccount = new Account("James Bond", new BigDecimal(10000));
        Account targetAccount = new Account("Jill Bond", new BigDecimal(3000));
        this.accountRepository.save(sourceAccount);
        this.accountRepository.save(targetAccount);

        Transaction transaction = new Transaction(sourceAccount.getId(), targetAccount.getId(), new BigDecimal(0));

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(transaction);

        RequestBuilder request =
                MockMvcRequestBuilders.post("/transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value( "Transfer amount must be greater than zero"));
    }
}