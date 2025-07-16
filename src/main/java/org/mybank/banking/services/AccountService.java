package org.mybank.banking.services;

import org.mybank.banking.models.Account;
import org.mybank.banking.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service //indicates the class is a service component, facilitates dependency injection
public class AccountService {
    private final AccountRepository accountRepository; //final, ensures
    //will always refer to same instance passed into the constructor.

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    public void delete(Account account) {
        accountRepository.delete(account);
    }
}
