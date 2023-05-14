package com.bankapp.account;

import com.bankapp.customer.CustomerRepository;
import com.bankapp.exceptions.CustomerNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/customers")
@RestController
public class AccountController {

    private CustomerRepository customerRepository;
    private AccountService accountService;

    public AccountController(CustomerRepository customerRepository, AccountService accountService) {
        this.customerRepository = customerRepository;
        this.accountService = accountService;
    }

    @GetMapping("/accounts")
    public List<Account> allAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}/accounts")
    public List<Account> customerAccounts (@PathVariable Integer id) {
        return accountService.getAllCustomerAccounts(id);
    }

    @PostMapping("/{id}/accounts")
    public ResponseEntity<Account> createCustomerAccount(@PathVariable Integer id,
                                                         @Valid @RequestBody Account account) {
        Account savedAccount = accountService.createCustomerAccount(id, account);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedAccount.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/{id}/accounts/{aid}")
    public void deleteAccountById(@PathVariable Integer id,
                                  @PathVariable Integer aid) {
        customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("id:" + id));
        accountService.deleteAccount(aid);
    }

    @PutMapping("{id}/accounts/{aid}")
    public void editAccountName(@PathVariable Integer id,
                                @PathVariable Integer aid,
                                @RequestParam String accountName) {
        accountService.editAccount(id, aid, accountName);

    }


}
