package org.mybank.banking.services;

import jakarta.transaction.Transactional;
import org.mybank.banking.dto.TransactionFilterDTO;
import org.mybank.banking.models.Account;
import org.mybank.banking.models.Transaction;
import org.mybank.banking.repositories.AccountRepository;
import org.mybank.banking.repositories.TransactionRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service //indicates the class is a service component, facilitates dependency injection
//Repository is injected to allow it to use it for database operations
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public Transaction save(Transaction transaction) {
        transaction.initializeTimestamp(); //Set timestamp to now
        return transactionRepository.save(transaction);
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }

    @Transactional
    public void recordTransaction(String acctNo, BigDecimal amount, String transactionType) {
        Account account = accountRepository.findByAcctNo(acctNo);
        if (account == null) {
            throw new IllegalStateException("Account not found");
        }

        if ("WITHDRAWAL".equals(transactionType)) {
            if (amount.compareTo(account.getBalance()) > 0) {
                throw new IllegalArgumentException("Insufficient funds for withdrawal");
            }
            account.setBalance(account.getBalance().subtract(amount));

        } else if ("DEPOSIT".equals(transactionType)) {
            account.setBalance(account.getBalance().add(amount));

        } else {
            throw new IllegalArgumentException("Invalid transaction type");
        }

        // Save the updated account balance
        accountRepository.save(account);

        // Create and save the transaction
        Transaction transaction = new Transaction();
        transaction.setAcctNo(acctNo);
        transaction.setAmount(amount);
        transaction.setTransactionType(transactionType);
        transaction.initializeTimestamp();
        transaction.setAccount(account); // Link the existing account
        transactionRepository.save(transaction); // Save the transaction
    }
    //DTO(data transfer object). Controller receives http request, Spring auto maps it into a TransactionFilterDTO object.
    //Transaction is the entity type or data to filter, TransactionFilterDTO contains the filter criteria.
    public List<Transaction> filterTransactions(TransactionFilterDTO filter) {
        Specification<Transaction> spec = (root, query, criteriaBuilder) ->
                criteriaBuilder.conjunction();

        // Add specifications based on the filter DTO
        if (filter.getAccountNo() != null) {
            spec = spec.and(TransactionSpecifications.hasAccountNo(filter.getAccountNo()));
        }
        if (filter.getType() != null) {
            spec = spec.and(TransactionSpecifications.hasType(filter.getType()));
        }
        if (filter.getFromDate() != null || filter.getToDate() != null) {
            spec = spec.and(TransactionSpecifications.isBetweenDates(filter.getFromDate(), filter.getToDate()));
        }
        if (filter.getMinAmount() != null) {
            spec = spec.and(TransactionSpecifications.hasMinAmount(filter.getMinAmount()));
        }
        if (filter.getMaxAmount() != null) {
            spec = spec.and(TransactionSpecifications.hasMaxAmount(filter.getMaxAmount()));
        }

        return transactionRepository.findAll(spec);
    }



    // transfer funds between accounts
    @Transactional
    public void transferFunds(String sourceAcctNo, String targetAcctNo, BigDecimal amount) {
        // Fetch both accounts
        Account sourceAccount = accountRepository.findByAcctNo(sourceAcctNo);
        Account targetAccount = accountRepository.findByAcctNo(targetAcctNo);

        if (sourceAccount == null || targetAccount == null) {
            throw new IllegalStateException("One or both accounts not found");
        }

        // Check sufficient funds
        if (amount.compareTo(sourceAccount.getBalance()) > 0) {
            throw new IllegalArgumentException("Insufficient funds for transfer");
        }

        // Adjust balances
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
        targetAccount.setBalance(targetAccount.getBalance().add(amount));

        // Save both accounts
        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        // Record the transactions
        Transaction outTransaction = new Transaction();
        outTransaction.setAcctNo(sourceAcctNo);
        outTransaction.setAmount(amount);
        outTransaction.setTransactionType("TRANSFER_OUT");
        outTransaction.setAccount(sourceAccount);
        outTransaction.initializeTimestamp();

        Transaction inTransaction = new Transaction();
        inTransaction.setAcctNo(targetAcctNo);
        inTransaction.setAmount(amount);
        inTransaction.setTransactionType("TRANSFER_IN");
        inTransaction.setAccount(targetAccount);
        inTransaction.initializeTimestamp();

        transactionRepository.save(outTransaction);
        transactionRepository.save(inTransaction);
    }

}
