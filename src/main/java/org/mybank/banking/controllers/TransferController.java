package org.mybank.banking.controllers;

import org.mybank.banking.models.TransferRequest;
import org.mybank.banking.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {
    private final TransactionService transactionService;

    public TransferController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<String> transferFunds(@RequestBody TransferRequest transferRequest) {
        String message = transactionService.transferFunds(
                transferRequest.getSourceAcctNo(),
                transferRequest.getTargetAcctNo(),
                transferRequest.getAmount());
        return ResponseEntity.ok(message);
    }
}

