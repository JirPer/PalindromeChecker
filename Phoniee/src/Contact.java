import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class Contact {

    private String name;
    private String phoneNumber;
    private Map<String, String> contacts = new HashMap<>();

    public Contact() {
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean addContact(String name, String phoneNumber) {
        if(contacts.containsKey(name)) {
            System.out.println("Contact already exists. Contact not added! Create contact with different name.");
            return false;
        } else {
            contacts.put(name, phoneNumber);
            System.out.println("Contact " + name + " " + phoneNumber + " has been added.");
            return true;
        }
    }
    public boolean removeContact(String name) {
        if(contacts.containsKey(name.toLowerCase())) {
            contacts.remove(name);
            System.out.println("Contact " + name + " has been removed.");
            return true;
        } else {
            System.out.println("Couldn't find the contact " + name + " no contact was removed.");
            return false;
        }
    }
    public boolean replaceContact(String name, String phoneNumber) {
        if(contacts.containsKey(name.toLowerCase())) {
            contacts.replace(name.toLowerCase(), phoneNumber);
            System.out.println("Number of contact " + name + " has been replaced.");
            return true;
        } else {
            System.out.println("Couldn't find contact " + name);
            return false;
        }
    }
    public void displayContacts() {
        System.out.println(contacts.entrySet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(name, contact.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
