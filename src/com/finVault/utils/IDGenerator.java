package com.finVault.utils;

import java.util.UUID;

public class IDGenerator {
    public static String generateTransactionId() {
        return "TRANS-"+ UUID.randomUUID().toString().substring(0,8);
    }

    public static String generateAccountNumber() {
        return "ACC-"+ UUID.randomUUID().toString().substring(0, 8);
    }

    public static String generateCustomerId() {
        return "CUST-"+ UUID.randomUUID().toString().substring(0, 8);
    }
}
