package com.bankapp.transaction;

import com.bankapp.account.Account;
import com.bankapp.account.AccountRepository;
import com.bankapp.customer.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = TransactionsController.class)
class TransactionsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountRepository accountRepositoryTest;
    @MockBean
    private TransactionsRepository transactionsRepositoryTest;
    @MockBean
    private TransactionsService transactionsServiceTest;
    @Autowired
    private ObjectMapper objectMapper;

    private final Customer customer = new Customer(1,"Jack", "Sparrow", new ArrayList<>(),"jack.sparrow@gmail.com",2.5);
    private final Account account = new Account(1,"Jacks", new Customer(),new ArrayList<>());
    private final Transactions transactions = new Transactions(1L,3.0,new Account(), LocalDateTime.now());
    @Test
    void getAccountTransactions() throws Exception {
        //given
        List<Transactions> listOfTransactions = new ArrayList<>();
        listOfTransactions.add(transactions);

        given(transactionsServiceTest.getAllAccountTransactions(customer.getId(), account.getId())).willReturn(listOfTransactions);
        //when
        ResultActions response = mockMvc.perform(get("/customers/{cid}/accounts/{aid}",customer.getId(),account.getId()));
        //then
        response.andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[0].id").value(transactions.getId()));

    }

    @Test
    void createTransaction() throws Exception {
        //given
        given(transactionsServiceTest.crateTransaction(anyInt(), anyInt(),any(Transactions.class))).willReturn(transactions);
        //when
        ResultActions response = mockMvc.perform(post("/customers/{cid}/accounts/{aid}/transactions",customer.getId(),account.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactions)));
        //then
        response.andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void deleteTransaction() throws Exception{
        //given
        List<Transactions> listOfTransactions = new ArrayList<>();
        listOfTransactions.add(transactions);
        account.setTransfers(listOfTransactions);

        given(accountRepositoryTest.findById(account.getId())).willReturn(Optional.of(transactions.getAccount()));
        given(transactionsServiceTest.getAllAccountTransactions(customer.getId(),account.getId())).willReturn(listOfTransactions);
        //when
        ResultActions response = mockMvc.perform(delete("/customers/{cid}/accounts/{aid}/transactions/{tid}",customer.getId(),account.getId(), transactions.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactions)));
        //then
        response.andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}