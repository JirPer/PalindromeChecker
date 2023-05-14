package com.bankapp.customer;

import com.bankapp.exceptions.CustomerNotFoundException;
import com.bankapp.exceptions.EmailExistsException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Transactional
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;


    public Optional<Customer> customerOptional(Integer id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(Integer id) {
        customerOptional(id).orElseThrow(() -> new CustomerNotFoundException("customer does not exist:" + id));
        return customerOptional(id).get();
    }

    public Customer addNewCustomer(Customer customer) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(customer.getEmail());
        if(customerOptional.isPresent()) {
            throw new EmailExistsException("email:" + customer.getEmail() + " is already present");
        }
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Integer id) {
        customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("customer does not exist:" + id));
        customerRepository.deleteById(id);
    }

    public void editCustomer(Integer id, String firstName, String lastName, String email) {
        customerOptional(id).orElseThrow(() -> new CustomerNotFoundException("customer does not exist:" + id));
        if(firstName != null && !Objects.equals(customerOptional(id).get().getFirstName(), firstName)) {
            customerOptional(id).get().setFirstName(firstName);
        }
        if(lastName != null && !Objects.equals(customerOptional(id).get().getLastName(), lastName)) {
            customerOptional(id).get().setLastName(lastName);
        }
        if(email != null && !Objects.equals(customerOptional(id).get().getEmail(), email)) {
            customerOptional(id).get().setEmail(email);
        }
    }
}
