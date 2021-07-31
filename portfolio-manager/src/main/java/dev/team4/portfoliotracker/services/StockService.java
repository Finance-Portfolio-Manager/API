package dev.team4.portfoliotracker.services;

import java.util.List;

import dev.team4.portfoliotracker.models.Stock;

public interface StockService {
	Stock addStock(Stock stock);
	Stock addStock(int userId, String stockSymbol, double stockQuantity);
	void deleteStock(int stockId);
	Stock updateStockQuantity(int stockId, double d);
	Stock getStock(int stockId);
	List<Stock> getAllStocks(int userId);
}