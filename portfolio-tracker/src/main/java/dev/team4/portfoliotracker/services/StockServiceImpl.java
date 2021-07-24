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

	@Override
	public Stock addStock(Stock stock) {
		return stockRepo.save(stock);
	}

	@Override
	public Stock addStock(int userId, double stockQuantity) {
		Stock s = new Stock(userId, stockQuantity);
		return stockRepo.save(s);
	}

	@Override
	public void deleteStock(int userId, int stockId) {
		Stock s = stockRepo.findStockByUserIdAndStockId(userId, stockId);
		stockRepo.delete(s);
	}

	@Override
	public void updateStockQuantity(int userId, int stockId, int stockQuantity) {
		Stock s = stockRepo.findStockByUserIdAndStockId(userId, stockId);
		s.setUserId(userId);
		s.setStockId(stockId);
		s.setStockQuantity(stockQuantity);
		stockRepo.save(s);
	}

	@Override
	public Stock getStock(int userId, int stockId) {
		return stockRepo.findStockByUserIdAndStockId(userId, stockId);
	}

	@Override
	public List<Stock> getAllStocks(int userId) {
		return stockRepo.findAllStocksByUserId(userId);
	}
}
