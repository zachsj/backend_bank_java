package org.mybank.banking.repositories;

import org.mybank.banking.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
} //1st param type is type for entity, and 2nd param type for unique id
