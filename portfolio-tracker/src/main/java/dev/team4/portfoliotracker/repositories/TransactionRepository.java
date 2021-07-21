package dev.team4.portfoliotracker.repositories;

import dev.team4.portfoliotracker.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
