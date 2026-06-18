package com.finVault.model;

import com.finVault.entity.BaseEntity;
import com.finVault.enums.TransactionStatus;
import com.finVault.enums.TransactionType;
import com.finVault.utils.IDGenerator;

import java.time.LocalDateTime;

public class Transaction extends BaseEntity {
    private final String transactionId;
    private final TransactionType transactionType;
    private final double amount;
    private final TransactionStatus transactionStatus;
    private final LocalDateTime transactionDateTime;
    private final String fromAccountNumber;
    private final String toAccountNumber;
    private final double balanceAfterTransaction;
    private final String transactionDescription;

    public Transaction(TransactionType transactionType,double amount,
                       String fromAccountNumber, String toAccountNumber, String createdBy,
                       double balanceAfterTransaction, TransactionStatus transactionStatus,
                       String transactionDescription) {
        LocalDateTime localDateTime=LocalDateTime.now();
        this.transactionId = IDGenerator.generateTransactionId();
        this.transactionType= transactionType;
        this.amount= amount;
        this.transactionStatus = transactionStatus;
        this.transactionDateTime=localDateTime;
        this.fromAccountNumber=fromAccountNumber;
        this.toAccountNumber=toAccountNumber;
        this.balanceAfterTransaction=balanceAfterTransaction;
        this.transactionDescription=transactionDescription;
        this.createdBy=createdBy;
        this.createdAt=localDateTime;
    }

    public String getTransactionId() { return transactionId; }
    public double getAmount() { return amount; }
    public String getFromAccountNumber() { return fromAccountNumber; }
    public String getToAccountNumber() { return toAccountNumber; }
    public TransactionType getTransactionType() { return transactionType; }
    public LocalDateTime getTransactionDateTime() { return transactionDateTime; }
    public double getBalanceAfterTransaction() { return balanceAfterTransaction; }
    public String getTransactionDescription() { return transactionDescription; }
    public TransactionStatus getTransactionStatus() { return transactionStatus; }
}
