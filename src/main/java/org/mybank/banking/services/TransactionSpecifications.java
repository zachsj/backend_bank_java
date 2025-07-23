package org.mybank.banking.services;

import org.mybank.banking.models.Transaction;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionSpecifications {

    //Specification is an interface that allows to define criteria queries for the Entity type
    public static Specification<Transaction> hasAccountNo(String accountNo) {
        return (root, query, criteriaBuilder) ->
                //conjunction means adds no restrictions to query.
                accountNo == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("acctNo"), accountNo);
    }

    public static Specification<Transaction> hasType(String type) {
        return (root, query, criteriaBuilder) ->
                type == null ?
                        criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(criteriaBuilder.lower(root.get("transactionType")), type.toLowerCase()); // Map to transactionType
    }

    public static Specification<Transaction> isBetweenDates(LocalDate fromDate, LocalDate toDate) {
        return (root, query, criteriaBuilder) -> {
            if (fromDate == null && toDate == null) {
                return criteriaBuilder.conjunction();
            } else if (fromDate != null && toDate != null) {
                return criteriaBuilder.between(root.get("timestamp"), fromDate.atStartOfDay(), toDate.plusDays(1).atStartOfDay());
            } else if (fromDate != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("timestamp"), fromDate.atStartOfDay());
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get("timestamp"), toDate.plusDays(1).atStartOfDay());
            }
        };
    }

    public static Specification<Transaction> hasMinAmount(BigDecimal minAmount) {
        return (root, query, criteriaBuilder) ->
                minAmount == null ? criteriaBuilder.conjunction() : criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), minAmount);
    }

    public static Specification<Transaction> hasMaxAmount(BigDecimal maxAmount) {
        return (root, query, criteriaBuilder) ->
                maxAmount == null ? criteriaBuilder.conjunction() : criteriaBuilder.lessThanOrEqualTo(root.get("amount"), maxAmount);
    }
}

