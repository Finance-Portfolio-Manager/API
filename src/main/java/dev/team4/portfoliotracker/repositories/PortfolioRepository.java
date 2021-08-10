package dev.team4.portfoliotracker.repositories;

import dev.team4.portfoliotracker.models.PortfolioFrontEnd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<PortfolioFrontEnd, Integer> {
    PortfolioFrontEnd getPortfolioById(int portfolioId);
    List<PortfolioFrontEnd> getPortfoliosByAccountId(int accountId);
    List<PortfolioFrontEnd> getPortfoliosByPublic(boolean privacy);
}
