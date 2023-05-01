package com.bankapp.customer;

import com.bankapp.account.Account;
import com.bankapp.exceptions.CustomerNotFoundException;
import com.bankapp.exceptions.EmailExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    private CustomerService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerRepository);
    }

    @Test
    void canGetAllCustomers() {
        //when
        underTest.getCustomers();
        //then
        verify(customerRepository).findAll();
    }
    @Test
    void throwsExceptionWhenIdDoesNotExists() {
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
        //when
        //then
        assertThrows(CustomerNotFoundException.class,()-> underTest.getCustomer(2),"customer does not exist:" + customer.getId());
    }

    @Test
    void canAddNewCustomer() {
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
        //when
        underTest.addNewCustomer(customer);
        //then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertEquals(capturedCustomer,customer);
    }
    @Test
    void willThrowExceptionCustomerBecauseEmailIsTaken() {
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
        given(customerRepository.findByEmail(email)).willReturn(Optional.of(customer));

        //when
        //then
        assertThatThrownBy(() -> underTest.addNewCustomer(customer))
                .isInstanceOf(EmailExistsException.class)
                .hasMessage("email:" + customer.getEmail() + " is already present");
        verify(customerRepository, never()).save(any());
    }

    @Test
    void deleteCustomer() {
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
        //when
        underTest.addNewCustomer(customer);
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        underTest.deleteCustomer(1);
        //then
        var bitch = verify(customerRepository).findAll();
        assertEquals(bitch,null);
    }

    @Test
    @Disabled
    void editCustomer() {
    }
}