package com.finVault.menu;

import com.finVault.enums.KycStatus;
import com.finVault.exception.FinVaultException;
import com.finVault.model.Customer;
import com.finVault.repository.CustomerRepository;
import com.finVault.service.CustomerService;
import com.finVault.view.CustomerPrinter;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Scanner;

import static com.finVault.utils.ConsoleUtils.readInt;

public class CustomerMenuHandler {
    private final Scanner sc;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    public CustomerMenuHandler(CustomerRepository customerRepository, CustomerService customerService, Scanner sc) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
        this.sc = sc;
    }
    public void handleCustomerMenu(){
        System.out.println("\n--- Customer Management ---");
        System.out.println("1. Register Customer");
        System.out.println("2. View Customer Details");
        System.out.println("3. Update Mobile Number");
        System.out.println("4. Update Email");
        System.out.println("5. Update Address");
        System.out.println("6. Update Nominee");
        System.out.println("7. Deactivate Customer");
        System.out.println("8. Update KYC Status");
        System.out.println("9. View All Customers");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        try{
            switch(choice){
                case 1 -> registerCustomer();
                case 2 -> viewCustomer();
                case 3 -> updateMobileNumber();
                case 4 -> updateEmail();
                case 5 -> updateAddress();
                case 6 -> updateNominee();
                case 7 -> updateKYCStatus();
                case 8 -> deactivateCustomer();
                case 9 -> viewAllCustomers();
                default -> System.out.println("Invalid choice");
            }
        }
        catch(FinVaultException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public Customer registerCustomer() {
        System.out.println("Customer Name: ");
        String name = sc.nextLine().trim();
        System.out.println("Date of Birth (yyyy mm dd): ");
        LocalDate dob = LocalDate.parse(sc.nextLine());
        System.out.print("Mobile Number: ");
        String mobile = sc.nextLine().trim();
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        System.out.print("Aadhaar Number: ");
        String aadhaar = sc.nextLine().trim();
        System.out.print("PAN Number: ");
        String pan = sc.nextLine().trim();
        System.out.print("Address: ");
        String address = sc.nextLine().trim();
        System.out.print("Nominee Name: ");
        String nominee = sc.nextLine().trim();

        Customer customer = new Customer(name, dob, mobile, email, aadhaar, pan, address, nominee);
        customerService.registerCustomer(customer);
        System.out.println("Customer registered successfully! Customer ID: " + customer.getCustomerId());
        return customer;
    }

    public void viewCustomer() {
        System.out.print("Enter Customer ID: ");
        String id = sc.nextLine().trim();
        Customer customer = customerService.findCustomerById(id);
        CustomerPrinter.printCustomerDetails(customer);
    }

    public void updateMobileNumber() {
        System.out.println("Enter Customer ID");
        String id = sc.nextLine().trim();
        System.out.print("Enter Mobile Number: ");
        String mobile = sc.nextLine().trim();
        customerService.updateMobileNumber(id, mobile);
        System.out.println("Mobile number updated successfully!");
    }

    public void updateEmail() {
        System.out.println("Enter Customer ID");
        String id = sc.nextLine().trim();
        System.out.print("Enter Email: ");
        String email = sc.nextLine().trim();
        customerService.updateEmail(id, email);
        System.out.println("Email updated successfully!");
    }

    public void updateAddress() {
        System.out.println("Enter Customer ID");
        String id = sc.nextLine().trim();
        System.out.print("Enter Address: ");
        String address = sc.nextLine().trim();
        customerService.updateAddress(id, address);
        System.out.println("Address updated successfully!");
    }

    public void updateNominee() {
        System.out.println("Enter Customer ID");
        String id = sc.nextLine().trim();
        System.out.print("Enter Nominee: ");
        String nominee = sc.nextLine().trim();
        customerService.updateNomineeName(id, nominee);
        System.out.println("Nominee updated successfully!");
    }

    public void updateKYCStatus() {
        System.out.println("Enter Customer ID");
        String id = sc.nextLine().trim();
        System.out.print("Enter KYC Status: ");
        String status = sc.nextLine().trim();
        customerService.updateKycStatus(id, KycStatus.valueOf(status));
        System.out.println("KYC updated successfully!");
    }

    public void deactivateCustomer() {
        System.out.println("Enter Customer ID");
        String id = sc.nextLine().trim();
        customerService.deactivateCustomer(id);
        System.out.println("Customer deactivated successfully!");
    }

    public void viewAllCustomers() {
        Collection<Customer> customers = customerService.findAllCustomers();
        if(customers.isEmpty()){
            System.out.println("No customer found!");
        }
        customers.forEach(CustomerPrinter::printCustomerDetails);
    }

    public Customer findCustomerForAccount() {
        System.out.println("Do you have a Customer ID? (y/n): ");
        String ans = sc.nextLine().trim().trim();
        if (ans.equalsIgnoreCase("y")) {
            System.out.print("Enter Customer ID: ");
            return customerService.findCustomerById(sc.nextLine());
        } else {
            System.out.println("Register customer first:");
            return registerCustomer();
        }
    }
}
