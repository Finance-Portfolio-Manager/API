package dev.team4.portfoliotracker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.team4.portfoliotracker.models.Stock;
import dev.team4.portfoliotracker.services.StockService;

@RestController
@CrossOrigin
@RequestMapping("/stocks")
public class StockController {
	
	@Autowired
	StockService stockServ;
	
	@PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Stock> addStock(@RequestBody Stock stock) {
		
		return new ResponseEntity<>(stockServ.addStock(stock), HttpStatus.OK);
    }
	
	@DeleteMapping(consumes = "application/json")
	public ResponseEntity<Stock> deleteStock(@RequestBody Stock stock){
		stockServ.deleteStock(stock.getStockId());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Stock> updateStockQuantity(@RequestBody Stock stock) {
		
		return new ResponseEntity<>(stockServ.updateStockQuantity(stock.getStockId(), stock.getStockQuantity()), HttpStatus.OK);
    }
	
	@GetMapping(value = "/{stockId}", produces = "application/json")
    public ResponseEntity<Stock> getStock(@PathVariable("stockId")int stockId) {
		
		return new ResponseEntity<Stock>(stockServ.getStock(stockId), HttpStatus.OK);
    }
	
	@GetMapping(value = "/all/{userId}", produces = "application/json")
    public ResponseEntity<List<Stock>> getAllStocks(@PathVariable("userId")int userId) {
		
		return new ResponseEntity<List<Stock>>(stockServ.getAllStocks(userId), HttpStatus.OK);
    }

}