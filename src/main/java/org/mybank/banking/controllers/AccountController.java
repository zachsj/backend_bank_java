package org.mybank.banking.controllers;

import org.mybank.banking.models.Account;
import org.mybank.banking.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")

public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping//ResponseEntity is entire HTTP response, @RequestBody indicates
    //the account param should be bound to body of http request
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account savedAccount = accountService.save(account); //calls service method
        //to save Account object to db.
        return ResponseEntity.ok(savedAccount); //creates response with 200 OK status
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() { //returns ResponseEntity
        //containing a list of account objects
        List<Account> accounts = accountService.findAll();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Optional<Account> account = accountService.findById(id);
        return account.map(ResponseEntity::ok).
                orElseGet(() -> ResponseEntity.notFound().build()); //if Optional has no value
        //should return a 404 Not Found status. .build() finalizes the ResponseEntity
        //config & produces actual ResponseEntity object to be returned
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build(); //returns 204 status, indicates
        // success, but no content to return in response body
    }
}
