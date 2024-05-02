package dev.enegadi.digitalbankingbackend.exepctions;

public class BankAccountNotFoundException extends Exception {
    public BankAccountNotFoundException(String accountNotFound) {
        super(accountNotFound);
    }
}
