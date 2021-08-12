package dev.team4.portfoliotracker.repositories;

import dev.team4.portfoliotracker.models.Portfolio;
import dev.team4.portfoliotracker.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Transaction findTransactionByTransactionId(int transactionId);
    List<Transaction> findTransactionsByPortfolio(Portfolio portfolio);
    List<Transaction> findTransactionsByPortfolioAndStockSymbol(Portfolio portfolio, String stockSymbol);

}
