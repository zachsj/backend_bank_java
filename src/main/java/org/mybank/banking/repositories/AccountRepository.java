package org.mybank.banking.repositories;

import org.mybank.banking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
//1st param type is type for entity, and 2nd param type for unique id
public interface AccountRepository extends JpaRepository<Account, Long> {
    //find all accts associated with a specific customer ID.
    List<Account> findByCustomerId(Long customerId);
    //used to find out if any Active or Suspended accounts before deleting.
    long countByCustomerIdAndStatus(Long customerId, String status);
    //@Query("SELECT COUNT(a) FROM Account a WHERE a.customer.id = :customerId AND a.status = :status")

    Account findByAcctNo(String acctNo);

    //@Query("SELECT a.balance FROM Account a WHERE a.acctNo = :acctNo")
    //BigDecimal findBalanceByAcctNo(String acctNo);

}