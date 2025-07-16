package org.mybank.banking.controllers;

import org.mybank.banking.models.Transaction;
import org.mybank.banking.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")

public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping//ResponseEntity is entire HTTP response, @RequestBody indicates
    //the transaction param should be bound to body of http request
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction
    transaction) {
        Transaction savedTransaction = transactionService.save(transaction);//calls service method
        //to save Transaction object to db.
        return ResponseEntity.ok(savedTransaction); //creates response with 200 OK status
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() { //returns ResponseEntity
        //containing a list of transaction objects
        List<Transaction> transactions = transactionService.findAll();
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
