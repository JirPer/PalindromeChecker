package com.jiripernik;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class BranchTest {

    Customer cus = new Customer("badboy",1L,20.00);
    Branch branch = new Branch("MMB");

    @Test
    void addCustomer() {
        branch.addCustomer(cus);
        List<Customer> expected = Arrays.asList(cus);
        Assertions.assertIterableEquals(expected, branch.getCustomerList());
    }

    @Test
    void removeCustomer() {
        branch.addCustomer(cus);
        branch.removeCustomer(1L);
        Assertions.assertTrue(branch.getCustomerList().isEmpty());
    }

    @Test
    void updateCustomer() {

    }

    @Test
    void depositToCustomer() {
        branch.addCustomer(cus);
        branch.depositToCustomer(1L,10.00);
        List<Double> trans = Arrays.asList(20.00, 10.00);
        Assertions.assertIterableEquals(trans, cus.getTransactions());
    }

    @Test
    void withdrawalFromCustomer() {
        branch.addCustomer(cus);
        branch.withdrawalFromCustomer(1L,10.00);
        List<Double> trans = Arrays.asList(20.00, -10.00);
        Assertions.assertIterableEquals(trans, cus.getTransactions());
    }
    @Test
    void getCustomerTransactions() {
        branch.addCustomer(cus);
        Assertions.assertNotNull(cus.getTransactions());
    }
}