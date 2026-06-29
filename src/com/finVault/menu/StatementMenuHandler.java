package com.finVault.menu;

import com.finVault.exception.FinVaultException;
import com.finVault.model.Transaction;
import com.finVault.repository.AccountRepository;
import com.finVault.service.AccountService;
import com.finVault.service.StatementService;
import com.finVault.view.StatementPrinter;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static com.finVault.utils.ConsoleUtils.readInt;

public class StatementMenuHandler {
    private final Scanner sc;
    private final StatementService statementService;
    public StatementMenuHandler(StatementService statementService, Scanner sc) {
        this.statementService = statementService;
        this.sc = sc;
    }
    public void handleStatementMenu(){
        System.out.println("\n--- Statements ---");
        System.out.println("1. Mini Statement (last 10)");
        System.out.println("2. Full Statement");
        System.out.println("3. Statement by Date Range");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        try {
            switch (choice) {
                case 1 -> miniStatement();
                case 2 -> fullStatement();
                case 3 -> statementByDate();
                default -> System.out.println("Invalid choice.");
            }
        } catch (FinVaultException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void miniStatement(){
        System.out.println("Enter Account Number: ");
        String accountNumber = sc.nextLine();
        List<Transaction> miniStatements = statementService.getMiniStatement(accountNumber);
        StatementPrinter.printStatement(miniStatements);
    }

    public void fullStatement(){
        System.out.println("Enter Account Number: ");
        String accountNumber = sc.nextLine();
        List<Transaction> fullStatement = statementService.getFullStatement(accountNumber);
        StatementPrinter.printStatement(fullStatement);
    }

    public void statementByDate(){
        System.out.println("Enter Account Number: ");
        String accountNumber = sc.nextLine();
        System.out.println("Enter Start Date (yyyy-mm-dd): ");
        LocalDate startDate = LocalDate.parse(sc.nextLine());
        System.out.println("Enter End Date (yyyy-mm-dd): ");
        LocalDate endDate = LocalDate.parse(sc.nextLine());
        List<Transaction> statements = statementService.getStatementByDate(accountNumber, startDate, endDate);
        StatementPrinter.printStatement(statements);
    }
}
