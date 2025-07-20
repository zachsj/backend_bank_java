package org.mybank.banking.repositories;

import org.mybank.banking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
 //1st param type is type for entity, and 2nd param type for unique id
        List<Account> findByCustomerId(Long customerId);

@Query("SELECT COUNT(a) FROM Account a WHERE a.customer.id = :customerId AND a.status = :status")
        long countByCustomerIdAndStatus(Long customerId, String status);

    Account findByAcctNo(String acctNo);

    @Query("SELECT a.balance FROM Account a WHERE a.acctNo = :acctNo")
    BigDecimal findBalanceByAcctNo(String acctNo);

    /*@Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Account a SET a.balance = a.balance + ?2 WHERE a.acctNo = ?1")
    void increaseBalance(String acctNo, BigDecimal amount);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Account a SET a.balance = a.balance - ?2 WHERE a.acctNo = ?1")
    void decreaseBalance(String acctNo, BigDecimal amount);*/


}