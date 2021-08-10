package dev.team4.portfoliotracker.util;

import dev.team4.portfoliotracker.models.Balances;
import dev.team4.portfoliotracker.models.PortfolioFrontEnd;
import dev.team4.portfoliotracker.services.BalancesService;
import dev.team4.portfoliotracker.services.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        List<PortfolioFrontEnd> portfolios = portfolioService.getAllPortfolios();
        // get current timestamp
        LocalDateTime timestamp = LocalDateTime.now();

        // get list of stocks AND quantities from every portfolio
        //for each portfolio, sum each (stock quantity*stock price) for total portfolio value
        for (PortfolioFrontEnd portfolio: portfolios) {
            Balances bal = new Balances(portfolio.getValue(), timestamp, portfolio.getUserId(), portfolio.getPortfolioId());
            balancesService.addBalance(bal);
        }
    }
}