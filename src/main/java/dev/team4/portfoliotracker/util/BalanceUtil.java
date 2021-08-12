package dev.team4.portfoliotracker.util;

import dev.team4.portfoliotracker.models.*;
import dev.team4.portfoliotracker.services.BalancesService;
import dev.team4.portfoliotracker.services.PortfolioService;
import dev.team4.portfoliotracker.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class BalanceUtil {

    @Autowired
    PortfolioService portfolioService;

    @Autowired
    BalancesService balancesService;

    @Autowired
    TransactionService transactionService;

    public void storeBalances() {
        //get every portfolio
        List<Portfolio> portfolios = portfolioService.getAllPortfolios();

        // get list of stocks AND quantities from every portfolio
        //for each portfolio, sum each (stock quantity*stock price) for total portfolio value
        for (Portfolio p: portfolios) {
//            System.out.println(p);
            // get current timestamp
            LocalDateTime timestamp = LocalDateTime.now();
            Balances invested = new Balances(getInvestedValue(p), timestamp, p.getUser().getUserId(), p.getPortfolioId(), "i");
            Balances current = new Balances(getCurrentValue(p), timestamp, p.getUser().getUserId(), p.getPortfolioId(), "c");
            System.out.println(balancesService.addBalance(invested));
            System.out.println(balancesService.addBalance(current));
//            System.out.println(invested);
//            System.out.println(current);
        }
    }

    public BigDecimal getCurrentValue(Portfolio p) {
        List<Transaction> transactions = transactionService.getAllTransactionsByPortfolio(p);
        BigDecimal val = BigDecimal.ZERO;
        for (int i=0; i< transactions.size(); i++) {
//            BigDecimal price = transactions.get(i).getSharePrice();
            Stock s = new Stock(transactions.get(i));
            System.out.println("fetching price of " + s.getSymbol());
            BigDecimal currentPrice = s.getCurrentPrice();
            double quantity = transactions.get(i).getTransactionQuantity();
//            System.out.println(val + " current");
            val = val.add(BigDecimal.valueOf(quantity).multiply(currentPrice));
//            System.out.println(val + " current");
        }
        return val;
    }

    public BigDecimal getInvestedValue(Portfolio p) {

        List<Transaction> transactions = transactionService.getAllTransactionsByPortfolio(p);
        BigDecimal val = BigDecimal.ZERO;
        for (int i=0; i< transactions.size(); i++) {
            BigDecimal price = transactions.get(i).getSharePrice();
            double quantity = transactions.get(i).getTransactionQuantity();
//            System.out.println(val + " invested");
            val = val.add(BigDecimal.valueOf(quantity).multiply(price));
//            System.out.println(val + " invested");
        }
        return val;
    }
}