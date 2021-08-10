package dev.team4.portfoliotracker.util;

import dev.team4.portfoliotracker.models.Balances;
import dev.team4.portfoliotracker.models.PortfolioObj;
import dev.team4.portfoliotracker.services.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BalanceUtil {

    @Autowired
    PortfolioService portfolioService;

    public void storeBalances() {
        //get every portfolio
        List<PortfolioObj> portfolios = portfolioService.getAllPortfolios();
        // get list of stocks AND quantities from every portfolio
        LocalDateTime timestamp = LocalDateTime.now();

        for (PortfolioObj portfolio: portfolios) {
            Balances bal = new Balances(portfolio.getValue(), timestamp, portfolio.getUserId(), portfolio.getPortfolioId());
        }
        //for each portfolio, sum each (stock quantity*stock price) for total portfolio value
    }
}