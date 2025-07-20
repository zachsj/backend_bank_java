package org.mybank.banking.models;

import jakarta.persistence.*; //allows to use various JPA annotations & interfaces
// enabling to define entities and perform database operations.
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String acctNo;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String transactionType;

    @ManyToOne // multiple trans associates w/single acct
    @JoinColumn(name = "account_id", nullable = false) //specifies the foreign key
    //column in Transaction table that refers to Account table. So can fetch account
    //associated w any trans and vice versa.
    private Account account;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    // Method to set timestamp during transaction creation
    public void initializeTimestamp() {
        this.timestamp = LocalDateTime.now();
    }
}
