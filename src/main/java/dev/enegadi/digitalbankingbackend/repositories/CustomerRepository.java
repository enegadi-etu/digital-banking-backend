package dev.enegadi.digitalbankingbackend.repositories;

import dev.enegadi.digitalbankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
