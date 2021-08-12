package dev.team4.portfoliotracker.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import yahoofinance.YahooFinance;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private UpdateNews updateNews;

    /**
     * Author: David Garcia
     * scheduledReceiveDailyNews calls the news API every hour and persists news data to database
     */
    @Scheduled(fixedRate = 3600000) // fixed rate set to an hour
    public void scheduledReceiveDailyNews(){
        updateNews.updateDailyNews();
    }

}
