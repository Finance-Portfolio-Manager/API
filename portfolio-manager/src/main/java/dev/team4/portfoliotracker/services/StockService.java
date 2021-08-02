package dev.team4.portfoliotracker.services;

import java.util.List;

import dev.team4.portfoliotracker.models.Stock;

public interface StockService {
	/**
	 * adds Stock to the database
	 * @param Stock
	 * @return added Stock
	 */
	Stock addStock(Stock stock);
	/**
	 * adds Stock to the database
	 * @param userId
	 * @param stockSymbol
	 * @param stockQuantity
	 * @return added Stock
	 */
	Stock addStock(int userId, String stockSymbol, double stockQuantity);
	/**
	 * Deletes stock matching stockId from the database
	 * @param stockId
	 */
	void deleteStock(int stockId);
	/**
	 * Updates quantity of Stock matching stockId
	 * @param stockId
	 * @param stockQuantity
	 * @return updated Stock
	 */
	Stock updateStockQuantity(int stockId, double d);
	/**
	 * Gets Stock with matching stockId
	 * @param stockId
	 * @return Stock with matching stockId
	 */
	Stock getStock(int stockId);
	/**
	 * Gets all Stocks matching the userId
	 * @param userId
	 * @return List of Stocks with matching userId
	 */
	List<Stock> getAllStocks(int userId);
}