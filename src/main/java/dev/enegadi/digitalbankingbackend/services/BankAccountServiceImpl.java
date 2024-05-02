package dev.enegadi.digitalbankingbackend.services;

import dev.enegadi.digitalbankingbackend.DTOs.CustomerDTO;
import dev.enegadi.digitalbankingbackend.entities.*;
import dev.enegadi.digitalbankingbackend.enums.AccountStatus;
import dev.enegadi.digitalbankingbackend.enums.OperationDate;
import dev.enegadi.digitalbankingbackend.exepctions.BalanceNotSufficientException;
import dev.enegadi.digitalbankingbackend.exepctions.BankAccountNotFoundException;
import dev.enegadi.digitalbankingbackend.exepctions.CustomerNotFoundException;
import dev.enegadi.digitalbankingbackend.mappers.BankAccountMapperImpl;
import dev.enegadi.digitalbankingbackend.repositories.AccountOperationRepository;
import dev.enegadi.digitalbankingbackend.repositories.BankAccountRepository;
import dev.enegadi.digitalbankingbackend.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    private AccountOperationRepository accountOperationRepository;
    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private BankAccountMapperImpl dtoMapper;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        log.info("Customer {} saved successfully", savedCustomer.getName());
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialDeposit, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        CurrentAccount  currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setBalance(initialDeposit);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        currentAccount.setStatus(AccountStatus.CREATED);
        CurrentAccount savedAccount = bankAccountRepository.save(currentAccount);
        log.info("Current Account {} created successfully", savedAccount.getId());
        return savedAccount;
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialDeposit, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setBalance(initialDeposit);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        savingAccount.setStatus(AccountStatus.CREATED);
        SavingAccount savedAccount = bankAccountRepository.save(savingAccount);
        log.info("Saving Account {} created successfully", savedAccount.getId());
        return savedAccount;
    }


    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers =  customerRepository.findAll();
        List<CustomerDTO> customerDTOS = customers.stream().map(customer -> dtoMapper.fromCustomer(customer)).collect(Collectors.toList());
        return customerDTOS;
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException("Account not found"));
        log.info("Bank Account {} retrieved successfully", bankAccount.getId());
        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
            BankAccount bankAccount = getBankAccount(accountId);
            if (bankAccount.getBalance() < amount) {
                throw new BalanceNotSufficientException("Insufficient funds");
            }
            AccountOperation accountOperation = new AccountOperation();
            accountOperation.setType(OperationDate.DEBIT);
            accountOperation.setAmount(amount);
            accountOperation.setDescription(description);
            accountOperation.setBankAccount(bankAccount);
            accountOperationRepository.save(accountOperation);
            bankAccount.setBalance(bankAccount.getBalance() - amount);
            bankAccountRepository.save(bankAccount);
            log.info("Debit operation of {} on account {} completed successfully", amount, accountId);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = getBankAccount(accountId);
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationDate.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
        log.info("Credit operation of {} on account {} completed successfully", amount, accountId);
    }

    @Override
    public void transfer(String fromAccountId, String toAccountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
            debit(fromAccountId, amount, description);
            credit(toAccountId, amount, description);
    }

    @Override
    public List<BankAccount> bankAccountList(){
        return bankAccountRepository.findAll();
    }

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        return dtoMapper.fromCustomer(customer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer updatedCustomer = customerRepository.save(customer);
        log.info("Customer {} updated successfully", updatedCustomer.getName());
        return dtoMapper.fromCustomer(updatedCustomer);
    }


    @Override
    public void deleteCustomer(Long customerId){
        customerRepository.deleteById(customerId);
        log.info("Customer {} deleted successfully", customerId);
    }
}
