package org.mybank.banking.controllers;

import org.mybank.banking.dto.TransactionFilterDTO;
import org.mybank.banking.exceptions.AccountNotFoundException;
import org.mybank.banking.models.Transaction;
import org.mybank.banking.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.mybank.banking.exceptions.AccountNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")

public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping//ResponseEntity is entire HTTP response, @RequestBody indicates
    //the transaction param should be bound to body of http request
    public ResponseEntity<?> createTransaction(@RequestBody Transaction
    transaction) {
        try {
        transactionService.recordTransaction( //calls the service file method
                transaction.getAcctNo(),
                transaction.getAmount(),
                transaction.getTransactionType()
        );
        return ResponseEntity.ok(transaction);
    } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions( //returns ResponseEntity
        //containing a list of transaction objects
        @ModelAttribute TransactionFilterDTO filter) //DTO is the model the Spring binds request params to.
        {
     List<Transaction> transactions = transactionService.filterTransactions(filter);
            return ResponseEntity.ok(transactions);
        }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionService.findById(id);
        return transaction.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()); //if Optional has no value
        //should return a 404 Not Found status. .build() finalizes the ResponseEntity
        //config & produces actual ResponseEntity object to be returned
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build(); //returns 204 status, indicates
        // success, but no content to return in response body
    }
}
