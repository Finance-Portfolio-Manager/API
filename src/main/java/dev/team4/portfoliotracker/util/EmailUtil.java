package dev.team4.portfoliotracker.util;

import dev.team4.portfoliotracker.models.News;
import dev.team4.portfoliotracker.models.Stock;
import dev.team4.portfoliotracker.models.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class EmailUtil {
    private static String myEmail = "revature.team.3@gmail.com";
    private static String myPassword = "team3casino";

    public static Session createEmailSession(){
        System.out.println("Prepare to send email....");
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

    public static boolean sendEmail(String recipient, String subject, String text){

        Session session = createEmailSession();
        // Message message = prepareMessage(session, myEmail, recipient);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
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

    public static boolean sendEmailAboutPortfolio(User user, List<Stock> stocks, double balance, HashMap<String, Double> priceMap, String name){

        System.out.println(user);
        System.out.println("sending Portfolio Update email to " + user.getEmail());
        Session session = createEmailSession();
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject("Portfolio Update");

            String html = "<h1>For your portfolio " + name + " Your current balance is " + balance + "</h1><br>";
            html += "<table style='text-align: center'> <tr> <th>Stock symbol</th> <th>Quantity</th> <th>Price</th> </tr>";

            for(Stock stock: stocks){
                html += "<tr><td>";
                html += stock.getSymbol();
                html += "</td><td>";
                html += stock.getQuantity();
                html += "</td><td>";
                html += priceMap.get(stock.getSymbol());
                html += "</td></tr>";
            }
            html += "</table>";
            String logoURL = "https://i.imgur.com/bJmwANo.png";
            String projectName = "apexstocks";
            html += "<br><br><h2>" + projectName + "</h2>";
            html += "<br><apexstocks><img src=' " + logoURL + " 'alt='logo' width='400'>";
            message.setContent(html, "text/html");
            Transport.send(message);
            System.out.println("Email sent successfully to " + user.getEmail());
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sendEmailAboutNews(User user, News news){
        Session session = createEmailSession();
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject("Daily News");

            String html = "<h1>" + news.getTitle() + "</h1><br>";
            html += "<h3>" + news.getDescription() + "</h3><br>";
            html += "<img src=' " + news.getUrlToImage() + " 'alt= 'imageURL'><br>";
            html += "<a href='" + news.getUrl() + "'>Click me to read more</a>";

            message.setContent(html, "text/html");
            Transport.send(message);
            System.out.println("Email sent successfully");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
