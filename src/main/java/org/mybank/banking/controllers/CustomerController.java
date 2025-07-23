package org.mybank.banking.controllers;

import org.mybank.banking.models.Account;
import org.mybank.banking.models.Customer;
import org.mybank.banking.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    //constructor
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping //ResponseEntity is entire HTTP response, @RequestBody indicates
    //the customer param should be bound to body of http request
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.save(customer); //calls service method
        //to save Customer object to db.
        return ResponseEntity.ok(savedCustomer); //creates response with 200 OK status
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        Optional<Customer> existingCustomerOpt = customerService.findById(id);

        if (existingCustomerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Customer existingCustomer = existingCustomerOpt.get();
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhone(customer.getPhone());
        Customer updatedCustomer = customerService.save(existingCustomer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() { //returns ResponseEntity
        //containing a list of customer objects
        List<Customer> customers = customerService.findAll();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        return customer.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); //if Optional has no value
                //should return a 404 Not Found status. .build() finalizes the ResponseEntity
                //config & produces actual ResponseEntity object to be returned
    }

    @GetMapping("/{id}/accounts")
    public ResponseEntity<List<Account>> getAccountsForCustomer(@PathVariable Long id) {
        List<Account> accounts = customerService.findAccountsByCustomerId(id);
        if (accounts.isEmpty()) {
            return ResponseEntity.notFound().build(); //204 no content
        }
        return ResponseEntity.ok(accounts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.delete(id);
            return ResponseEntity.noContent().build(); //204
        }  catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); //400
        }  catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
