package org.mybank.banking.repositories;

import org.mybank.banking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
} //1st param type is type for entity, and 2nd param type unique id
