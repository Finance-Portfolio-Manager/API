package dev.team4.portfoliotracker.repositories;

import dev.team4.portfoliotracker.models.Portfolio;
import dev.team4.portfoliotracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
    Portfolio findPortfolioByPortfolioId(int portfolioId);
    List<Portfolio> getPortfoliosByUser(User user);
    List<Portfolio> getPortfoliosByIsPublic(boolean privacy);
}
