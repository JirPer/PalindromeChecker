package com.bankapp.account;

import com.bankapp.customer.Customer;
import com.bankapp.customer.CustomerRepository;
import com.bankapp.exceptions.AccountNotFoundException;
import com.bankapp.exceptions.CustomerNotFoundException;
import com.bankapp.exceptions.NameExistsException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Transactional
@AllArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public Optional<Customer> customerOptional(Integer cid) {
        return customerRepository.findById(cid);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    public List<Account> getAllCustomerAccounts(Integer id) {
        customerOptional(id).orElseThrow(() -> new CustomerNotFoundException("customer id:" + id));
        return customerOptional(id).get().getAccount();
    }

    public Account createCustomerAccount(Integer id, Account account) {
        customerOptional(id).orElseThrow(() -> new CustomerNotFoundException("customer id:" + id));
        Optional<Account> accountOptional = accountRepository.findByAccountName(account.getAccountName());
        if(accountOptional.isPresent()) {
            throw new NameExistsException("name: " + account.getAccountName() + " already exists");
        }
        account.setCustomer(customerOptional(id).get());
        return accountRepository.save(account);
    }
    public void deleteAccount(Integer id) {
        accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("id:" + id));
        accountRepository.deleteById(id);
    }

    public void editAccount(Integer id, Integer aid, String accountName) {
        customerOptional(id).orElseThrow(() -> new CustomerNotFoundException("customer id:" + id));
        Optional<Account> account = accountRepository.findById(aid);
            if (account.isEmpty()) {
                throw new AccountNotFoundException("account id does not exist:" + aid);
            }
            List<Account> customerAccounts = getAllCustomerAccounts(id);
                if (!customerAccounts.contains(account.get())) {
                throw new AccountNotFoundException("account id belongs to other customer:" + aid);
                }
            account.get().setCustomer(customerOptional(id).get());
                if ((accountName != null && !Objects.equals(accountName, account.get().getAccountName()))) {
                account.get().setAccountName(accountName);
                }
    }

}

