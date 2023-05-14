package com.bankapp.account;

import com.bankapp.customer.Customer;
import com.bankapp.customer.CustomerRepository;
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
@WebMvcTest(value = AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private AccountService accountService;
    @Autowired
    private ObjectMapper objectMapper;

    private final Customer customer = new Customer(1,"Jack", "Sparrow", new ArrayList<>(),"jack.sparrow@gmail.com",2.5);
    private final Account account = new Account(1,"Jacks", new Customer(),new ArrayList<>());

    @Test
    void canGetAllAccounts() throws Exception {
        //given
        List<Account> listOfAccounts = new ArrayList<>();
        listOfAccounts.add(account);
        given(accountService.getAllAccounts()).willReturn(listOfAccounts);

        //when
        ResultActions response = mockMvc.perform(get("/customers/accounts"));

        //then
        response.andDo(print())
                .andExpect(jsonPath("$.size()").value(listOfAccounts.size()));

    }

    @Test
    void canRetrieveCustomerAccount() throws Exception {
        //given
        List<Account> listOfAccounts = new ArrayList<>();
        listOfAccounts.add(account);
        account.setCustomer(customer);
        customer.setAccount(listOfAccounts);
        given(customerRepository.findById(customer.getId())).willReturn(Optional.of(customer));
        given(accountService.getAllCustomerAccounts(customer.getId())).willReturn(listOfAccounts);

        //when
        ResultActions response = mockMvc.perform(get("/customers/{cid}/accounts", customer.getId()));


        //then
        response.andDo(print())
                .andExpect(jsonPath("$[0].account_name").value(account.getAccountName()));
    }

    @Test
    void canCreateCustomerAccount() throws Exception{
        //given
        given(accountService.createCustomerAccount(anyInt(),any(Account.class))).willReturn(account);

        //when
        ResultActions response = mockMvc.perform(post("/customers/{cid}/accounts",customer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(account)));

        //then
        response.andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void deleteAccountById() throws Exception {
        //given
        given(customerRepository.findById(customer.getId())).willReturn(Optional.of(customer));
        given(accountService.getAllCustomerAccounts(account.getId())).willReturn(customer.getAccount());

        //when
        ResultActions response = mockMvc.perform(delete("/customers/{cid}/accounts/{aid}",customer.getId(),account.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(account)));

        //then
        response.andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void editAccountName() throws Exception {
        //given
        Account editedAccount = new Account(account.getId(), "Jacksies",new Customer(),new ArrayList<>());
        given(accountService.getAllCustomerAccounts(account.getId())).willReturn(customer.getAccount());
        given(accountService.getAllCustomerAccounts(editedAccount.getId())).willReturn(customer.getAccount());

        //when
        ResultActions response = mockMvc.perform(put("/customers/{cid}/accounts/{aid}?accountName={name}",customer.getId(),account.getId(),editedAccount.getAccountName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(editedAccount)));


        //then
        response.andDo(print())
                .andExpect(status().is2xxSuccessful()); 
    }
}