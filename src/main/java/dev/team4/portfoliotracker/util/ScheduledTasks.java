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

//    @Autowired
//    UpdateUtil updateUtil;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//    @Scheduled(fixedRate = 5000)
//    public void reportCurrentTime() {
//        log.info("The time is now {}", dateFormat.format(new Date()));
//    }

//    @Scheduled(fixedRate = 10000) //1000 = 1sec
//    public void scheduledSendUpdateStockInfo() {
//        //System.out.println(new java.util.Date());
//        updateUtil.sendUpdateStockInfo();
//
//    }
//
//    @Scheduled(fixedRate = 10000) //1000 = 1sec
//    public void scheduledNotifyPriceChange() {
//        //System.out.println(new java.util.Date());
//
//        updateUtil.notifyPriceChange();
//    }

    //TODO Above code commented out to prevent compile errors, will need to be refactored

}

