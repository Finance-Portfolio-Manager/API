package dev.team4.portfoliotracker.services;

import java.util.List;

import dev.team4.portfoliotracker.models.Stock;

public interface StockService {
	Stock addStock(Stock stock);
	Stock addStock(int userId, double stockQuantity);
	void deleteStock(int userId, int stockId);
	void updateStockQuantity(int userId, int stockId, int stockQuantity);
	Stock getStock(int userId, int stockId);
	List<Stock> getAllStocks(int userId);
}