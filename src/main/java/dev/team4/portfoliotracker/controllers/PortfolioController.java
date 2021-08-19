package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.*;
import dev.team4.portfoliotracker.security.JwtUtility;
import dev.team4.portfoliotracker.services.FavoritesService;
import dev.team4.portfoliotracker.services.PortfolioService;
import dev.team4.portfoliotracker.services.TransactionService;
import dev.team4.portfoliotracker.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/portfolios")
public class PortfolioController {

    @Autowired
    JwtUtility jwtUtility;

    @Autowired
    PortfolioService portfolioService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    FavoritesService favoriteService;

    @Autowired
    TransactionService transactionService;

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<List<PortfolioFrontEnd>> getPortfoliosByUserId(@PathVariable("id")int id) {
        //List of portfolios mapped to database returned by the service method
        User user = userDetailsService.getUserByUserId(id);
        List<Portfolio> userPortfolios = portfolioService.getPortfoliosByUser(user);

        //Prepped list of full portfolio objects to be sent back as the response entity
        List<PortfolioFrontEnd> responsePorts = new ArrayList<>();

        for (Portfolio portfolio : userPortfolios) {
            //Create new response portfolio with portfolio table as constructor parameter
            PortfolioFrontEnd portObj = new PortfolioFrontEnd(portfolio);
            //Will rely on creating List of Stock objects with transaction methods
            portObj.setTransactions(transactionService.getAllTransactionsByPortfolio(portfolio));

            //List of stocks to be assigned to the portfolio
            List<Stock> portStocks = new ArrayList<>();
            //Temp list of stock symbols for tracking in the loop
            List<String> symbols = new ArrayList<>();

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

                //After making the list of stocks and initializing their first quantity, update all quantities
                for (Stock stock : portStocks) {
                    //The method named here has not been created at the time of writing, but its function is planned
                    //will return list of transactions from a portfolio that matches one stock symbol
                    List<Transaction> relevantTransactions = transactionService.getTransactionsByPortfolioAndStockSymbol(portfolio, stock.getSymbol());
                    stock.setQuantity(relevantTransactions);
                    stock.setAvgBuyPrice(relevantTransactions);
                    stock.setCurrentPrice();
                    stock.setChangePercentage();
                }
            }

            portStocks.removeIf(stock -> stock.getQuantity() == 0);
            //Compile remaining details about the portfolio before adding to response list
            portObj.setStocks(portStocks);
            portObj.setValue(portStocks);

            responsePorts.add(portObj);

        }


        return new ResponseEntity<>(responsePorts, HttpStatus.OK);
    }

    @GetMapping("/{id}/favorites")
    @Transactional
    public ResponseEntity<List<PortfolioFrontEnd>> getFavoritesByUserId(@PathVariable("id")int id) {
        //Similar process to creating the user's portfolios, but using the favorites table to grab the IDs

        //List of favorite objects for the user
        List<Favorites> favList = favoriteService.getFavoritesByUserId(id);

        //Tried moving this logic from the favorites service layer to the controller
        List<Portfolio> favoritePortfolios = new ArrayList<>();
        //for each entry in the favorites table for a user, return the portfolio for the Id listed
        for (Favorites favorite : favList) {
            Portfolio tempPort = portfolioService.getPortfolioByPortfolioId(favorite.getPortfolioId());
            favoritePortfolios.add(tempPort);
        }

        //Prepped list of full portfolio objects to be sent back as the response entity
        List<PortfolioFrontEnd> responsePorts = new ArrayList<>();


        for (Portfolio portfolio : favoritePortfolios) {
            //Create new response portfolio with portfolio table as constructor parameter
            PortfolioFrontEnd portObj = new PortfolioFrontEnd(portfolio);
            //Will rely on creating List of Stock objects with transaction methods
            portObj.setTransactions(transactionService.getAllTransactionsByPortfolio(portfolio));

            //List of stocks to be assigned to the portfolio
            List<Stock> portStocks = new ArrayList<>();
            //Temp list of stock symbols for tracking in the loop
            List<String> symbols = new ArrayList<>();

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

                //After making the list of stocks and initializing their first quantity, update all quantities
                for (Stock stock : portStocks) {
                    //The method named here has not been created at the time of writing, but its function is planned
                    //will return list of transactions from a portfolio that matches one stock symbol
                    List<Transaction> relevantTransactions = transactionService.getTransactionsByPortfolioAndStockSymbol(portfolio, stock.getSymbol());
                    stock.setQuantity(relevantTransactions);
                    stock.setAvgBuyPrice(relevantTransactions);
                    stock.setCurrentPrice();
                    stock.setChangePercentage();
                }
            }

            portStocks.removeIf(stock -> stock.getQuantity() == 0);

            //Compile remaining details about the portfolio before adding to response list
            portObj.setStocks(portStocks);
            portObj.setValue(portStocks);

            responsePorts.add(portObj);

        }


        return new ResponseEntity<>(responsePorts, HttpStatus.OK);

    }

    @GetMapping(value = "/public", produces = "application/json")
    @Transactional
    public ResponseEntity<List<PortfolioFrontEnd>> getAllPublicPortfolios() {
        //Similar process to creating the user's portfolios, but grabbing all public ports

        //List of portfolios mapped to database returned by the service method
        List<Portfolio> publicPortfolios = portfolioService.getPortfoliosByIsPublic(true);

        //Prepped list of full portfolio objects to be sent back as the response entity
        List<PortfolioFrontEnd> responsePorts = new ArrayList<>();

        for (Portfolio portfolio : publicPortfolios) {
            //Create new response portfolio with portfolio table as constructor parameter
            PortfolioFrontEnd portObj = new PortfolioFrontEnd(portfolio);
            //Will rely on creating List of Stock objects with transaction methods
            portObj.setTransactions(transactionService.getAllTransactionsByPortfolio(portfolio));
            List<Transaction> transactionList = portObj.getTransactions();

            //List of stocks to be assigned to the portfolio
            List<Stock> portStocks = new ArrayList<>();
            //Temp list of stock symbols for tracking in the loop
            List<String> symbols = new ArrayList<>();

            //Create the list of Stock objects
            for (Transaction transaction : transactionList) {
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
                List<Transaction> relevantTransactions = transactionService.getTransactionsByPortfolioAndStockSymbol(portfolio, stock.getSymbol());
                stock.setQuantity(relevantTransactions);
                stock.setAvgBuyPrice(relevantTransactions);
                stock.setCurrentPrice();
                stock.setChangePercentage();
            }

            portStocks.removeIf(stock -> stock.getQuantity() == 0);

            //Compile remaining details about the portfolio before adding to response list
            portObj.setStocks(portStocks);
            portObj.setValue(portStocks);

            responsePorts.add(portObj);

        }
        return new ResponseEntity<>(responsePorts, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Portfolio> createNewPortfolio(@RequestBody Portfolio portfolio) {
        Portfolio p = portfolioService.createNewPortfolio(portfolio);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @DeleteMapping(consumes = "application/json")
    public ResponseEntity<Portfolio> deletePortfolio(@RequestBody Portfolio portfolio) {
        portfolioService.deletePortfolio(portfolio);
        return new ResponseEntity<>(portfolio, HttpStatus.ACCEPTED);
    }

}
