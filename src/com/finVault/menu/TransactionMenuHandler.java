package com.finVault.menu;

import com.finVault.exception.FinVaultException;
import com.finVault.repository.AccountRepository;
import com.finVault.repository.TransactionRepository;
import com.finVault.service.AccountService;
import com.finVault.service.TransactionService;

import java.math.BigDecimal;
import java.util.Scanner;


public class TransactionMenuHandler {
    private final Scanner sc;
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    public TransactionMenuHandler(TransactionRepository transactionRepository, TransactionService transactionService, AccountRepository accountRepository, AccountService accountService, Scanner sc) {
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.sc = sc;
    }
    public final void handleTransactionMenu(){
        System.out.println("\n--- Transactions ---");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Transfer");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        try {
            switch (choice) {
                case 1 -> deposit();
                case 2 -> withdraw();
                case 3 -> transfer();
                default -> System.out.println("Invalid choice.");
            }
        } catch (FinVaultException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public final void deposit(){
        System.out.println("Enter Account Number: ");
        String accountNumber = sc.nextLine();
        System.out.println("Enter Amount to Deposit: ");
        BigDecimal amount = sc.nextBigDecimal();
        sc.nextLine();
        transactionService.deposit(accountNumber,amount);
    }

    public final void withdraw(){
        System.out.println("Enter Account Number: ");
        String accountNumber = sc.nextLine();
        System.out.println("Enter Amount to Withdraw: ");
        BigDecimal amount = sc.nextBigDecimal();
        sc.nextLine();
        transactionService.withdraw(accountNumber,amount);
    }

    public final void transfer(){
        System.out.println("Enter From Account Number: ");
        String fromAccountNumber = sc.nextLine();
        System.out.println("Enter To Account Number: ");
        String toAccountNumber = sc.nextLine();
        System.out.println("Enter Amount to Transfer: ");
        BigDecimal amount = sc.nextBigDecimal();
        sc.nextLine();
        transactionService.transfer(fromAccountNumber,toAccountNumber,amount);
    }
}
