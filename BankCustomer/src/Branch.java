import java.util.*;

public class Branch {
    private Set<Customer> branchCustomersSet = new HashSet<>();
    private List<Customer> branchCustomersList = new ArrayList<>();
    private String branchName;


    public Branch(String name) {
        this.branchName = name;
    }
    public boolean addCustomer(Customer newCustomer) {
        if(findCustomer(newCustomer) == null) {
            System.out.println("Customer " + newCustomer.getName() + " with id: " + newCustomer.getId() + " has been added.");
            branchCustomersList.add(newCustomer);
            branchCustomersSet.add(newCustomer);
            return true;
        } else {
            System.err.println("Customer with id: " + newCustomer.getId() + ", is already on file. Customer not added!");
            return false;
        }
    }
    public void removeCustomer(Long id) {
        boolean existingId;
        for (int i = 0; i < branchCustomersList.size(); i++) {
            existingId = branchCustomersList.get(i).getId().equals(id);
            if(existingId) {
                branchCustomersList.remove(i);
                System.out.println("Customer with id: " + id + " was successfully removed.");
            }
            if(!existingId) {
                System.err.println("Customer with id: " + id + " is not in list.");
            }
            if (branchCustomersList.isEmpty()) {
                System.err.println("Customer list is empty.");
            }
        }
    }

    public void getBranchCustomers() {
        Iterator<Customer> setIterator = branchCustomersSet.iterator();
        if(setIterator.hasNext()) {
            System.out.println(branchCustomersSet);
        } else {
            System.err.println("There is no customer in branch's customers list.");
        }
    }
    public Customer findCustomer(Customer newCustomer) {
        for(int i =0;i< branchCustomersList.size();i++) {
            if(branchCustomersList.get(i).hashCode() == (newCustomer.hashCode())) {
                return newCustomer;
            }
        } return null;
    }

    public boolean findCustomer(Long id) {
        boolean existingCustomer;
        for(int i=0;i< branchCustomersList.size();i++) {
            existingCustomer = branchCustomersList.get(i).getId().equals(id);
            if(existingCustomer) {
                return true;
        }
        } return false;
    }

    public void depositToCustomer(Long id, Double amount) {
        boolean existingId;
        if(findCustomer(id)) {
            for (int i = 0; i < branchCustomersList.size(); i++) {
                existingId = branchCustomersList.get(i).getId().equals(id);
                if (existingId) {
                    branchCustomersList.get(i).depositToCustomer(amount);
                } else {
                    System.err.println("There is no customer with id: " + id);
                }
            }
        }
    }

    public void withdrawalFromCustomer(Long id, Double amount) {
        boolean existingId;
        if(findCustomer(id)) {
            for (int i = 0; i < branchCustomersList.size(); i++) {
                existingId = branchCustomersList.get(i).getId().equals(id);
                if (existingId) {
                    branchCustomersList.get(i).withdrawalFromCustomer(amount);
                } else {
                    System.err.println("There is no customer with id: " + id);
                }
            }
        }
    }
    public void getCustomerTransactions(Long id) {
        boolean existingId;
        if(findCustomer(id)) {
            for(int i=0;i<branchCustomersList.size();i++) {
                existingId = branchCustomersList.get(i).getId().equals(id);
                if(existingId) {
                    System.out.println(branchCustomersList.get(i).getTransactions());
                } else {
                    System.err.println("There is no customer with id: " + id);
                }
            }
        }
    }
}
