package com.finVault.view;

import com.finVault.model.FixedDepositAccount;

public class FDAccountPrinter {
    public static void printAccountDetails(FixedDepositAccount fd) {
        AccountPrinter.printAccountDetails(fd);
        System.out.println("Start Date       : " + fd.getStartDate());
        System.out.println("Mature Date      : " + fd.getMatureDate());
        System.out.println("Tenure           : " + fd.getTenureInMonths() + " months");
        System.out.println("Interest Rate    : " + fd.getInterestRate() + "%");
        System.out.println("Penalty Rate     : " + fd.getPenaltyRate() + "%");
        System.out.println("Maturity Amount  : " + fd.getMaturityAmount());
        System.out.println("Matured          : " + (fd.isMatured() ? "Yes" : "No"));
    }
}