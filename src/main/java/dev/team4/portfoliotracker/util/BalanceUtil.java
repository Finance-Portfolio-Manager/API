package dev.team4.portfoliotracker.util;

import dev.team4.portfoliotracker.models.*;
import dev.team4.portfoliotracker.services.BalancesService;
import dev.team4.portfoliotracker.services.PortfolioService;
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

    public void storeBalances() {
        //get every portfolio
        List<Portfolio> portfolios = portfolioService.getAllPortfolios();

        // get list of stocks AND quantities from every portfolio
        //for each portfolio, sum each (stock quantity*stock price) for total portfolio value
        for (Portfolio p: portfolios) {
            // get current timestamp
            LocalDateTime timestamp = LocalDateTime.now();
            Balances bal = new Balances(getValue(p), timestamp, p.getUser().getUserId(), p.getPortfolioId());
            balancesService.addBalance(bal);
        }
    }

    public BigDecimal getValue(Portfolio p) {
        List<Transaction> transactions = p.getTransactions();
        BigDecimal val = BigDecimal.ZERO;
        for (int i=0; i< transactions.size(); i++) {
            BigDecimal price = transactions.get(i).getSharePrice();
            double quantity = transactions.get(i).getTransactionQuantity();
            val = val.add(BigDecimal.valueOf(quantity).multiply(price));
        }
        return val;
    }
}