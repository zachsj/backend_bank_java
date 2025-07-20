package org.mybank.banking.models;

import java.math.BigDecimal;

public class TransferRequest {
    private String sourceAcctNo;
    private String targetAcctNo;
    private BigDecimal amount;

    // Getters and Setters
    public String getSourceAcctNo() {
        return sourceAcctNo;
    }

    public void setSourceAcctNo(String sourceAcctNo) {
        this.sourceAcctNo = sourceAcctNo;
    }

    public String getTargetAcctNo() {
        return targetAcctNo;
    }

    public void setTargetAcctNo(String targetAcctNo) {
        this.targetAcctNo = targetAcctNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
