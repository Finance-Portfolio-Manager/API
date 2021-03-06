package dev.team4.portfoliotracker.util;

import dev.team4.portfoliotracker.models.News;
import dev.team4.portfoliotracker.models.Stock;
import dev.team4.portfoliotracker.models.Transaction;
import dev.team4.portfoliotracker.models.User;
import yahoofinance.YahooFinance;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import static java.lang.Math.abs;

public class EmailUtil {
    private static String myEmail = "revature.team.3@gmail.com";
    private static String myPassword = "team3casino";

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public static Session createEmailSession(){
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, myPassword);
            }
        });
        return session;
    }

    public static List<Stock> convertTransactionsToStock(List<Transaction> transactions){
        List<Stock> stocks = new ArrayList<>();
        for(Transaction transaction: transactions){
            boolean isExisted = false;
            for(int i =0; i<stocks.size(); i++){
                if(stocks.get(i).getSymbol().equals(transaction.getStockSymbol())){
                    isExisted = true;
                    double quantity = stocks.get(i).getQuantity() + transaction.getTransactionQuantity();
                    // new method: setQuantityNormal
                    stocks.get(i).setQuantityNormal(quantity);
                }
            }
            if(isExisted == false){
                Stock newStock = new Stock();
                newStock.setSymbol(transaction.getStockSymbol());
                newStock.setQuantityNormal(transaction.getTransactionQuantity());
                stocks.add(newStock);
            }
        }
        for(int i =0; i<stocks.size(); i++){ // remove 0 quantity stock
            if(stocks.get(i).getQuantity() <= 0){
                stocks.remove(i);
            }
        }
        return stocks;
    }


    public static User sendEmailAboutPriceChange(User user, List<Transaction> transactions, Double bound, int intervalInSec){
        List<Stock> stocks = convertTransactionsToStock(transactions);
        for (Stock stock : stocks) {
            yahoofinance.Stock yahooStock = null;
            try {
                yahooStock = YahooFinance.get(stock.getSymbol());
            } catch (IOException e) {
                e.printStackTrace();
            }
            double price = yahooStock.getQuote().getPrice().doubleValue();
            double change = yahooStock.getQuote().getChangeInPercent().doubleValue();
            System.out.println(change);
            if(abs(change) >= bound) {
                String subject = "User: " + user.getUsername() + ". One of your stock has significant price change";
                String html = "<h3 >Stock Symbol: " +  yahooStock.getSymbol() + "<br style='padding-right: 5px'>Company: "
                        + yahooStock.getName() + "<br style='padding-right: 5px'>Change In Percent: " + change + "<br style='padding-right: 5px'>Current Price: $" + df2.format(price);
                html += "<br>You currently have " + stock.getQuantity() + " shares</h3>";
                long currentTimestamp = System.currentTimeMillis();
                if(user.getLastEmailEpochTime() != null){
                    if( (currentTimestamp - user.getLastEmailEpochTime()) > intervalInSec) { //the number is for interval
                        EmailUtil.sendEmailHtml(user.getEmail(), subject, html);
                    }
                }else{ // when user.getLastEmailEpochTime() is  null
                    EmailUtil.sendEmailHtml(user.getEmail(), subject, html);
                }
                user.setLastEmailEpochTime(currentTimestamp);
            }
        }
        return user;
    }
    public static boolean sendEmailAboutPortfolio(User user, List<Transaction> transactions, String portfolioName) {
        List<Stock> stocks = convertTransactionsToStock(transactions);
        if (stocks.size() > 0) {
            String[] symbols = new String[stocks.size()];
            for (int i = 0; i < stocks.size(); i++) {
                symbols[i] = stocks.get(i).getSymbol();
            }
            Map<String, yahoofinance.Stock> yahooStocks = null;
            try {
                yahooStocks = YahooFinance.get(symbols);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HashMap<String, Double> priceMap = new HashMap<>();
            double balance = 0;
            for (int i = 0; i < stocks.size(); i++) {
                yahoofinance.Stock yahooStock = yahooStocks.get(stocks.get(i).getSymbol());
                double price = yahooStock.getQuote().getPrice().doubleValue();
                balance += price * stocks.get(i).getQuantity();
                priceMap.put(stocks.get(i).getSymbol(), price);

            }
            System.out.println(priceMap);

            //ready for email

            String subject = "Portfolio Update";
            String html = "<h3>For your portfolio " + portfolioName + ". Your current balance is $" + df2.format(balance) + "</h3><br>";
            html += "<table style='text-align: center' border='1'> <tr> <th>Stock symbol</th> <th>Quantity</th> <th>Price</th> </tr>";
            for (Stock stock : stocks) {
                html += "<tr><td>";
                html += stock.getSymbol();
                html += "</td><td>";
                html += stock.getQuantity();
                html += "</td><td>$";
                html += df2.format(priceMap.get(stock.getSymbol()));
                html += "</td></tr>";
            }
            html += "</table>";

            if (sendEmailHtml(user.getEmail(), subject, html)) {
                return true;
            }
        }
        return false;
    }

    public static boolean sendEmailAboutNews(User user, News news) {
        String subject = "Daily News";

        String html = "<h1>" + news.getTitle() + "</h1><br>";
        html += "<h3>" + news.getDescription() + "</h3><br>";
        html += "<img src=' " + news.getUrlToImage() + " 'alt= 'imageURL'><br>";
        html += "<a href='" + news.getUrl() + "'>Click me to read more</a><br>";
        if (EmailUtil.sendEmailHtml(user.getEmail(), subject, html)){
            return true;
        }
        return false;
    }

    public static boolean sendEmailHtml(String recipientEmail, String subject, String html){
        if(recipientEmail == null){
            System.out.println("email is null");
            return false;
        }
        System.out.println("Prepare to send email");
        String logoURL = "https://i.imgur.com/bJmwANo.png";
        String projectName = "APEX Stocks";
        html += "<br><img src=' " + logoURL + " 'alt='logo' width='100'>";
        html += "<h1>" + projectName + "</h1>";
        Session session = createEmailSession();
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(subject);
            message.setContent(html, "text/html");
            Transport.send(message);
            System.out.println("Email sent successfully to " + recipientEmail);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sendEmail(String recipientEmail, String subject, String text){

        Session session = createEmailSession();
        // Message message = prepareMessage(session, myEmail, recipient);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);
            System.out.println("Email sent successfully");
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
