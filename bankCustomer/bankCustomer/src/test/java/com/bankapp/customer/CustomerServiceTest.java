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

    @Mock private CustomerRepository customerRepositoryMock;
    private CustomerService customerService;
    private Customer customer = new Customer(3,"Jack","Sparrow",new ArrayList<>(),"jack.sparrow@gmail.com",2.5);
    @BeforeEach
    public void setup() {

        customerRepositoryMock = mock(CustomerRepository.class);
        customerService = new CustomerService(customerRepositoryMock);
    }

    @Test
    void gettingAllCustomers() {
        //when
        customerService.getCustomers();
        //then
        verify(customerRepositoryMock).findAll();
    }
    @Test
    void canAddCustomer() {
        //when
        customerService.addNewCustomer(customer);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepositoryMock).save(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();
        //then
        assertEquals(customer,capturedCustomer);
    }
    @Test
    void canFindOptional() {
        //when
        customerService.customerOptional(customer.getId());
        //then
        verify(customerRepositoryMock).findById(customer.getId());
    }
    @Test
    void canGetCustomer() {
        //given
        given(customerRepositoryMock.findById(customer.getId())).willReturn(Optional.of(customer));
        //when
        customerService.getCustomer(3);
        //then
        verify(customerRepositoryMock, times(2)).findById(3);
    }
    @Test
    void canDeleteCustomer() {
        //given
        when(customerService.customerOptional(customer.getId())).thenReturn(Optional.of(customer));
        //when
        customerService.deleteCustomer(3);
        //then
        verify(customerRepositoryMock).deleteById(3);
    }

    @Test
    void canEditCustomer() {
        //given
        when(customerService.customerOptional(customer.getId())).thenReturn(Optional.of(customer));
        //when
        customerService.editCustomer(customer.getId(),"jakcie","Sparrows","jackie.sparrows@gmail.com");
        //then
        assertNotEquals(customer,new Customer());
    }

    @Test
    void willThrowExceptionCustomerBecauseEmailIsTaken() {
        //given
        given(customerRepositoryMock.findByEmail(customer.getEmail())).willReturn(Optional.of(customer));
        //when
        //then
        assertThatThrownBy(() -> customerService.addNewCustomer(customer))
                .isInstanceOf(EmailExistsException.class)
                .hasMessage("email:" + customer.getEmail() + " is already present");
        verify(customerRepositoryMock, never()).save(any());
    }

    @Test
    void throwsExceptionWhenIdDoesNotExists() {
        //given
        given(customerRepositoryMock.findById(2)).willReturn(Optional.empty());
        //when
        //then
        assertThrows(CustomerNotFoundException.class,() -> customerService.getCustomer(2));
    }



}