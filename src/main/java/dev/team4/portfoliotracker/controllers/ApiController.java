package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.services.ApiService;
import java.util.Arrays;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiController {

	@Autowired
	ApiService apiService;

	/**
	 * Author: David Garcia
	 * @param stockSymbols - array inside of post body. CONTENT-TYPE: application/Json - ex: looking like the following as a request: ["MSFT", "AMZN", "GOOG"]
	 * @return json string with up-to-date stock prices as of the time of the HTTP request
	 */
	@PostMapping(value="/get-symbol-prices", consumes="application/json")
	public ResponseEntity<Map> getSymbolPrices(@RequestBody String[] stockSymbols){

		System.out.println(Arrays.toString(stockSymbols));

		return ResponseEntity.ok().body(apiService.getSymbolPrices(stockSymbols));
	}

	/**
	 * Author: David Garcia
	 * @param stockSymbols - array inside of post body. CONTENT-TYPE: application/Json - ex: looking like the following as a request: ["MSFT", "AMZN", "GOOG"]
	 * @return json string with % pnl calculated from previous day's close. ex: ["MSFT": -0.02, "GOOG": 0.07, "AMZN": -0.92]
	 */
	@PostMapping(value="/get-symbol-pnl", consumes="application/json")
	public ResponseEntity<Map> getSymbolPnl(@RequestBody String[] stockSymbols){
		String[] symbolArray = {"MSFT","AMZN"};

		return ResponseEntity.ok().body(apiService.getSymbolPnl(stockSymbols));
	}
}
