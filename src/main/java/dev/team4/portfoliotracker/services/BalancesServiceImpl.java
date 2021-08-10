package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.models.Balances;
import dev.team4.portfoliotracker.repositories.BalancesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BalancesServiceImpl implements BalancesService {

    @Autowired
    BalancesRepository balancesRepository;

    @Override
    public List<Balances> getAllBalances() {
        return balancesRepository.findAll();
    }

    @Override
    public List<Balances> getAllBalancesByUserId() {
        return balancesRepository.findAllBalancesByUserId();
    }

    @Override
    public List<Balances> getAllBalancesByPortfolioId() {
        return balancesRepository.findAllBalancesByPortfolioId();
    }

    @Override
    public Balances addBalance(Balances bal) {
        return balancesRepository.save(bal);
    }
}
