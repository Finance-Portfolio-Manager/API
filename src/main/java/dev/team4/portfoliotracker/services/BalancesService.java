package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.models.Balances;

import java.util.List;

public interface BalancesService {

    List<Balances> getAllBalances();

    List<Balances> getAllBalancesByUserId();

    List<Balances> getAllBalancesByPortfolioId();

    Balances addBalance(Balances bal);
}
