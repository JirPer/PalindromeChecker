import java.util.*;

public class Customer {

    private String name;
    private Long id;
    private List<Double> transactions;
    private Double initialAmount;
    private Double accountBalance = 0.00;

    public Customer(String name, Long id, Double initialAmount) {
        this.name = name;
        this.id = id;
        this.transactions = new ArrayList<>();
        this.initialAmount = initialAmount;
        this.accountBalance += initialAmount;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }
    public String getName() {
        return name;
    }
    public Long getId() {
        return id;
    }
    public void depositToCustomer(Double amount) {
        if(amount>0) {
            accountBalance+=amount;
            transactions.add(amount);
            System.out.println("Amount of " + amount + " has been added. Your balance is now: " + getAccountBalance());
        } else {
            System.err.println("Invalid format! Number must be greater than 0.");
        }
    }

    public void withdrawalFromCustomer(Double amount) {
        if(amount>0) {
            if(getAccountBalance()<amount) {
                System.err.println("Insufficient funds. You only have: " + getAccountBalance());
            } else {
                accountBalance -= amount;
                transactions.add(amount);
                System.out.println("Amount of " + amount + " has been subtracted. Your balance is now: " + getAccountBalance());
            }
        } else {
            System.err.println("Invalid format! Number must be greater than 0.");
        }
    }

    public List<Double> getTransactions() {
        return transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Name: '" + name +
                " - id: " + id +
                ", account balance: " + accountBalance + " ||\n";
    }
}
