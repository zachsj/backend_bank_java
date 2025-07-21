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

    @Column(unique = true, nullable = false)
    private String acctNo;

    public static final String CHECKING = "CHECKING";
    public static final String SAVINGS = "SAVINGS";

    public static final String ACTIVE = "ACTIVE";
    public static final String CLOSED = "CLOSED";
    public static final String SUSPENDED = "SUSPENDED";

    private String accountType;
    private BigDecimal balance;
    private String status;

    @ManyToOne // multiple accounts for single customer
    @JoinColumn(name = "customer_id", nullable = false) //specifies the foreign key
    //column in Account table that refers to Customer table. So can fetch customer
    //associated w any acct and vice versa.
    private Customer customer;

    //No arg Constructor for Jpa handling deposits/withdrawals
    public Account() {
    }

    //Constructor for creating new acct
    public Account(String accountType, BigDecimal initialBalance) {
        setAccountType(accountType);
        this.balance = initialBalance; //see line 36 AccountController.
        this.acctNo = generateAccountNumber();
        this.status = ACTIVE;
    }

    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis();
    }

    // Getters and Setters
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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        if (!accountType.equals(CHECKING) && !accountType.equals(SAVINGS)) {
            throw new IllegalArgumentException("Invalid account type. Must be CHECKING or SAVINGS");
        }
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (!status.equals(ACTIVE) && !status.equals(CLOSED) && !status.equals(SUSPENDED)) {
            throw new IllegalArgumentException("Invalid status. Must be ACTIVE or CLOSED or SUSPENDED");
        }
        this.status = status;
    }
}
