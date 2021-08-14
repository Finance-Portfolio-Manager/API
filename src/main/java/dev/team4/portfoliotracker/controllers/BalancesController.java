package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.Balances;
import dev.team4.portfoliotracker.services.BalancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/balances")
public class BalancesController {

    @Autowired
    BalancesService balancesService;

    /**
     * Author: Aaron Parker
     *
     * @param portfolioId - the id of the portfolio in which we are getting the historical balances
     * @return list of balances to be graphed
     */
    @GetMapping(value = "/{portfolioId}")
    public ResponseEntity<List<Balances>> getAllBalancesByPortfolioId(@PathVariable("portfolioId") int portfolioId) {
        return new ResponseEntity<>(balancesService.getAllBalancesByPortfolioId(portfolioId), HttpStatus.OK);
    }

    @GetMapping(value = "/daily/{portfolioId}")
    public ResponseEntity<List<Balances>> getDailyBalancesByPortfolioId(@PathVariable("portfolioId") int portfolioId) {
        int rotate = 1;
        Balances balance = new Balances();
        balance.setBalance(new BigDecimal(0));
        balance.setBalanceType("c");
        Balances balance2 = new Balances();
        balance2.setBalance(new BigDecimal(0));
        balance2.setBalanceType("i");
        LocalDateTime current = null;
        int indexTracker = 0;
        List<Balances> list = balancesService.getAllBalancesByPortfolioId(portfolioId);
        List<Balances> filteredList = new ArrayList<>();
        while(filteredList.size()<14 && indexTracker<list.size()){
            if(current==null || current.getDayOfMonth() != list.get(indexTracker).getDate().getDayOfMonth()){
                current = list.get(indexTracker).getDate();
                for(int i = 0;i<2;i++){
                    filteredList.add(list.get(indexTracker));
                    indexTracker++;
                }
            }
            indexTracker++;
        }
        while(filteredList.size()<14){
            filteredList.add(balance);

            filteredList.add(balance2);
        }
        return new ResponseEntity<>(filteredList, HttpStatus.OK);
    }

    /**
     * Author: Aaron Parker
     *
     * @param userId - the id of the user of which we are getting the historical balances
     * @return list of balances to be graphed
     */
    @GetMapping(value = "userId")
    public ResponseEntity<List<Balances>> getAllBalancesByUserId(@PathVariable("userId") int userId) {
        return new ResponseEntity<>(balancesService.getAllBalancesByUserId(userId), HttpStatus.OK);
    }

}
