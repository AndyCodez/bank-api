package com.example.moneytransfer.controllers;

import com.example.moneytransfer.data.entities.Account;
import com.example.moneytransfer.data.repositories.AccountRepository;
import com.example.moneytransfer.services.AccountService;
import com.example.moneytransfer.services.AccountServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        Account account = new Account("Janet Doe", new BigDecimal(300));
        this.accountRepository.save(account);

        RequestBuilder request = MockMvcRequestBuilders.get("/accounts/"+ 1);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Janet Doe"))
                .andExpect(jsonPath("$.balance").value(300));
    }

}