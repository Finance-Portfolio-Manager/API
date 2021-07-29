package dev.team4.portfoliotracker.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.team4.portfoliotracker.services.StockService;
import dev.team4.portfoliotracker.models.*;

@RestController
@CrossOrigin
@RequestMapping("/stocks")
public class StockController {
	
	@Autowired
	StockService stockServ;
	
	@PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Stock> addStock(@RequestBody Stock stock) {
		stockServ.addStock(stock);
		return new ResponseEntity<>(stock, HttpStatus.OK);
    }
	
	@DeleteMapping(consumes = "application/json")
	public ResponseEntity<Stock> deleteStock(@RequestBody Stock stock){
		stockServ.deleteStock(stock.getStockId());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Stock> updateStockQuantity(@RequestBody Stock stock) {
		stockServ.updateStockQuantity(stock.getStockId(), stock.getStockQuantity());
		return new ResponseEntity<>(stock, HttpStatus.OK);
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