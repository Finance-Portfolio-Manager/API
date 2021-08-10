package dev.team4.portfoliotracker.repositories;

import dev.team4.portfoliotracker.models.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
    Portfolio getPortfolioById(int portfolioId);
    List<Portfolio> getPortfoliosByAccountId(int accountId);
    List<Portfolio> getPortfoliosByPublic(boolean privacy);
}
