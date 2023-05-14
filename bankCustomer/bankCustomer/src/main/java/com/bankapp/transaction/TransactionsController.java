package com.bankapp.transaction;

import com.bankapp.account.AccountRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/customers")
@RestController
public class TransactionsController {

    private final AccountRepository accountRepository;
    private final TransactionsRepository transactionsRepository;
    private final TransactionsService transactionsService;

    public TransactionsController(AccountRepository accountRepository, TransactionsRepository transactionsRepository, TransactionsService transactionsService) {
        this.accountRepository = accountRepository;
        this.transactionsRepository = transactionsRepository;
        this.transactionsService = transactionsService;
    }

    @GetMapping("/{cid}/accounts/{aid}")
    public List<Transactions> getAccountTransactions(@PathVariable Integer cid, @PathVariable Integer aid) {
       return transactionsService.getAllAccountTransactions(cid, aid);
    }

    @PostMapping("/{cid}/accounts/{aid}/transactions")
    public ResponseEntity<Transactions> createTransaction(@PathVariable Integer cid,
                                                          @PathVariable Integer aid,
                                                          @Valid @RequestBody Transactions transactions) {

        Transactions savedTransactions = transactionsService.crateTransaction(cid,aid, transactions);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTransactions.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/{cid}/accounts/{aid}/transactions/{tid}")
    public void deleteTransaction(@PathVariable Integer cid,
                                  @PathVariable Integer aid,
                                  @PathVariable Long tid) {
    transactionsService.deleteTransactionById(cid, aid, tid);
    }

}
