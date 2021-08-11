package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.Transaction;
import dev.team4.portfoliotracker.security.JwtUtility;
import dev.team4.portfoliotracker.services.TransactionService;
import dev.team4.portfoliotracker.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService txnService;

    @Autowired
    JwtUtility jwtUtility;

    @Autowired
    UserDetailsService userDetailsService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return new ResponseEntity<>(txnService.getAllTransactions(), HttpStatus.OK);
    }

    @GetMapping(value = "/{transactionId}", produces = "application/json")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("transactionId") int transactionId) {
        return new ResponseEntity<>(txnService.getTransactionById(transactionId), HttpStatus.OK);
    }

    @PostMapping(value = "/new", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction txn) {
        Transaction t = new Transaction(txn.getPortfolio(), txn.getStockSymbol().toUpperCase(), txn.getTransactionQuantity(), txn.getSharePrice(), txn.getDateTime());
        return new ResponseEntity<>(txnService.addTransaction(t), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{transactionId}", consumes = "application/json")
    public ResponseEntity<Map<String, Boolean>> updateTransaction(@RequestBody Transaction txn) {
        txnService.updateTransaction(txn.getPortfolio().getPortfolioId(), txn);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping(consumes = "application/json")
    public ResponseEntity<Map<String, Boolean>> deleteTransaction(@RequestBody Transaction transaction) {
        txnService.deleteTransaction(transaction);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
