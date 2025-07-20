package org.mybank.banking.controllers;

import org.mybank.banking.models.Account;
import org.mybank.banking.models.Customer;
import org.mybank.banking.services.AccountService;
import org.mybank.banking.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")

public class AccountController {
    private final AccountService accountService;
    private final CustomerService customerService;

    public AccountController(AccountService accountService, CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    @PostMapping//ResponseEntity is entire HTTP response, @RequestBody indicates
    //the account param should be bound to body of http request
    public ResponseEntity<Account> createAccount(@RequestBody AccountRequest accountRequest) {
        //validate account type
        if (!accountRequest.getAccountType().equals(Account.CHECKING) &&
            !accountRequest.getAccountType().equals(Account.SAVINGS)) {
            return ResponseEntity.badRequest().body(null); //Return 400 bad request
        }
        //Create an account based on the request
        Account account = new Account(
                accountRequest.getAccountType(),
                accountRequest.getInitialDeposit() //this value is sent to
                //initialBalance in Account.java.
        );

        //Find customer by ID
        Customer customer = customerService.findById(accountRequest.getCustomerId()).
            orElseThrow(() -> new RuntimeException("Customer not found"));
        account.setCustomer(customer);

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

    @GetMapping("/{acctNo}")
    public ResponseEntity<Account> getAccountByAcctNo(@PathVariable String acctNo) {
        Optional<Account> account = accountService.findByAccountNumber(acctNo);
        return account.map(ResponseEntity::ok).
                orElseGet(() -> ResponseEntity.notFound().build()); //if Optional has no value
        //should return a 404 Not Found status. .build() finalizes the ResponseEntity
        //config & produces actual ResponseEntity object to be returned
    }

    @DeleteMapping("/{acctNo}")
    public ResponseEntity<String> deleteAccount(@PathVariable String acctNo) {
        try {
            accountService.delete(acctNo);
            return ResponseEntity.noContent().build(); //returns 204 status, indicates
            // success, but no content to return in response body
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // return 400 bad request
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); // 404 not found
        }
    }

    @PatchMapping("/{acctNo}/status") //used for partial updates w/o modifying other attributes
    public ResponseEntity<Account> updateAccountStatus(@PathVariable String acctNo, @RequestBody String newStatus) {
        Account updatedAccount = accountService.updateAccountStatus(acctNo, newStatus);
        return ResponseEntity.ok(updatedAccount);
    }


}
