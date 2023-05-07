package com.bankapp.transactions;

import com.bankapp.account.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_transactions")
public class Transactions {

    @Id
    @GeneratedValue
    private Long id;
    private Double amount;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
    private LocalDateTime localTime = LocalDateTime.now();

}
