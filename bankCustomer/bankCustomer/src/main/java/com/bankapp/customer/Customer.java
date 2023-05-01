package com.bankapp.customer;

import com.bankapp.account.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Entity
@Table(name = "bank_customer")
public class Customer {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;
    @Size(min = 2)
    @JsonProperty("first_name")
    private String firstName;
    @Size(min = 2)
    @JsonProperty("last_name")
    private String lastName;
    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Account> account;
    @NotNull
    @Pattern(regexp = "^(.+)@(\\S+)$")
    private String email;
    private Double balance;

    public void editBalance(Double amount) {
        this.balance += amount;
    }
}
