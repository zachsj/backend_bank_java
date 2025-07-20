package org.mybank.banking.services;

import org.mybank.banking.models.Account;
import org.mybank.banking.models.Customer;
import org.mybank.banking.repositories.AccountRepository;
import org.mybank.banking.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service //indicates the class is a service component, facilitates dependency injection
//Repository is injected to allow it to use it for database operations
public class AccountService {
    private final AccountRepository accountRepository; //final, ensures
    //will always refer to same instance passed into the constructor.
    private final CustomerRepository customerRepository;

    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    public Account save(Account account) {
        //Check if customer exists
        Optional<Customer> customer =
                customerRepository.findById(account.getCustomer().getId());
        if (customer.isPresent()) {
            return accountRepository.save(account);
        } else {
            throw new RuntimeException("Customer not found");
        }
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> findByAccountNumber(String acctNo) {
        return Optional.ofNullable(accountRepository.findByAcctNo(acctNo));
    }

    public void delete(String acctNo) {
        Account account = findByAccountNumber(acctNo)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException("Cannot delete account with non-zero balance");
        }
        accountRepository.delete(account);
    }

    public Account updateAccountStatus(String acctNo, String newStatus) {
        Account account = findByAccountNumber(acctNo)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Validate the new status
        if (!newStatus.equals(Account.ACTIVE) &&
                !newStatus.equals(Account.CLOSED) &&
                !newStatus.equals(Account.SUSPENDED)) {
            throw new IllegalArgumentException("Invalid status");
        }

        account.setStatus(newStatus);
        return save(account);
    }

}
