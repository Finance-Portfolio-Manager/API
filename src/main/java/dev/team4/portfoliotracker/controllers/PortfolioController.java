package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.Portfolio;
import dev.team4.portfoliotracker.models.PortfolioFrontEnd;
import dev.team4.portfoliotracker.models.Stock;
import dev.team4.portfoliotracker.models.Transaction;
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
        //List of portfolios mapped to database returned by the service method
        List<Portfolio> userPortfolios = portfolioService.getPortfoliosByAccountId(id);

        //Prepped list of full portfolio objects to be sent back as the response entity
        List<PortfolioFrontEnd> responsePorts = new ArrayList<>();

        //List of stocks to be assigned to the portfolio
        List<Stock> portStocks = new ArrayList<>();
        //Temp list of stock symbols for tracking in the loop
        List<String> symbols = new ArrayList<>();

        for (Portfolio portfolio : userPortfolios) {
            //Create new response portfolio with portfolio table as constructor parameter
            PortfolioFrontEnd portObj = new PortfolioFrontEnd(portfolio);
            //Will rely on creating List of Stock objects with transaction methods
            portObj.setTransactions(transactionService.getAllTransactionsByUserId(id));

            //Create the list of Stock objects
            for (Transaction transaction : portObj.getTransactions()) {
                //At each transaction, check if a stock symbol has been recorded

                if (symbols.contains(transaction.getStockSymbol())) {
                    //If stock has already been added, skip
                    continue;
                } else {
                    //add to list of strings tracking symbols
                    symbols.add(transaction.getStockSymbol());
                    //Create new stock object for this portfolio by passing the first transaction for it as arg
                    //Initialized quantity is the quantity of the first transaction
                    Stock tempStock = new Stock(transaction);
                    //Add new object to list of Stocks
                    portStocks.add(tempStock);
                }
            }

            //After making the list of stocks and initializing their first quantity, update all quantities
            for (Stock stock : portStocks) {
                //The method named here has not been created at the time of writing, but its function is planned
                //will return list of transactions from a portfolio that matches one stock symbol
                List<Transaction> relevantTransactions = portfolioService.getTransactionByPortfolioIdAndStockSymbol();
                stock.setQuantity(relevantTransactions);
            }

            //Compile remaining details about the portfolio before adding to response list
            portObj.setStocks(portStocks);
            portObj.setValue(portStocks);

            responsePorts.add(portObj);

        }


        return new ResponseEntity<>(responsePorts, HttpStatus.OK);
    }


}
