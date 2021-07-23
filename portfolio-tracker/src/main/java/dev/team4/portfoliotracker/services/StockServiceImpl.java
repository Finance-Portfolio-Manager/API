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
	public Stock addStock(int userId, int stockId, int stockQuantity) {
		Stock s = new Stock(userId, stockId, stockQuantity);
		return stockRepo.save(s);
	}

	@Override
	public void deleteStock(int userId, int stockId) {
		Stock s = stockRepo.findStockById(userId, stockId);
		stockRepo.delete(s);
	}

	@Override
	public void updateStockQuantity(int userId, int stockId, int stockQuantity) {
		Stock s = stockRepo.findStockById(userId, stockId);
		s.setUserId(userId);
		s.setStockId(stockId);
		s.setStockQuantity(stockQuantity);
		stockRepo.save(s);
	}

	@Override
	public Stock getStock(int userId, int stockId) {
		return stockRepo.findStockById(userId, stockId);
	}

	@Override
	public List<Stock> getAllStocks(int userId) {
		return stockRepo.findAllUserStocks(userId);
	}
}
