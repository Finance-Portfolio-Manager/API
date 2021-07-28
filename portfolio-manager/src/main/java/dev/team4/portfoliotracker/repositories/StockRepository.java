package dev.team4.portfoliotracker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.team4.portfoliotracker.models.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer> {
	Stock findStockByStockId(int stockId);
	List<Stock> findAllStocksByUserId(int userId);
}
