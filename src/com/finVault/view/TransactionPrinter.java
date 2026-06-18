package com.finVault.view;

import com.finVault.model.Account;
import com.finVault.model.Transaction;

public class TransactionPrinter {
    public static void printStatement(Account account) {
        System.out.println("=== Transaction History ===");
        if (account.getTransactionHistory().isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            account.getTransactionHistory().forEach(TransactionPrinter::printTransaction);
        }
    }

    public static void printTransaction(Transaction transaction) {

        System.out.println("--------------------------------");
        System.out.println("Transaction ID : " + transaction.getTransactionId());
        System.out.println("Date           : " + transaction.getTransactionDateTime());
        System.out.println("Type           : " + transaction.getTransactionType());
        System.out.println("Amount         : " + transaction.getAmount());
        System.out.println("From Account   : " + transaction.getFromAccountNumber());
        System.out.println("To Account     : " + transaction.getToAccountNumber());
        System.out.println("Status         : " + transaction.getTransactionStatus());
        System.out.println("Balance After  : " + transaction.getBalanceAfterTransaction());
        System.out.println("Description    : " + transaction.getTransactionDescription());
    }
}
