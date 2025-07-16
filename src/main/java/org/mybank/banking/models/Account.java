package org.mybank.banking.models;

import jakarta.persistence.*; //allows to use various JPA annotations & interfaces
// enabling to define entities and perform database operations.
import java.math.BigDecimal;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //private for encapsulation, restrict direct access from
    //outside the class. Allows controlled access thru getters/setters.

    private String accountNumber;
    private String accountType;
    private BigDecimal balance;

    @ManyToOne // multiple accounts for single customer
    @JoinColumn(name = "customer_id", nullable = false) //specifies the foreign key
    //column in Account table that refers to Customer table. So can fetch customer
    //associated w any acct and vice versa.
    private Customer customer;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
