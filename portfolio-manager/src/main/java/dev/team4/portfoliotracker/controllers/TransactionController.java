package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.Transaction;
import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.security.JwtUtility;
import dev.team4.portfoliotracker.services.TransactionService;
import dev.team4.portfoliotracker.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    public ResponseEntity<List<Transaction>> getAllTransactions(@RequestParam(value = "userId", required = false) Integer userId) {
        if (userId!=null) {
            return new ResponseEntity<>(txnService.getAllTransactionsByUserId(userId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(txnService.getAllTransactions(), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{transactionId}", produces = "application/json")
    public ResponseEntity<Transaction> getTransactionById(HttpServletRequest request, @PathVariable("transactionId") int transactionId) {
//        int userId = request.getAttribute("userId");
        return new ResponseEntity<>(txnService.getTransactionById(transactionId), HttpStatus.OK);
    }

    @PostMapping(value = "/new", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Transaction> addTransaction(HttpServletRequest request, @RequestBody Transaction txn) {
        User user = userDetailsService.getUserByUsername(jwtUtility.getUsernameFromToken(txn.getToken()));
        Transaction t = new Transaction(user.getUserId(), txn.getTicker().toUpperCase(), txn.getShareAmount(), txn.getSharePrice(), txn.getNote(), txn.isBuy());
        return new ResponseEntity<>(txnService.addTransaction(t), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{transactionId}", consumes = "application/json")
    public ResponseEntity<Map<String, Boolean>> updateTransaction(HttpServletRequest request, @PathVariable("userId") int userId, @PathVariable("transactionId") int transactionId) {

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{transactionId}")
    public ResponseEntity<Map<String, Boolean>> deleteTransaction(HttpServletRequest request, @PathVariable("transactionId") int transactionId) {
//        int userId = request.getAttribute("userId");
        txnService.deleteTransaction(transactionId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
