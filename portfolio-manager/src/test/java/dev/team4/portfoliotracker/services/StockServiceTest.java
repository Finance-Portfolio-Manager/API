package dev.team4.portfoliotracker.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import dev.team4.portfoliotracker.models.Stock;
import dev.team4.portfoliotracker.repositories.StockRepository;

@SpringBootTest
public class StockServiceTest {
	
	@InjectMocks
	private StockServiceImpl stockServ;

	@Mock
	private StockRepository stockRepo;
	
	
	private Stock stock = new Stock(1, "AMD", 10);
	
	
	@Test
	public void addStockTest() {
		Stock stockCheck = stock;
		stockCheck.setStockId(1);
		when(stockRepo.save(stock)).thenReturn(stockCheck);
		assertEquals(stockCheck, stockServ.addStock(stock));
	}
	
	@Test
	public void addStockTest2() {
		Stock stockCheck = stock;
		stockCheck.setStockId(1);
		when(stockRepo.save(any(Stock.class))).thenReturn(stockCheck);
		assertEquals(stockCheck, stockServ.addStock(stock.getUserId(), stock.getStockSymbol(), stock.getStockQuantity()));
	}
	
	@Test
	public void deleteStockTest() {
		stockRepo.save(stock);
		when(stockRepo.save(stock)).thenReturn(stock);
		when(stockRepo.findStockByStockId(stock.getStockId())).thenReturn(stock);
		stockServ.deleteStock(stock.getStockId());
		verify(stockRepo, times(1)).delete(stock);
	}
	
	@Test
	public void updateStockTest() {
		Stock stockCheck = stock;
		stockCheck.setStockId(1);
		stockCheck.setStockQuantity(20);
		when(stockRepo.findStockByStockId(stock.getStockId())).thenReturn(stock);
		when(stockRepo.save(stock)).thenReturn(stockCheck);
		assertEquals(stockCheck, stockServ.updateStockQuantity(stock.getStockId(), 20));
	}
	
	@Test
	public void getStockTest() {
		when(stockRepo.findStockByStockId(stock.getStockId())).thenReturn(stock);
		assertEquals(stock, stockServ.getStock(stock.getStockId()));
	}
	
	@Test
	public void getAllStockTest() {
		List<Stock> stockList = null;
		when(stockRepo.findAllStocksByUserId(stock.getUserId())).thenReturn(stockList);
		assertEquals(stockList, stockServ.getStock(stock.getUserId()));
	}

}
