package com.finVault.view;

import com.finVault.model.Customer;

public class CustomerPrinter {
    public static void printCustomerDetails(Customer customer) {
        System.out.println("=== Customer Details ===");
        System.out.println("Customer ID     : "    + customer.getCustomerId());
        System.out.println("Name            : "    + customer.getCustomerName());
        System.out.println("Date of Birth   : "    + customer.getDateOfBirth());
        System.out.println("PAN Number      : "    + customer.getPanNumber());
        System.out.println("Aadhaar Number  : "    + customer.getAadhaarNumber());
        System.out.println("Mobile Number   : "    + customer.getMobileNumber());
        System.out.println("Email           : "    + customer.getEmailId());
        System.out.println("Address         : "    + customer.getAddress());
        System.out.println("Nominee Name    : "    + customer.getNomineeName());
        System.out.println("Kyc Status      : "    + customer.getKycStatus());
        System.out.println("Customer Status : "    + customer.getCustomerStatus());
        System.out.println("Joined Date     : "    + customer.getJoinedDate());
        System.out.println("Accounts        : "    + customer.getAccountNumbers());
    }
    public static void printAccounts(Customer customer) {
        System.out.println("=== Account Statement for " + customer.getCustomerName() + " ===");
        customer.getAccountNumbers().forEach(acc ->
                System.out.println("Account: " + acc));
    }
}
