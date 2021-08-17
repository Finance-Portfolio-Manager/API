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
//        //This only works for David DB
//        List<News> newsList = newsService.getNewsList();
//        if(newsList.size() >0){
//            News news = newsList.get(newsList.size()-1)
//        }

        //Testing data
        final News news = new News(1,"Facebook Ranked Among Today's Trending Stocks","Q.ai runs daily factor models to get the most up-to-date reading on stocks and ETFs. Today, our deep-learning algorithms have identified Facebook among others.","https://www.forbes.com/sites/qai/2021/08/09/facebook-ranked-among-todays-trending-stocks/", "https://thumbor.forbes.com/thumbor/fit-in/1200x0/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F61112c3daa5906c61c419c4e%2F0x0.png");

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


