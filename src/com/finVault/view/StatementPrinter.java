package com.finVault.view;

import com.finVault.model.Transaction;

import java.util.List;

public class StatementPrinter {

    public static void printStatement(List<Transaction> transactions) {

        if(transactions.isEmpty()) {
            System.out.println("No transactions found");
            return;
        }

        transactions.forEach(TransactionPrinter::printTransaction);
    }
}