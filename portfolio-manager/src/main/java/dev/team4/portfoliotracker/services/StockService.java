package dev.team4.portfoliotracker.services;

import java.util.List;

import dev.team4.portfoliotracker.models.Stock;

public interface StockService {
	Stock addStock(Stock stock);
	Stock addStock(int userId, int stockId, String stockSymbol, double stockQuantity);
	void deleteStock(int stockId);
	void updateStockQuantity(int stockId, double d);
	Stock getStock(int stockId);
	List<Stock> getAllStocks(int userId);
}