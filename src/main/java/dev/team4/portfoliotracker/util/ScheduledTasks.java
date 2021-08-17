package dev.team4.portfoliotracker.util;

import java.text.SimpleDateFormat;

import dev.team4.portfoliotracker.services.UserDetailsService;
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
    // private UpdateNews updateNews;

    @Autowired
    UserDetailsService userDetailsService;


    @Scheduled(cron = "0 0 0 * * ?") // happens at midnight everyday
    public void scheduledBalanceStore() {
        balanceUtil.storeBalances();
    }

    @Autowired
    private UpdateNews updateNews;

//    /**
//     * Author: David Garcia
//     * scheduledReceiveDailyNews calls the news API every hour and persists news data to database
//     */
//    @Scheduled(fixedRate = 3600000) // fixed rate set to an hour
//    public void scheduledReceiveDailyNews(){
//        log.info("running update daily news");
//        updateNews.updateDailyNews();
//    }

//
//    //fuming
//    @Scheduled(fixedRate = 86400000) //1000 = 1sec
//    public void scheduledSendUpdateStockInfo() {
//        //System.out.println(new java.util.Date());
//        updateUtil.sendAllAboutPortfolio();
//
//    }
//
//    //fuming
//    @Scheduled(fixedRate = 86400000) //1000 = 1sec
//    public void scheduledSendUpdateNews() {
//        updateUtil.sendUpdateNews();
//
//    }
//
//    //fuming
//    @Scheduled(fixedRate = 3600000) //1000 = 1sec
//    public void scheduledNotifyPriceChange() {
//        updateUtil.notifyPriceChange(0.1, 1); //should be 1, 86400
//    }
//

}