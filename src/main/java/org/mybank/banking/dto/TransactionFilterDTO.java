package org.mybank.banking.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

//DTO(data transfer object). Controller receives http request, Spring auto maps
// it into a TransactionFilterDTO object.
public class TransactionFilterDTO {

    private String accountNo;
    private String type;              // For filtering by transaction type (e.g., deposit, withdrawal)
    private LocalDate fromDate;
    private LocalDate toDate;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;

    // Getters and Setters

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }
}

