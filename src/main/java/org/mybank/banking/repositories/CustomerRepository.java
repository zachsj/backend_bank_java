package org.mybank.banking.repositories;

import org.mybank.banking.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


//1st param type is type for entity, and 2nd param type for unique id
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //findByEmail and Phone are not provided in Jpa, so needed as custom query here.
    Customer findByEmail(String email);
    Customer findByPhone(String phone);
}
