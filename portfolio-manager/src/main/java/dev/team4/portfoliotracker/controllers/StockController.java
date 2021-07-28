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
	
	@PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Stock> addStock(@RequestBody Stock stock) {
		stockServ.addStock(stock);
		return new ResponseEntity<>(stock, HttpStatus.OK);
    }
	
	@DeleteMapping(value = "/delete", consumes = "application/json")
	public ResponseEntity<Stock> deleteStock(@RequestBody int stockId){
		stockServ.deleteStock(stockId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Stock> updateStockQuantity(@RequestParam(value = "stockId")int stockId,
														@RequestParam(value = "stockQuantity")int stockQuantity) {
		stockServ.updateStockQuantity(stockId, stockQuantity);
		return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@GetMapping(value = "/{stockId}", produces = "application/json")
    public ResponseEntity<Stock> getStock(@PathVariable("stockId")int stockId) {
		
		return new ResponseEntity<Stock>(stockServ.getStock(stockId), HttpStatus.OK);
    }
	
	@GetMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<List<Stock>> getAllStocks(@PathVariable("userId")int userId) {
		
		return new ResponseEntity<List<Stock>>(stockServ.getAllStocks(userId), HttpStatus.OK);
    }

}