package dev.enegadi.digitalbankingbackend.repositories;

import dev.enegadi.digitalbankingbackend.entities.AccountOperation;
import dev.enegadi.digitalbankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
}
