package com.bankapp.transaction;

import com.bankapp.account.Account;
import com.bankapp.account.AccountRepository;
import com.bankapp.customer.Customer;
import com.bankapp.customer.CustomerRepository;
import com.bankapp.exceptions.AccountNotFoundException;
import com.bankapp.exceptions.TransactionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionsServiceTest {

    @Mock
    private TransactionsRepository transactionsRepositoryMock;
    @Mock
    private AccountRepository accountRepositoryMock;
    @Mock
    private CustomerRepository customerRepositoryMock;
    private TransactionsService transactionsServiceTest;

    private final Customer customer = new Customer(1, "Jack", "Sparrow", new ArrayList<>(), "jack.sparrow@gmail.com", 2.5);
    private final Account account = new Account(1, "Jacks", new Customer(), new ArrayList<>());
    private final Transactions transactions = new Transactions(1L,3.0,new Account(), LocalDateTime.now());

    @BeforeEach
    public void setUp() {
        transactionsRepositoryMock = mock(TransactionsRepository.class);
        accountRepositoryMock = mock(AccountRepository.class);
        customerRepositoryMock = mock(CustomerRepository.class);
        transactionsServiceTest = new TransactionsService(transactionsRepositoryMock,accountRepositoryMock,customerRepositoryMock);
    }

    @Test
    void getAllAccountTransactions() {
        //given
        account.setCustomer(customer);

        given(customerRepositoryMock.findById(customer.getId())).willReturn(Optional.of(account.getCustomer()));
        given(accountRepositoryMock.findById(customer.getId())).willReturn(Optional.of(account));
        //when
        transactionsServiceTest.getAllAccountTransactions(customer.getId(), account.getId());
        //then
        verify(accountRepositoryMock,times(3)).findById(account.getId());
    }

    @Test
    void canCrateTransactionForCustomersAccount() {
        //given
        account.setCustomer(customer);

        given(customerRepositoryMock.findById(customer.getId())).willReturn(Optional.of(account.getCustomer()));
        given(accountRepositoryMock.findById(account.getId())).willReturn(Optional.of(account));
        //when
        transactionsServiceTest.crateTransaction(customer.getId(), account.getId(), transactions);

        ArgumentCaptor<Transactions> transactionArgumentCaptor = ArgumentCaptor.forClass(Transactions.class);
        verify(transactionsRepositoryMock).save(transactionArgumentCaptor.capture());
        Transactions capturedTransactions = transactionArgumentCaptor.getValue();
        //then
        assertEquals(transactions, capturedTransactions);

    }

    @Test
    void canDeleteCustomersAccountTransactionById() {
        //given
        account.setCustomer(customer);

        given(customerRepositoryMock.findById(customer.getId())).willReturn(Optional.of(account.getCustomer()));
        given(accountRepositoryMock.findById(account.getId())).willReturn(Optional.of(account));
        given(transactionsRepositoryMock.findById(transactions.getId())).willReturn(Optional.of(transactions));
        //when
        transactionsServiceTest.deleteTransactionById(customer.getId(), account.getId(), transactions.getId());
        //then
        verify(transactionsRepositoryMock).deleteById(transactions.getId());
    }

    @Test
    void throwsAccountNotFoundExceptionWhenAccountDoesNotBelongToCustomer() {
        //given
        given(customerRepositoryMock.findById(customer.getId())).willReturn(Optional.of(customer));
        given(accountRepositoryMock.findById(account.getId())).willReturn(Optional.of(account));
        //when
        //then
        assertThrows(AccountNotFoundException.class,() -> transactionsServiceTest.getAllAccountTransactions(customer.getId(), account.getId()),"customer does not have account id:" + account.getId());
    }
    @Test
    void throwsTransactionsNotFoundExceptionWhenTransactionIdIsNotFound() {
        //given
        given(customerRepositoryMock.findById(customer.getId())).willReturn(Optional.of(account.getCustomer()));
        given(accountRepositoryMock.findById(account.getId())).willReturn(Optional.of(account));
        //when
        //then
        assertThrows(TransactionNotFoundException.class,() -> transactionsServiceTest.deleteTransactionById(customer.getId(), account.getId(), 2L));

    }


}