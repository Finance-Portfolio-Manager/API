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
@RequestMapping("/stocks")
public class StockController {
	
	@Autowired
	StockService stockServ;
	
	@PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Stock> addStock(@RequestParam(value = "userId")int userId,
    										@RequestParam(value = "stockId")int stockId,
    										@RequestParam(value = "stockQuantity")int stockQuantity) {
		
		return new ResponseEntity<Stock>(stockServ.addStock(userId, stockId, stockQuantity), HttpStatus.OK);
    }
	
	@DeleteMapping(consumes = "application/json")
	public ResponseEntity<Stock> deleteStock(@PathVariable("userId")int userId, @PathVariable("stockId")int stockId){
		stockServ.deleteStock(userId, stockId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Stock> updateStockQuantity(@RequestParam(value = "userId")int userId,
														@RequestParam(value = "stockId")int stockId,
														@RequestParam(value = "stockQuantity")int stockQuantity) {
		stockServ.updateStockQuantity(userId, stockId, stockQuantity);
		return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@GetMapping(produces = "application/json")
    public ResponseEntity<Stock> getStock(@PathVariable("userId")int userId, @PathVariable("stockId")int stockId) {
		
		return new ResponseEntity<Stock>(stockServ.getStock(userId, stockId), HttpStatus.OK);
    }
	
	@GetMapping(produces = "application/json")
    public ResponseEntity<List<Stock>> getAllStocks(@PathVariable("userId")int userId) {
		
		return new ResponseEntity<List<Stock>>(stockServ.getAllStocks(userId), HttpStatus.OK);
    }

}
