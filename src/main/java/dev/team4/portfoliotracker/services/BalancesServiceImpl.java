package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.models.Balances;
import dev.team4.portfoliotracker.repositories.BalancesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        return balancesRepository.findAllBalancesByPortfolioIdOrderByDateDesc(portfolioId);
        //.stream().limit(14).collect(Collectors.toList())
    }

    @Override
    public Balances addBalance(Balances bal) {
        return balancesRepository.save(bal);
    }
}
