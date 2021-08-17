package dev.team4.portfoliotracker.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.IOException;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    UpdateUtil updateUtil;

    @Autowired
    BalanceUtil balanceUtil;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private UpdateNews updateNews;

    /**
     * Author: David Garcia
     * scheduledReceiveDailyNews calls the news API every hour and persists news data to database
     */
    @Scheduled(fixedRate = 3600000) // fixed rate set to an hour
    public void scheduledReceiveDailyNews(){
        log.info("running update daily news");
        updateNews.updateDailyNews();
    }


    @Scheduled(cron = "0 0 0 * * ?") // happens at midnight everyday
    public void scheduledBalanceStore() {
        balanceUtil.storeBalances();
    }


//    @Scheduled(fixedRate = 5000)
//    public void reportCurrentTime() {
//        log.info("The time is now {}", dateFormat.format(new Date()));
//    }


    @Scheduled(fixedRate = 86400000) //1000 = 1sec
    public void scheduledSendUpdateStockInfo() {
        //System.out.println(new java.util.Date());
        updateUtil.sendUpdateStockInfo();

    }

    @Scheduled(fixedRate = 3600000) //1000 = 1sec
    public void scheduledNotifyPriceChange() {
        //System.out.println(new java.util.Date());

        updateUtil.notifyPriceChange();
    }


}