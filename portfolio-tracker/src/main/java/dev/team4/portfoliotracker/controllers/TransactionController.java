package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.Transaction;
import dev.team4.portfoliotracker.services.TransactionService;
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
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Transaction>> getAllTransactions(HttpServletRequest request, @PathVariable("userId") int userId) {

        List<Transaction> transactions = new ArrayList<>();

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping(value = "/{transactionId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Transaction> getTransactionById(HttpServletRequest request, @PathVariable("userId") int userId) {

        Transaction transaction = new Transaction();

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Transaction> addTransaction(HttpServletRequest request, @PathVariable("userId") int userId) {

        Transaction transaction = new Transaction();

        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{transactionId}", consumes = "application/json")
    public ResponseEntity<Map<String, Boolean>> updateTransaction(HttpServletRequest request, @PathVariable("userId") int userId, @PathVariable("transactionId") int transactionId) {

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{transactionId}", consumes = "application/json")
    public ResponseEntity<Map<String, Boolean>> deleteTransaction(HttpServletRequest request, @PathVariable("userId") int userId, @PathVariable("transactionId") int transactionId) {

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
