package dev.enegadi.digitalbankingbackend.services;

import dev.enegadi.digitalbankingbackend.DTOs.CustomerDTO;
import dev.enegadi.digitalbankingbackend.entities.BankAccount;
import dev.enegadi.digitalbankingbackend.entities.CurrentAccount;
import dev.enegadi.digitalbankingbackend.entities.Customer;
import dev.enegadi.digitalbankingbackend.entities.SavingAccount;
import dev.enegadi.digitalbankingbackend.exepctions.BalanceNotSufficientException;
import dev.enegadi.digitalbankingbackend.exepctions.BankAccountNotFoundException;
import dev.enegadi.digitalbankingbackend.exepctions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    List<CustomerDTO> listCustomers();
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentAccount saveCurrentBankAccount(double initialDeposit, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccount saveSavingBankAccount(double initialDeposit, double interestRate, Long customerId) throws CustomerNotFoundException;
    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String fromAccountId, String toAccountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;

    List<BankAccount> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);
}
