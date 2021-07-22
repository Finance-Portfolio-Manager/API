package dev.team4.portfoliotracker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.team4.portfoliotracker.models.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer> {
	Stock findStockById(int userId, int stockId);
	List<Stock> findAllUserStocks(int userId);
}
