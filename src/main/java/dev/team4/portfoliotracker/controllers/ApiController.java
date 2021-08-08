package dev.team4.portfoliotracker.controllers;



import java.util.Arrays;
import java.util.List;
import java.util.Map;

import dev.team4.portfoliotracker.services.ApiService;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiController {
	/*
	@GetMapping(produces = "application/json")
	public ResponseEntity<String> getStock(@RequestParam(value = "symbol") String stockSymbol) {
		HttpResponse<String> response = null;
		String uri = "https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + stockSymbol + "&datatype=json";
		try {
			response = Unirest.get(uri)
				.header("x-rapidapi-key", "2026ae733amsh2b3a3e7ba055725p1025d0jsn28ad9fad2454")
				.header("x-rapidapi-host", "alpha-vantage.p.rapidapi.com")
				.asString();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(response.getBody(), HttpStatus.OK);
	}
	*/

	@Autowired
	ApiService apiService;
	
	//doesn't use consumed json, but its too late to change it
	@GetMapping(value = "/all", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> getStock(@RequestParam(value = "symbol") List<String> stockSymbols) {
		HttpResponse<String> response = null;
		StringBuilder uri = new StringBuilder("https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes?region=US&symbols=");
		for (String stockSymbol : stockSymbols) {
			uri.append(stockSymbol + "%2C");
		}
		uri.setLength(uri.length()-3);
		try {
			response = Unirest.get(uri.toString())
				.header("x-rapidapi-key", "2026ae733amsh2b3a3e7ba055725p1025d0jsn28ad9fad2454")
				.header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
				.asString();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(response.getBody(), HttpStatus.OK);
	}

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
