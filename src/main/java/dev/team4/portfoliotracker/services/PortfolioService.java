package dev.team4.portfoliotracker.services;


import dev.team4.portfoliotracker.models.PortfolioObj;
import dev.team4.portfoliotracker.repositories.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PortfolioService {

    @Autowired
    PortfolioRepository portfolioRepository;

    public PortfolioObj getPortfolioById(int portfolioId) {
        return portfolioRepository.getPortfolioById(portfolioId);
    }


    public List<PortfolioObj> getPortfoliosByAccountId(int accountId) {
        return portfolioRepository.getPortfoliosByAccountId(accountId);
    }


    public List<PortfolioObj> getPortfoliosByPublic(boolean privacy) {
        return portfolioRepository.getPortfoliosByPublic(privacy);
    }

    public PortfolioObj createNewPortfolio(PortfolioObj portfolioObj) {
        return portfolioRepository.save(portfolioObj);
    }

}
