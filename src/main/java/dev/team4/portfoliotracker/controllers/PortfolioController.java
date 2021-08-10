package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.Portfolio;
import dev.team4.portfoliotracker.models.PortfolioFrontEnd;
import dev.team4.portfoliotracker.models.Stock;
import dev.team4.portfoliotracker.security.JwtUtility;
import dev.team4.portfoliotracker.services.PortfolioService;
import dev.team4.portfoliotracker.services.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    JwtUtility jwtUtility;

    @Autowired
    PortfolioService portfolioService;

    @Autowired
    TransactionServiceImpl transactionService;

    @GetMapping("/{id}")
    public ResponseEntity<List<PortfolioFrontEnd>> getPortfoliosByUserId(@PathVariable("id")int id) {
        List<Portfolio> userPortfolios = portfolioService.getPortfoliosByAccountId(id);
        List<PortfolioFrontEnd> responsePorts = new ArrayList<>();
        List<Stock> portStocks = new ArrayList<>();

        for (Portfolio portfolio : userPortfolios) {
            //Create new response portfolio with portfolio table as parameter
            PortfolioFrontEnd portObj = new PortfolioFrontEnd(portfolio);
            //Will rely on creating List of Stock objects with transaction methods
            portObj.setTransactions(transactionService.getAllTransactionsByUserId(id));

            //create temp list for tracking stocks held
        }
        return new ResponseEntity<>(responsePorts, HttpStatus.OK);
    }


}
