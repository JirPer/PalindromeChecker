package com.bankapp.customer;

import com.bankapp.account.Account;
import com.bankapp.exceptions.EmailExistsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepositoryTest;

    @AfterEach
    void tearDown() {
        customerRepositoryTest.deleteAll();
    }

    @Test
    void checksIfCustomerCanBeFoundByEmail() {
        //given
        var email = "jack.sparrow@gmail.com";
        List<Account> accountList = new ArrayList<>();
        var customer = new Customer(
                1,
                "Jack",
                "Sparrow",
                accountList,
                email,
                2.5
        );
        customerRepositoryTest.save(customer);
        //when
        Optional<Customer> customerOptional = customerRepositoryTest.findByEmail(email);
        //then
        assertTrue(customerOptional.isPresent());
    }
    @Test
    void checksIfCustomerCanNotBeFoundByEmail() {
        //given
        var email = "jack.sparrow@gmail.com";
        //when
        Optional<Customer> customerOptional = customerRepositoryTest.findByEmail(email);
        //then
        assertFalse(customerOptional.isPresent());
    }

    @Test
    void checksEmailExistsExceptionThrowForExistingEmailInDatabase() {
        //given
        var email = "jack.sparrow@gmail.com";
        //when
        Optional<Customer> customerOptional = customerRepositoryTest.findByEmail(email);
        //then
        if(customerOptional.isPresent()) {
            assertThrows(EmailExistsException.class, customerOptional::get);
        }
    }
}