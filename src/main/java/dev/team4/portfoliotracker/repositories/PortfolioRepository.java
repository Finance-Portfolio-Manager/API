package dev.team4.portfoliotracker.repositories;

import dev.team4.portfoliotracker.models.Portfolio;
import dev.team4.portfoliotracker.models.PortfolioObj;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<PortfolioObj, Integer> {
    PortfolioObj getPortfolioById(int portfolioId);
    List<PortfolioObj> getPortfoliosByAccountId(int accountId);
    List<PortfolioObj> getPortfoliosByPublic(boolean privacy);
}
