package dev.team4.portfoliotracker.util;


import dev.team4.portfoliotracker.models.News;
import dev.team4.portfoliotracker.models.Transaction;
import dev.team4.portfoliotracker.models.User;
import org.hibernate.type.TrueFalseType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//fuming
@SpringBootTest
public class EmailUtilTest {
    @Test
    public void sendEmailAboutPriceChangeTest(){
        User user = new User("revature.team.3@gmail.com", "test1", "password");
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = new Transaction();
        transaction1.setTransactionQuantity(10);
        transaction1.setStockSymbol("AMD");
        transactions.add(transaction1);
        Transaction transaction2 = new Transaction();
        transaction2.setTransactionQuantity(-5);
        transaction2.setStockSymbol("AMD");
        transactions.add(transaction2);
        assertNotNull(EmailUtil.sendEmailAboutPriceChange(user, transactions, 0.1, 1));
    }

    @Test
    public void sendEmailAboutPortfolioTest(){
        User user = new User("revature.team.3@gmail.com", "test1", "password");
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = new Transaction();
        transaction1.setTransactionQuantity(10);
        transaction1.setStockSymbol("AMD");
        transactions.add(transaction1);
        Transaction transaction2 = new Transaction();
        transaction2.setTransactionQuantity(-5);
        transaction2.setStockSymbol("AMD");
        transactions.add(transaction2);
        assertTrue(EmailUtil.sendEmailAboutPortfolio(user, transactions, "portfolio_name"));
    }

    @Test
    public void sendEmailAboutNewsTest(){
        User user = new User("revature.team.3@gmail.com", "test1", "password");
        final News news = new News(1,"Facebook Ranked Among Today's Trending Stocks","Q.ai runs daily factor models to get the most up-to-date reading on stocks and ETFs. Today, our deep-learning algorithms have identified Facebook among others.","https://www.forbes.com/sites/qai/2021/08/09/facebook-ranked-among-todays-trending-stocks/", "https://thumbor.forbes.com/thumbor/fit-in/1200x0/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F61112c3daa5906c61c419c4e%2F0x0.png");
        final News news2 = new News(2,"test Title","test Description","Test URL", "Test IMG URL");
        assertTrue(EmailUtil.sendEmailAboutNews(user, news));
    }




}
