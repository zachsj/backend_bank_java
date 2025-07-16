package org.mybank.banking.controllers;

import org.mybank.banking.models.Customer;
import org.mybank.banking.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build(); //returns 204 status, indicates
        // success, but no content to return in response body
    }
}
