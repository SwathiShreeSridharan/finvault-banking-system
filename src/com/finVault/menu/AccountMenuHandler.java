package com.finVault.menu;

import com.finVault.enums.AccountType;
import com.finVault.exception.FinVaultException;
import com.finVault.factory.AccountFactory;
import com.finVault.model.Account;
import com.finVault.model.Customer;
import com.finVault.model.FixedDepositAccount;
import com.finVault.repository.AccountRepository;
import com.finVault.service.AccountService;
import com.finVault.view.AccountPrinter;
import com.finVault.view.FDAccountPrinter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.finVault.utils.ConsoleUtils.readInt;

public class AccountMenuHandler {
    private final Scanner sc;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final CustomerMenuHandler customerMenuHandler;
    public AccountMenuHandler(AccountRepository accountRepository, AccountService accountService, CustomerMenuHandler customerMenuHandler, Scanner sc) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.customerMenuHandler = customerMenuHandler;
        this.sc = sc;
    }
    public void handleAccountMenu(){
        System.out.println("\n--- Account Management ---");
        System.out.println("1. Create Savings Account");
        System.out.println("2. Create Current Account");
        System.out.println("3. Create Fixed Deposit Account");
        System.out.println("4. View Account Details");
        System.out.println("5. View All Accounts");
        System.out.println("6. Freeze Account");
        System.out.println("7. Close Account");
        System.out.println("8. Get Account Balance");
        System.out.println("9. Change PIN");
        System.out.println("Enter choice: ");
        try{
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice){
                case 1 -> createSavingsAccount();
                case 2 -> createCurrentAccount();
                case 3 -> createFDAccount();
                case 4 -> viewAccount();
                case 5 -> viewAllAccounts();
                case 6 -> freezeAccount();
                case 7 -> closeAccount();
                case 8 -> getAccountBalance();
                case 9 -> changePin();
                default -> System.out.println("Invalid choice.");
            }
        }
        catch(FinVaultException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void createSavingsAccount(){
        Customer customer = customerMenuHandler.findCustomerForAccount();
        System.out.println("PIN: ");
        String pin = sc.nextLine();
        System.out.println("Created By: ");
        String createdBy = sc.nextLine();
        System.out.println("Interest Rate (default 3.5): ");
        String rate = sc.nextLine();
        System.out.println("Min Balance (default 1000): ");
        String minimumBalance = sc.nextLine();
        System.out.println("Withdrawal Limit (default 20): ");
        String limit = sc.nextLine();

        Map<String, Object> params = new HashMap<>();
        params.put("interestRate", rate.isBlank() ? 3.5 : Double.parseDouble(rate));
        params.put("minBalance", minimumBalance.isBlank() ? 1000.0 : Double.parseDouble(minimumBalance));
        params.put("withdrawalLimit", limit.isBlank() ? 20 : Integer.parseInt(limit));

        Account account = AccountFactory.createAccount(AccountType.SAVINGS, customer, pin, createdBy, params);
        accountService.createAccount(account);
        System.out.println("Savings account created! Account Number: " + account.getAccountNumber());
    }

    public void createCurrentAccount(){
        Customer customer = customerMenuHandler.findCustomerForAccount();
        System.out.println("PIN: ");
        String pin = sc.nextLine();
        System.out.println("Created By: ");
        String createdBy = sc.nextLine();
        System.out.println("Overdraft Limit (default 2500): ");
        String overDraft = sc.nextLine();
        System.out.println("Transaction Fee (default 50): ");
        String transactionFee = sc.nextLine();

        Map<String, Object> params = new HashMap<>();
        params.put("overdraftLimit", overDraft.isBlank() ? 2500.0 : Double.parseDouble(overDraft));
        params.put("transactionFee", transactionFee.isBlank() ? 50.0 : Double.parseDouble(transactionFee));

        Account account = AccountFactory.createAccount(AccountType.CURRENT, customer, pin, createdBy, params);
        accountService.createAccount(account);
        System.out.println("Current account created! Account Number: " + account.getAccountNumber());
    }

    public void createFDAccount(){
        Customer customer = customerMenuHandler.findCustomerForAccount();
        System.out.println("PIN: ");
        String pin = sc.nextLine();
        System.out.println("Created By: ");
        String createdBy = sc.nextLine();
        System.out.println("Principal Amount: ");
        String principalAmount = sc.nextLine();
        System.out.println("Interest Rate (default 6): ");
        String rate = sc.nextLine();
        System.out.println("Tenure Months: ");
        String tenureMonths = sc.nextLine();
        System.out.println("Penalty Rate % (default 1): ");
        String penaltyRate = sc.nextLine();
        System.out.println("Start date (yyyy mm dd): ");
        LocalDate startDate = LocalDate.parse(sc.nextLine());

        Map<String, Object> params = new HashMap<>();
        params.put("principalAmount", principalAmount);
        params.put("rate", rate.isBlank() ? 6 : Double.parseDouble(rate));
        params.put("tenureInMonths", tenureMonths);
        params.put("penaltyRate", penaltyRate.isBlank() ? 1 : Double.parseDouble(penaltyRate));
        params.put("startDate", startDate);

        Account account = AccountFactory.createAccount(AccountType.FIXED_DEPOSIT, customer, pin, createdBy, params);
        accountService.createAccount(account);
        System.out.println("Fixed-Deposit account created! Account Number: " + account.getAccountNumber());
        FDAccountPrinter.printAccountDetails((FixedDepositAccount) account);
    }

    public void viewAccount(){
        System.out.println("Enter Account Number: ");
        String accountNumber = sc.nextLine();
        Account account = accountService.findAccount(accountNumber);
        AccountPrinter.printAccountDetails(account);
    }

    public void viewAllAccounts(){
        Collection<Account> accounts = accountRepository.findAll();
        if(accounts.isEmpty()){
            System.out.println("No accounts found!");
        }
        accounts.forEach(AccountPrinter::printAccountDetails);
    }

    public void freezeAccount(){
        System.out.println("Enter Account Number: ");
        String accountNumber = sc.nextLine();
        accountService.freezeAccount(accountNumber);
        System.out.println("Account frozen successfully!");
    }

    public void closeAccount(){
        System.out.println("Enter Account Number: ");
        String accountNumber = sc.nextLine();
        accountService.closeAccount(accountNumber);
        System.out.println("Account closed successfully!");
    }

    public void getAccountBalance(){
        System.out.println("Enter Account Number: ");
        String accountNumber = sc.nextLine();
        BigDecimal balance = accountService.getBalance(accountNumber);
        System.out.println("Account balance: " + balance);
    }

    public void changePin(){
        System.out.println("Enter Account Number: ");
        String accountNumber = sc.nextLine();
        System.out.println("Enter Old PIN: ");
        String oldPin = sc.nextLine();
        System.out.println("Enter New PIN: ");
        String newPin = sc.nextLine();
        accountService.changePin(accountNumber, oldPin, newPin);
        System.out.println("Account Pin changed successfully!");
    }
}
