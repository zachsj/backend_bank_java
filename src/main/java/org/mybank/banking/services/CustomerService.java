package org.mybank.banking.services;

import org.mybank.banking.models.Customer;
import org.mybank.banking.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service //indicates the class is a service component, facilitates dependency injection
//Repository is injected to allow it to use it for database operations
public class CustomerService {
    private final CustomerRepository customerRepository; //final, ensures
    //will always refer to same instance passed into the constructor.

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    //save or update the provided customer instance to db. save if new customer, update existing.
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

}
