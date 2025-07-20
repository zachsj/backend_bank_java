package org.mybank.banking.repositories;

import org.mybank.banking.dto.TransactionFilterDTO;
import org.mybank.banking.models.Transaction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import java.util.List;

//1st param type is type for entity, and 2nd param type unique id
public interface TransactionRepository extends JpaRepository<Transaction, Long>,
        JpaSpecificationExecutor<Transaction> {
    //List<Transaction> findByAcctNo(String acctNo);
    //List<Transaction> findTransactions(TransactionFilterDTO filter);
}
