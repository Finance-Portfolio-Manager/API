package dev.team4.portfoliotracker.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.team4.portfoliotracker.models.Stock;
import dev.team4.portfoliotracker.repositories.StockRepository;

@Service
@Transactional
public class StockServiceImpl implements StockService{
	
	@Autowired
	StockRepository stockRepo;

	/**
	 *@param
	 *@return added stock
	 */
	@Override
	public Stock addStock(Stock stock) {
		return stockRepo.save(stock);
	}

	/**
	 * @param
	 * @return added stock
	 */
	@Override
	public Stock addStock(int userId, String stockSymbol, double stockQuantity) {
		Stock s = new Stock(userId, stockSymbol, stockQuantity);
		return stockRepo.save(s);
	}

	/**
	 *@param
	 *@return deleted stock
	 */
	@Override
	public void deleteStock(int stockId) {
		Stock s = stockRepo.findStockByStockId(stockId);
		stockRepo.delete(s);
	}

	/**
	 *@param
	 *@return updated stock
	 */
	@Override
	public Stock updateStockQuantity(int stockId, double stockQuantity) {
		Stock s = stockRepo.findStockByStockId(stockId);
		s.setStockQuantity(stockQuantity);
		return stockRepo.save(s);
	}

	/**
	 *@param
	 *@return requested stock
	 */
	@Override
	public Stock getStock(int stockId) {
		return stockRepo.findStockByStockId(stockId);
	}

	/**
	 *@param
	 *@return list of requested stocks
	 */
	@Override
	public List<Stock> getAllStocks(int userId) {
		return stockRepo.findAllStocksByUserId(userId);
	}
}
