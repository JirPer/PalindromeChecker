package com.bankapp.customer;

import com.bankapp.exceptions.CustomerNotFoundException;
import com.bankapp.exceptions.EmailExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepositoryMock;
    private CustomerService customerServiceTest;
    private final Customer customer = new Customer(1,"Jack","Sparrow",new ArrayList<>(),"jack.sparrow@gmail.com",2.5);
    @BeforeEach
    public void setUp() {
        customerRepositoryMock = mock(CustomerRepository.class);
        customerServiceTest = new CustomerService(customerRepositoryMock);
    }

    @Test
    void gettingAllCustomers() {
        //when
        customerServiceTest.getCustomers();
        //then
        verify(customerRepositoryMock).findAll();
    }
    @Test
    void canAddCustomer() {
        //when
        customerServiceTest.addNewCustomer(customer);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepositoryMock).save(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();
        //then
        assertEquals(customer,capturedCustomer);
    }
    @Test
    void canFindOptional() {
        //when
        customerServiceTest.customerOptional(customer.getId());
        //then
        verify(customerRepositoryMock).findById(customer.getId());
    }
    @Test
    void canGetCustomer() {
        //given
        given(customerRepositoryMock.findById(customer.getId())).willReturn(Optional.of(customer));
        //when
        customerServiceTest.getCustomer(customer.getId());
        //then
        verify(customerRepositoryMock, times(2)).findById(customer.getId());
    }
    @Test
    void canDeleteCustomer() {
        //given
        when(customerServiceTest.customerOptional(customer.getId())).thenReturn(Optional.of(customer));
        //when
        customerServiceTest.deleteCustomer(customer.getId());
        //then
        verify(customerRepositoryMock).deleteById(customer.getId());
    }

    @Test
    void canEditCustomer() {
        //given
        when(customerServiceTest.customerOptional(customer.getId())).thenReturn(Optional.of(customer));
        //when
        customerServiceTest.editCustomer(customer.getId(),"Jakcie","Sparrows","jackie.sparrows@gmail.com");
        //then
        assertNotEquals(customer,new Customer());
    }

    @Test
    void throwsExceptionCustomerBecauseEmailIsTaken() {
        //given
        given(customerRepositoryMock.findByEmail(customer.getEmail())).willReturn(Optional.of(customer));
        //when
        //then
        assertThatThrownBy(() -> customerServiceTest.addNewCustomer(customer))
                .isInstanceOf(EmailExistsException.class)
                .hasMessage("email:" + customer.getEmail() + " is already present");
        verify(customerRepositoryMock, never()).save(any());
    }

    @Test
    void throwsExceptionWhenCustomerIdDoesNotExists() {
        //given
        given(customerRepositoryMock.findById(2)).willReturn(Optional.empty());
        //when
        //then
        assertThrows(CustomerNotFoundException.class,() -> customerServiceTest.getCustomer(2));
    }



}