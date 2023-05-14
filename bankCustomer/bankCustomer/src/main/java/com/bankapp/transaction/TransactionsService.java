package com.bankapp.transaction;

import com.bankapp.account.Account;
import com.bankapp.account.AccountRepository;
import com.bankapp.customer.Customer;
import com.bankapp.customer.CustomerRepository;
import com.bankapp.exceptions.AccountNotFoundException;
import com.bankapp.exceptions.CustomerNotFoundException;
import com.bankapp.exceptions.TransactionNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class TransactionsService {

    private final TransactionsRepository transactionsRepository;
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;


    public Optional<Customer> customerOptional(Integer cid) {
        return customerRepository.findById(cid);
    }
    public Optional<Account> accountOptional(Integer aid) {
        return accountRepository.findById(aid);
    }

    public List<Transactions> getAllAccountTransactions(Integer cid, Integer aid) {
        customerOptional(cid).orElseThrow(() -> new CustomerNotFoundException("customer id:" + cid));
        accountOptional(aid).orElseThrow(() -> new AccountNotFoundException("account does not exist id:" + aid));

        if(!Objects.equals(customerOptional(cid).get(),accountOptional(aid).get().getCustomer())) {
            throw new AccountNotFoundException("customer does not have account id:" + aid);
        }
        return accountOptional(aid).get().getTransfers();
    }

    public Transactions crateTransaction(Integer cid, Integer aid, Transactions transactions) {
        customerOptional(cid).orElseThrow(() -> new CustomerNotFoundException("customer id:" + cid));
        accountOptional(aid).orElseThrow(() -> new AccountNotFoundException("account does not exist id:" + aid));

        if(!Objects.equals(customerOptional(cid).get(),accountOptional(aid).get().getCustomer())) {
            throw new AccountNotFoundException("customer does not have account id:" + aid);
        }

        transactions.setAccount(accountOptional(aid).get());
        customerOptional(cid).get().editBalance(transactions.getAmount());
        return transactionsRepository.save(transactions);
    }

    public void deleteTransactionById(Integer cid, Integer aid, Long tid) {
        customerOptional(cid).orElseThrow(() -> new CustomerNotFoundException("customer id:" + cid));
        accountOptional(aid).orElseThrow(() -> new AccountNotFoundException("account does not exist id:" + aid));
        Optional<Transactions> transaction = transactionsRepository.findById(tid);
        if(transaction.isEmpty()) {
            throw new TransactionNotFoundException("transaction id:" + tid);
        }
        customerOptional(cid).get().editBalance(-transaction.get().getAmount());
        transactionsRepository.deleteById(tid);
    }
}
