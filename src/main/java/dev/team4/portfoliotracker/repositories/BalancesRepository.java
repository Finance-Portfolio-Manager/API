package dev.team4.portfoliotracker.repositories;

import dev.team4.portfoliotracker.models.Balances;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalancesRepository extends JpaRepository<Balances, Integer> {
    List<Balances> findAllBalancesByUserId(int userId);
    List<Balances> findAllBalancesByPortfolioIdOrderByDateDesc(int portfolioId);
}
