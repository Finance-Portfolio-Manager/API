package dev.team4.portfoliotracker.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/charts")
public class ChartController {

    @Autowired
    Environment environment;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/{symbol}")
    public ResponseEntity<String> getChartBySymbol(
        @PathVariable("symbol") String symbol
    ){
        logger.info(symbol); // debug

		HttpResponse<String> response = null;
		String uri = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="+symbol+"&apikey="+environment.getProperty("KEYS_ALPHAVANTAGE");
		try {
			response = Unirest.get(uri).asString();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(response.getBody(), HttpStatus.OK);
    }

}
