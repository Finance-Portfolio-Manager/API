package dev.team4.portfoliotracker.services;


import dev.team4.portfoliotracker.models.Portfolio;
import dev.team4.portfoliotracker.models.Transaction;
import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.repositories.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PortfolioService {

    @Autowired
    PortfolioRepository portfolioRepository;

    public Portfolio getPortfolioByPortfolioId(int portfolioId) {
        return portfolioRepository.findPortfolioByPortfolioId(portfolioId);
    }


    public List<Portfolio> getPortfoliosByUser(User user) {
        return portfolioRepository.getPortfoliosByUser(user);
    }


    public List<Portfolio> getPortfoliosByIsPublic(boolean privacy) {
        return portfolioRepository.getPortfoliosByIsPublic(privacy);
    }

    public Portfolio createNewPortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    public Portfolio deletePortfolio(Portfolio portfolio) {
        portfolioRepository.delete(portfolio);
        return portfolio;
    }

}
