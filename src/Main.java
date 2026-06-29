import com.finVault.menu.AccountMenuHandler;
import com.finVault.menu.CustomerMenuHandler;
import com.finVault.menu.StatementMenuHandler;
import com.finVault.menu.TransactionMenuHandler;
import com.finVault.repository.AccountRepository;
import com.finVault.repository.CustomerRepository;
import com.finVault.repository.TransactionRepository;
import com.finVault.service.AccountService;
import com.finVault.service.CustomerService;
import com.finVault.service.StatementService;
import com.finVault.service.TransactionService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountRepository accountRepository = new AccountRepository();
        CustomerRepository customerRepository = new CustomerRepository();
        TransactionRepository transactionRepository = new TransactionRepository();

        AccountService accountService = new AccountService(accountRepository);
        CustomerService customerService = new CustomerService(customerRepository);
        StatementService statementService = new StatementService(accountRepository);
        TransactionService transactionService = new TransactionService(transactionRepository, accountService);

        CustomerMenuHandler customerMenuHandler = new CustomerMenuHandler(customerRepository, customerService, sc);
        AccountMenuHandler accountMenuHandler = new AccountMenuHandler(accountRepository,accountService, customerMenuHandler, sc);
        StatementMenuHandler statementMenuHandler = new StatementMenuHandler(statementService, sc);
        TransactionMenuHandler transactionMenuHandler = new TransactionMenuHandler(transactionRepository,transactionService,accountRepository,accountService, sc);


        System.out.println("============================================");
        System.out.println("=====Welcome to FinVault Banking System=====");
        System.out.println("============================================");

        while(true){
            System.out.println("\n========= MAIN MENU =========");
            System.out.println("1. Customer Management");
            System.out.println("2. Account Management");
            System.out.println("3. Transactions");
            System.out.println("4. Statements");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice){
                case 1 -> customerMenuHandler.handleCustomerMenu();
                case 2 -> accountMenuHandler.handleAccountMenu();
                case 3 -> transactionMenuHandler.handleTransactionMenu();
                case 4 -> statementMenuHandler.handleStatementMenu();
                case 0 -> System.exit(0);
                default -> System.out.println("Invalid choice");
            }
        }
    }
}