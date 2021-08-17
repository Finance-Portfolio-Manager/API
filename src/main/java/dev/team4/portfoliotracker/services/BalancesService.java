package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.models.Balances;

import java.util.List;

public interface BalancesService {

    List<Balances> getAllBalances();

    List<Balances> getAllBalancesByUserId(int userId);

    List<Balances> getAllBalancesByPortfolioId(int portfolioId);

    Balances addBalance(Balances bal);
}
