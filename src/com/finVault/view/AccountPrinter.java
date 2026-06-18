package com.finVault.view;

import com.finVault.model.Account;

public class AccountPrinter {
    public static void printAccountDetails(Account account) {
        System.out.println("=== Account Details ===");
        System.out.println("Account Number : " + account.getAccountNumber());
        System.out.println("Owner          : " + account.getCustomer());
        System.out.println("Balance        : " + account.getBalance());
        System.out.println("Account Type   : " + account.getAccountType());
        System.out.println("Status         : " + account.getAccountStatus());
        System.out.println("Created Date   : " + account.getCreatedDate());
    }
}
