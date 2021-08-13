package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.Balances;
import dev.team4.portfoliotracker.services.BalancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
