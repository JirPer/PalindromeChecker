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
        } catch (NoSuchElementException | NumberFormatException e) {
        }

        while(!out) {
            try{
            choice = scanner.nextInt();
            scanner.nextLine();}
            catch (InputMismatchException e) {
                System.err.println("YOU CAN ONLY ENTER NUMBERS OF YOUR CHOICE!");
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
                    break;
                case 3:
                    removeCustomer();
                    break;
                case 4:
                    displayCustomers();
                    break;
                case 5:
                    customerDeposit();
                    break;
                case 6:
                    customerWithdrawal();
                    break;
                case 7:
                    displayCustomerTransactions();
                    break;
                case 8:
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
            System.err.println("WRONG INPUT!! YOU HAVE TO ENTER A NUMBER.");
            scanner.nextLine();
        }
    }

    public void removeCustomer() {

        try {
        Long id;
        System.out.println("Please insert customer id you want to remove : ");
        id = scanner.nextLong();
              branch.removeCustomer(id);
        } catch (InputMismatchException b) {
              System.err.println("YOU HAVE TO ENTER ID NUMBER!!");
              scanner.nextLine();
        }
    }
    public void displayOptions() {
        System.out.println("\s PRESS: \n " +
                "1 - to display options \n " +
                "2 - to add new customer \n " +
                "3 - to remove customer \n " +
                "4 - to display customers \n " +
                "5 - to customer deposit \n " +
                "6 - to customer withdrawal \n " +
                "7 - to display customer's transactions \n " +
                "8 - to turn off"
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
            System.err.println("WRONG INPUT! YOU HAVE TO ENTER A NUMBER.");
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
            System.err.println("WRONG INPUT! YOU HAVE TO ENTER A NUMBER.");
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
            System.err.println("WRONG INPUT! YOU HAVE TO ENTER A NUMBER.");
            scanner.nextLine();
        }
    }
}
