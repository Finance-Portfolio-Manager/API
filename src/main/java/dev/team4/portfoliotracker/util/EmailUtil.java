package dev.team4.portfoliotracker.util;

import dev.team4.portfoliotracker.models.Stock;
import dev.team4.portfoliotracker.models.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class EmailUtil {
    public static boolean sendEmail(String recipient, String subject, String text){

        System.out.println("Prepare to send email....");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myEmail = "revature.team.3@gmail.com";
        String myPassword = "team3casino";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, myPassword);
            }
        });
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

    public static boolean sendEmailAboutStock(User user, List<Stock> stocks, HashMap<String, Double> priceMap){

        System.out.println("Prepare to send email....");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myEmail = "revature.team.3@gmail.com";
        String myPassword = "team3casino";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, myPassword);
            }
        });
        // Message message = prepareMessage(session, myEmail, recipient);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject("Here are some stock information");

            String html = "<table style='text-align: center'> <tr> <th>Stock symbol</th> <th>Quantity</th> <th>Price</th> </tr>";

            for(Stock stock: stocks){
                html += "<tr><td>";
                html += stock.getStockSymbol();
                html += "</td><td>";
                html += stock.getStockQuantity();
                html += "</td><td>";
                html += priceMap.get(stock.getStockSymbol());
                html += "</td></tr>";
            }
            html += "</table>";

            //message.setContent("<h1> we love java </h1>", "text/html");
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
