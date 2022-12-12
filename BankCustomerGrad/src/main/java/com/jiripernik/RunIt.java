package com.jiripernik;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RunIt {
    private Scanner scanner = new Scanner(System.in);
    private Branch branch = new Branch("MMB");

    public void run() {

        int choice = 0;
        boolean out = false;
        displayOptions();

        try {
            branch.retrieveCustomerFile();
            branch.retrieveCustomerTransactions();
            Collections.sort(branch.getCustomerList());
        } catch (NoSuchElementException | NumberFormatException e) {
        }

        while(!out) {
            try{
            choice = scanner.nextInt();
            scanner.nextLine();}
            catch (InputMismatchException e) {
                System.err.println("you can only enter number of your choice!".toUpperCase());
                displayOptions();
                scanner.nextLine();
                continue;
            }


            switch(choice) {

                case 1:
                    displayOptions();
                    break;
                case 2:
                    addCustomerToBranch();
                    Collections.sort(branch.getCustomerList());
                    break;
                case 3:
                    removeCustomer();
                    break;
                case 4:
                    updateCustomer();
                    break;
                case 5:
                    displayCustomers();
                    break;
                case 6:
                    customerDeposit();
                    break;
                case 7:
                    customerWithdrawal();
                    break;
                case 8:
                    displayCustomerTransactions();
                    break;
                case 9:
                    System.out.println("Turning off..");
                    out = true;
            }
            branch.createCustomerFile();
        }

    }
    public void addCustomerToBranch() {
        try {
        String name;
        Long id;
        Double initialAmount;
        System.out.println("Please insert customer name: ");
        name = scanner.nextLine();
        System.out.println("Please insert customer id: ");
        id = scanner.nextLong();
        System.out.println("Please insert initial amount in form 0,0: ");
        initialAmount = scanner.nextDouble();
        branch.addCustomer(new Customer(name,id,initialAmount));
            }
        catch (InputMismatchException a) {
            System.err.println("Wrong input!! You must enter a number.".toUpperCase());
            displayOptions();
            scanner.nextLine();
        }
    }

    public void removeCustomer() {

        try {
        Long id;
        System.out.println("Please insert customer id you want to remove: ");
        id = scanner.nextLong();
              branch.removeCustomer(id);
        } catch (InputMismatchException b) {
              System.err.println("You must enter ID number".toUpperCase());
              scanner.nextLine();
        }
    }

    public void updateCustomer() {
        try {
            Long id;
            String name;
            System.out.println("Please insert customer id you want to update: ");
            id = scanner.nextLong();
            scanner.nextLine();
            if(branch.findCustomer(id)) {
                System.out.println("Insert new name of the client: ");
                name = scanner.nextLine();
                branch.updateCustomer(id, name);
                System.out.println("New name of the customer with id " + id + " is: " + name);
            } else {
                System.err.println("There is no customer with id: " + id);
            }

        } catch(InputMismatchException e){
            System.err.println("Wrong input! Try again please.".toUpperCase());
            scanner.nextLine();
        }
    }

    public void displayOptions() {
        System.out.println("\s PRESS: \n " +
                "1 - to display options \n " +
                "2 - to add new customer \n " +
                "3 - to remove customer \n " +
                "4 - to update customer \n " +
                "5 - to display customers \n " +
                "6 - to customer deposit \n " +
                "7 - to customer withdrawal \n " +
                "8 - to display customer's transactions \n " +
                "9 - to turn off" +
                "\n"
        );
    }
    public void displayCustomers() {
        branch.getBranchCustomers();
    }
    public void customerDeposit() {
        try {
        Long id;
        Double amount;
        System.out.println("Please enter an id of customer making a transaction: ");
        id = scanner.nextLong();
        if(branch.findCustomer(id)) {
            System.out.println("Please enter amount you want to deposit: ");
            amount = scanner.nextDouble();
            branch.depositToCustomer(id, amount);
        } else {
            System.err.println("There is no customer with id: " + id);
            }
        } catch (InputMismatchException e) {
            System.err.println("Wrong input! You must enter a number.".toUpperCase());
            scanner.nextLine();
        }
    }
    public void customerWithdrawal() {
        try {
        Long id;
        Double amount;
        System.out.println("Please enter an id of customer making a transaction: ");
        id = scanner.nextLong();
        if(branch.findCustomer(id)) {
            System.out.println("Please enter amount you want to subtract: ");
            amount = scanner.nextDouble();
            branch.withdrawalFromCustomer(id,amount);
        } else {
            System.err.println("There is no customer with id: " + id);
            }
        } catch (InputMismatchException e) {
            System.err.println("Wrong input! You must enter a number.".toUpperCase());
            scanner.nextLine();
        }
    }
    public void displayCustomerTransactions() {
        try {
        Long id;
        System.out.println("Please enter id of customer, you want to display: ");
        id = scanner.nextLong();
        if(branch.findCustomer(id)) {
            branch.getCustomerTransactions(id);
        } else {
            System.err.println("There is no customer with id: " + id);}
        } catch (InputMismatchException e) {
            System.err.println("Wrong input! You must enter a number.". toUpperCase());
            scanner.nextLine();
        }
    }
}
