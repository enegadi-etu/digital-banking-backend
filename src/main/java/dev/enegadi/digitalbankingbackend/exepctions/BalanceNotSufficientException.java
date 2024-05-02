package dev.enegadi.digitalbankingbackend.exepctions;

public class BalanceNotSufficientException extends Exception {
    public BalanceNotSufficientException(String insufficientFunds) {
        super(insufficientFunds);
    }
}
