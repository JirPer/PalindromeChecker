package com.bankapp.account;

import com.bankapp.customer.Customer;
import com.bankapp.customer.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepositoryTest;
    @Autowired
    private CustomerRepository customerRepositoryTest;
    @AfterEach()
    void tearDown() {
        accountRepositoryTest.deleteAll();
    }
    @Test
    void findAccountByName() {
        //given
        Customer customer = new Customer(1,"Jack", "Sparrow", new ArrayList<>(),"jack.sparrow@gmail.com",2.5);
        Account account = new Account(1,"Jacks",new Customer(),new ArrayList<>());

        List<Account> accountList = new ArrayList<>();
        accountList.add(account);
        account.setCustomer(customer);
        customer.setAccount(accountList);

        customerRepositoryTest.save(customer);
        accountRepositoryTest.save(account);

        //when
        Optional<Account> accountOptional = accountRepositoryTest.findByAccountName(account.getAccountName());

        //then
        assertTrue(accountOptional.isPresent());
    }

}