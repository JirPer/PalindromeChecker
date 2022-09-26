
import java.util.Scanner;


class Phone {

    Scanner scanner = new Scanner(System.in);
    private String phoneName;
    private Contact contatcs = new Contact();

    public Phone(String phoneName) {
        this.phoneName = phoneName;
    }

    public void runPhone() {
        boolean off = false;
        int choice = 0;
        System.out.println("Turning on the phone. Please select action below: ");
        displayChoices();

        while(!off) {

            System.out.println("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayChoices();
                    break;
                case 2:
                    addContact();
                    break;
                case 3:
                    removeContact();
                    break;
                case 4:
                    replaceContact();
                    break;
                case 5:
                    displayContact();
                    break;
                case 6:
                    System.out.println("shutting down...");
                    off = true;
                    break;
            }
        }
    }

    public void displayChoices() {
        System.out.println("\t Enter: \n " +
                "1 - to display choices \n" +
                "2 - to add contact \n" +
                "3 - to remove contact \n" +
                "4 - to replace contact \n" +
                "5 - to display contacts \n" +
                "6 - to turn off phone");
    }
    public void addContact() {
        String name;
        String phoneNumber;
        System.out.println("Enter contact name: ");
        name = scanner.nextLine().toLowerCase();
        System.out.println("Enter " + name + "'s phone number: ");
        phoneNumber = scanner.nextLine();
        contatcs.addContact(name, phoneNumber);
    }
    public void removeContact() {
        String name;
        System.out.println("Enter name of contact you want to remove: ");
        name = scanner.nextLine().toLowerCase();
        contatcs.removeContact(name);
    }
    public void replaceContact() {
        String name;
        String phoneNumber;
        System.out.println("Enter name of contact you want to replace: ");
        name = scanner.nextLine().toLowerCase();
        System.out.println("Enter phone number of contact " + name + " : ");
        phoneNumber = scanner.nextLine();
        contatcs.replaceContact(name, phoneNumber);
    }
    public void displayContact() {
        contatcs.displayContacts();
    }
}
