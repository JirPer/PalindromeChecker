//import java.util.Scanner;
//
//public class Transactions {
//    Scanner scanner = new Scanner(System.in);
//    Customer customer;
//    Branch branch;
//    RunIt runIt = new RunIt();
//    private Long customerId;
//
//    public Transactions() {
//        this.customerId = customerId;
//    }
//
//    public Long getId() {
//        return customerId;
//    }
//
//    public void customerSelect() {
//
//        boolean back = false;
//        int choice = 0;
//
//        while(!back) {
//
//            switch (choice) {
//                case 1:
//                    getCustomerTransactions();
//                case 2:
//                    addCustomerTransaction();
//                case 3:
//                    back = true;
//                    runIt.run();
//            }
//        }
//    }
//    public void getCustomerTransactions() {
//        System.out.println("All customer transactions: ");
//        System.out.println(customer.getTransactions());
//    }
////    public void addCustomerTransaction() {
////        Double amount;
////        System.out.println("Please insert amount: ");
////        amount = scanner.nextDouble();
////        customer.transaction(amount);
////        if(amount>= 0) {
////            System.out.println("Amount of " + amount + " has been added.");
////        } else {
////            System.out.println("Amount of " + amount + " has been removed.");
////        }
////
////
////    }
//}
