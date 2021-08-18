package dev.team4.portfoliotracker.util;

import dev.team4.portfoliotracker.models.*;
import dev.team4.portfoliotracker.services.NewsService;
import dev.team4.portfoliotracker.services.PortfolioService;
import dev.team4.portfoliotracker.services.TransactionService;
import dev.team4.portfoliotracker.services.UserDetailsService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Scheduled;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;

@Component
public class UpdateUtil {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    PortfolioService portfolioService;

    @Autowired
    NewsService newsService;


    public int sendUpdateNews(){
       News news = new News();
       List<News> newsList = newsService.getNewsList();
       if(newsList.size() >0){
           news = newsList.get(newsList.size()-1);
       }

        
        
        List<User> users = userDetailsService.checkAllUser();
        int count = 0;
        for(User user: users) {
            EmailUtil.sendEmailAboutNews(user, news);
            count += 1;
        }
        return count;
    }


    public int sendAllAboutPortfolio(){
        int count = 0;
        List<User> users = userDetailsService.checkAllUser();
        for(User user: users){
            List<Portfolio> portfolios = portfolioService.getPortfoliosByUser(user);
            for(Portfolio portfolio: portfolios){
                List<Transaction> transactions = transactionService.getAllTransactionsByPortfolio(portfolio);
                EmailUtil.sendEmailAboutPortfolio(user,transactions, portfolio.getName());
                count += 1;
            }
        }
        return count;
    }

    public int notifyPriceChange(Double bound, int intervalInSec) {
        int count = 0;
        List<User> users = userDetailsService.checkAllUser();
        for(User user: users){
            List<Portfolio> portfolios = portfolioService.getPortfoliosByUser(user);
            for(Portfolio portfolio: portfolios){
                List<Transaction> transactions = transactionService.getAllTransactionsByPortfolio(portfolio);
                User userTimeChanged = EmailUtil.sendEmailAboutPriceChange(user, transactions, bound, intervalInSec);
                userDetailsService.createUser(userTimeChanged);
                count += 1;
            }
        }
        return count;
    }
}


