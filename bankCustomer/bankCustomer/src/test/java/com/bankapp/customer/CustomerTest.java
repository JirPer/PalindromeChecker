package com.bankapp.customer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    private final Customer customer = new Customer(1,"Jack","Sparrow",new ArrayList<>(),"jack.sparrow@gmail.com",2.5);
    @Test
    void editBalance() {
        Double newBalance = 5.0;
        customer.editBalance(2.5);
        assertEquals(newBalance, customer.getBalance());
    }
}