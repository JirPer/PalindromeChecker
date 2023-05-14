package com.bankapp.account;

import com.bankapp.customer.Customer;
import com.bankapp.customer.CustomerRepository;
import com.bankapp.exceptions.AccountNotFoundException;
import com.bankapp.exceptions.NameExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepositoryMock;

    @Mock
    private CustomerRepository customerRepositoryMock;

    private AccountService accountServiceTest;

    private final Customer customer = new Customer(3, "Jack", "Sparrow", new ArrayList<>(), "jack.sparrow@gmail.com", 2.5);

    private final Account account = new Account(3, "Jacks", new Customer(), new ArrayList<>());
    @BeforeEach
    public void setUp() {
        accountRepositoryMock = mock(AccountRepository.class);
        customerRepositoryMock = mock(CustomerRepository.class);
        accountServiceTest = new AccountService(accountRepositoryMock, customerRepositoryMock);
    }

    @Test
    void gettingAllAccounts() {
        //given
        //when
        accountServiceTest.getAllAccounts();
        //then
        verify(accountRepositoryMock).findAll();
    }

    @Test
    void getAllCustomerAccounts() {
        //given
        given(customerRepositoryMock.findById(customer.getId()))
                .willReturn(Optional.of(customer));
        //when
        accountServiceTest.getAllCustomerAccounts(customer.getId());
        //then
        verify(customerRepositoryMock, times(2))
                .findById(customer.getId());
    }

    @Test
    void canCreateAccountForCustomer() {
        //given
        given(customerRepositoryMock.findById(customer.getId()))
                .willReturn(Optional.of(customer));
        //when
        accountServiceTest.createCustomerAccount(customer.getId(), account);

        ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepositoryMock).save(accountArgumentCaptor.capture());
        Account capturedAccount = accountArgumentCaptor.getValue();
        //then
        assertEquals(account,capturedAccount);
    }

    @Test
    void canDeleteCustomerAccountById() {
        //given
        given(accountRepositoryMock.findById(account.getId())).willReturn(Optional.of(account));
        //when
        accountServiceTest.deleteAccount(account.getId());
        //then
        verify(accountRepositoryMock).deleteById(account.getId());
    }

    @Test
    void canEditAccountNameWithGivenCustomer() {
        //given
        List<Account> listOfAccounts = new ArrayList<>();
        customer.setAccount(listOfAccounts);
        listOfAccounts.add(account);
        given(accountRepositoryMock.findById(account.getId())).willReturn(Optional.of(account));
        given(customerRepositoryMock.findById(customer.getId())).willReturn(Optional.of(customer));
        //when
        accountServiceTest.editAccount(account.getId(), customer.getId(), "Jacksies");
        //then
        verify(accountRepositoryMock).findById(account.getId());
        assertNotEquals(account,new Account());
    }
    @Test
    void throwsAccountNotFoundExceptionWhenAccountIdDoesNotExist() {
        //given
        given(customerRepositoryMock.findById(2)).willReturn(Optional.of(customer));
        given(accountRepositoryMock.findById(2)).willReturn(Optional.empty());
        //when
        //then
        assertThrows(AccountNotFoundException.class,()->accountServiceTest.editAccount(2,2,"Jacksies"),"account id does not exist:" + account.getId());
    }
    @Test
    void throwsAccountNotFoundExceptionWhenCustomersAccountListDoesNotContainIt() {
        //given
        given(customerRepositoryMock.findById(customer.getId())).willReturn(Optional.of(customer));
        given(accountRepositoryMock.findById(account.getId())).willReturn(Optional.of(account));
        //when
        //then
        assertThrows(AccountNotFoundException.class,()->accountServiceTest.editAccount(3,3,"Jacksies"),"account id belongs to other customer:" + account.getId());
    }
    @Test
    void throwsNameExistsExceptionWhenAccountNameIsAlreadyTaken() {
        //given
        given(accountRepositoryMock.findByAccountName(account.getAccountName()))
                .willReturn(Optional.of(account));
        given(customerRepositoryMock.findById(customer.getId()))
                .willReturn(Optional.of(customer));
        //when
        //then
        assertThrows(NameExistsException.class,()-> accountServiceTest.createCustomerAccount(customer.getId(), account));
    }
}