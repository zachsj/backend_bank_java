package org.mybank.banking.services;

import org.mybank.banking.models.Account;
import org.mybank.banking.models.Customer;
import org.mybank.banking.repositories.AccountRepository;
import org.mybank.banking.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service //indicates the class is a service component, facilitates dependency injection
//Repository is injected to allow it to use it for database operations
public class CustomerService {
    private final CustomerRepository customerRepository; //final, ensures
    private final AccountRepository accountRepository;
    //will always refer to same instance passed into the constructor.

    public CustomerService(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
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
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found");
        }

        if (hasOpenAccounts(id)) {
            throw new IllegalArgumentException("Cannot delete customer with open accounts");
        }
        customerRepository.deleteById(id);
    }

    public boolean hasOpenAccounts(Long id) {
        return accountRepository.countByCustomerIdAndStatus(id, Account.ACTIVE) > 0 ||
                accountRepository.countByCustomerIdAndStatus(id, Account.SUSPENDED) > 0;
    }

    public List<Account> findAccountsByCustomerId(Long customerId) {
        return accountRepository.findByCustomerId(customerId);
    }

}
