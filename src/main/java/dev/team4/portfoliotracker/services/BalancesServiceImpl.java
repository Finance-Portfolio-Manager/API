package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.models.Balances;
import dev.team4.portfoliotracker.repositories.BalancesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BalancesServiceImpl implements BalancesService {

    @Autowired
    BalancesRepository balancesRepository;

    @Override
    public List<Balances> getAllBalances() {
        return balancesRepository.findAll();
    }

    @Override
    public List<Balances> getAllBalancesByUserId(int userId) {
        return balancesRepository.findAllBalancesByUserId(userId);
    }

    @Override
    public List<Balances> getAllBalancesByPortfolioId(int portfolioId) {
        return balancesRepository.findAllBalancesByPortfolioId(portfolioId);
    }

    @Override
    public Balances addBalance(Balances bal) {
        return balancesRepository.save(bal);
    }
}
