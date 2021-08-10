package dev.team4.portfoliotracker.services;


import dev.team4.portfoliotracker.models.PortfolioFrontEnd;
import dev.team4.portfoliotracker.repositories.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PortfolioService {

    @Autowired
    PortfolioRepository portfolioRepository;

    public PortfolioFrontEnd getPortfolioById(int portfolioId) {
        return portfolioRepository.getPortfolioById(portfolioId);
    }


    public List<PortfolioFrontEnd> getPortfoliosByAccountId(int accountId) {
        return portfolioRepository.getPortfoliosByAccountId(accountId);
    }


    public List<PortfolioFrontEnd> getPortfoliosByPublic(boolean privacy) {
        return portfolioRepository.getPortfoliosByPublic(privacy);
    }

    public PortfolioFrontEnd createNewPortfolio(PortfolioFrontEnd portfolioFrontEnd) {
        return portfolioRepository.save(portfolioFrontEnd);
    }

    public List<PortfolioFrontEnd> getAllPortfolios() {
        return portfolioRepository.findAll();
    }
}
