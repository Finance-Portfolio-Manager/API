package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.models.Balances;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BalancesServiceImpl implements BalancesService {

    @Autowired


    @Override
    public List<Balances> getAllBalances() {
        return null;
    }

    @Override
    public List<Balances> getAllBalancesByUserId() {
        return null;
    }

    @Override
    public List<Balances> getAllBalancesByPortfolioId() {
        return null;
    }

    @Override
    public Balances addBalance(Balances bal) {
        return null;
    }
}
