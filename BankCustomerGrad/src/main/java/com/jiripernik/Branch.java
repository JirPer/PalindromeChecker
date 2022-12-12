package com.jiripernik;

import java.io.*;
import java.util.*;

public class Branch {

    private List<Customer> branchCustomersList = new ArrayList<>();
    private String branchName;

    public Branch(String name) {
        this.branchName = name;
    }

    public void createCustomerFile() {
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("customers.txt",false));
            BufferedWriter bwT = new BufferedWriter(new FileWriter("transactions.txt", false));

            for(Customer cus : branchCustomersList) {
               bw.write(cus.getName() + ";;" + cus.getId() + ";;" + cus.getAccountBalance() + ";;" + cus.getTransactions().toString() + ";;\n");
               bw.flush();
               bwT.write(cus.getId() + ";;");
               bwT.newLine();
               for(Double tra : cus.getTransactions()) {
                   bwT.write(tra.toString() + ";");
               }
               bwT.write(";");
               bwT.newLine();
               bwT.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void retrieveCustomerFile() {
        Scanner scan = null;
        List<Double> transactions = new ArrayList<>();
        try{
           scan = new Scanner(new BufferedReader(new FileReader("customers.txt")));
           scan.useDelimiter(";;");
           if(scan.hasNextLine()) {
               System.out.println("customer file uploaded.".toUpperCase());
               while (scan.hasNextLine()) {
                   String name = scan.next();
                   scan.skip(";;");
                   Long id = Long.parseLong(scan.next());
                   scan.skip(";;");
                   Double balance = Double.parseDouble(scan.next());
                   scan.skip(";;");
                   transactions.add(balance);
                   scan.nextLine();
                   addCustomer(new Customer(name, id, balance));
               }
           } else {
               System.err.println("customer file not loaded, file is empty.".toUpperCase());
           }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(scan!=null) {
                scan.close();
            }
        }
    }

    public void retrieveCustomerTransactions() {
        Scanner scan = null;
        Long id;
        try {
            scan = new Scanner(new BufferedReader(new FileReader("transactions.txt")));
            scan.useDelimiter(";;");
            while(scan.hasNextLine()) {
                List<Double> transact = new ArrayList<>();
                id = Long.parseLong(scan.next());
                scan.nextLine();
                String inputLine = scan.nextLine();
                String withoutEnd = inputLine.substring(0,inputLine.indexOf(";;"));
                String[] transArray = withoutEnd.split(";");
                for(Customer cus : branchCustomersList) {
                    if(cus.getId().equals(id)) {
                        for(String str : transArray) {
                            transact.add(Double.parseDouble(str));
                            cus.setTransactions(transact);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(scan!=null) {
                scan.close();
            }
        }
    }

    public void addCustomer(Customer newCustomer) {
        if(findCustomer(newCustomer) == null) {
            System.out.println("Customer " + newCustomer.getName() + " with id: " + newCustomer.getId() + " has been added.");
            branchCustomersList.add(newCustomer);
        } else {
            System.err.println("Customer with id: " + newCustomer.getId() + ", is already on file. Customer of name: " + newCustomer.getName() + " WAS NOT ADDED!");
        }
    }

    public void removeCustomer(Long id) {

        boolean existingCustomer;
        if(branchCustomersList.isEmpty()) {
            System.out.println("There is no customer in branch's customers list.");
        } else {
            if(!findCustomer(id)) {
                System.err.println("Customer with id: " + id + " is not in the list.");
            } else {
                for(int i =0;i<branchCustomersList.size();i++) {
                    existingCustomer = branchCustomersList.get(i).getId().equals(id);
                    if(existingCustomer) {
                        branchCustomersList.remove(i);
                        System.out.println("Customer with id: " + id + " was successfully removed.");
                    }
                }
            }
        }
    }

    public void updateCustomer(Long id, String newName) {
        for(Customer cus : branchCustomersList) {
            if(cus.getId().equals(id)) {
                cus.setName(newName);
            }
        }
    }

    public void getBranchCustomers() {
        Iterator<Customer> listIterator = branchCustomersList.listIterator();
        if(listIterator.hasNext()) {
            System.out.println(branchCustomersList);
        } else {
            System.err.println("There is no customer in branch's customers list.");
        }
    }
    public List<Customer> getCustomerList() {
        return branchCustomersList;
    }
    public Customer findCustomer(Customer newCustomer) {
        for (Customer customer : branchCustomersList) {
            if (customer.hashCode() == (newCustomer.hashCode())) {
                return newCustomer;
            }
        }
        return null;
    }

    public boolean findCustomer(Long id) {
        boolean existingCustomer;
        for (Customer customer : branchCustomersList) {
            existingCustomer = customer.getId().equals(id);
            if (existingCustomer) {
                return true;
            }
        }
        return false;
    }

    public void depositToCustomer(Long id, Double amount) {
        boolean existingId;
        if(findCustomer(id)) {
            for (Customer customer : branchCustomersList) {
                existingId = customer.getId().equals(id);
                if (existingId) {
                    customer.depositToCustomer(amount);
                }
            }
        }
    }

    public void withdrawalFromCustomer(Long id, Double amount) {
        boolean existingId;
        if(findCustomer(id)) {
            for (Customer customer : branchCustomersList) {
                existingId = customer.getId().equals(id);
                if (existingId) {
                    customer.withdrawalFromCustomer(amount);
                }
            }
        }
    }

    public void getCustomerTransactions(Long id) {
        boolean existingId;
        if(findCustomer(id)) {
            for (Customer customer : branchCustomersList) {
                existingId = customer.getId().equals(id);
                if (existingId) {
                    System.out.println(customer.getTransactions());
                }
            }
        }
    }
}