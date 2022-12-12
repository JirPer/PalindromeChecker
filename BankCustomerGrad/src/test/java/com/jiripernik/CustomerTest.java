package com.jiripernik;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    Customer cus = new Customer("jiri", 1L,20.00);
    @org.junit.jupiter.api.Test
    void getAccountBalance() {
        Assertions.assertEquals(20.00,cus.getAccountBalance(),0.01);
    }

    @org.junit.jupiter.api.Test
    void depositToCustomer() {
        cus.depositToCustomer(20.00);
        Assertions.assertEquals(40.00,cus.getAccountBalance(),0.01);
    }

    @org.junit.jupiter.api.Test
    void withdrawalFromCustomer() {
        cus.withdrawalFromCustomer(10.00);
        Assertions.assertEquals(10.00,cus.getAccountBalance(),0.01);
    }

    @org.junit.jupiter.api.Test
    void getName() {
        Assertions.assertEquals("jiri", cus.getName());
    }

    @org.junit.jupiter.api.Test
    void getId() {
        Assertions.assertEquals(1, cus.getId());
    }

    @org.junit.jupiter.api.Test
    void getTransactions() {
        Double[] expected = {20.0};
        Assertions.assertArrayEquals(expected, cus.getTransactions().toArray());
    }

}