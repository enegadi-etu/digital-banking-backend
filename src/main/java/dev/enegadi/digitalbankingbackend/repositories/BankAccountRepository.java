package dev.enegadi.digitalbankingbackend.repositories;

import dev.enegadi.digitalbankingbackend.entities.BankAccount;
import dev.enegadi.digitalbankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
