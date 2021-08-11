package dev.team4.portfoliotracker.services;


import dev.team4.portfoliotracker.models.Portfolio;
import dev.team4.portfoliotracker.models.Transaction;
import dev.team4.portfoliotracker.repositories.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PortfolioService {

    @Autowired
    PortfolioRepository portfolioRepository;

    public Portfolio getPortfolioById(int portfolioId) {
        return portfolioRepository.getPortfolioById(portfolioId);
    }


    public List<Portfolio> getPortfoliosByAccountId(int accountId) {
        return portfolioRepository.getPortfoliosByAccountId(accountId);
    }


    public List<Portfolio> getPortfoliosByPublic(boolean privacy) {
        return portfolioRepository.getPortfoliosByPublic(privacy);
    }

    public Portfolio createNewPortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

}
