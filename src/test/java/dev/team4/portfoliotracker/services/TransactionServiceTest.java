package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.exceptions.BadRequestException;
import dev.team4.portfoliotracker.models.Portfolio;
import dev.team4.portfoliotracker.models.Transaction;
import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TransactionServiceTest {

    @InjectMocks
    TransactionService transactionService = new TransactionService();

    @Mock
    TransactionRepository transactionRepository;

    @Test
    void getAllTransactionsByPortfolio() {
        LocalDateTime dateTime = null;
        User u = new User("email", "username", "pass");
        Portfolio p = new Portfolio();
        p.setName("Portfolio 1");
        p.setUser(u);
        p.setPublic(false);
        Transaction txn1 = new Transaction(p, "TSLA", 5.32, BigDecimal.valueOf(690.32), dateTime);
        Transaction txn2 = new Transaction(p, "MSFT", 3.32, BigDecimal.valueOf(32.41), dateTime);
        Transaction txn3 = new Transaction(p, "IBM", 4, BigDecimal.valueOf(6490.80), dateTime);
        List<Transaction> txns = Arrays.asList(txn1,txn2,txn3);
        u.setUserId(2);
        p.setTransactions(txns);
        doReturn(txns).when(transactionRepository).findTransactionsByPortfolio(p);
        assertEquals(transactionService.getAllTransactionsByPortfolio(p), txns);

    }

    @Test
    void getAllTransactionsByInvalidPortfolioShouldFail() {
        User u = new User("email", "username", "pass");
        Portfolio p = new Portfolio();
        p.setName("Portfolio 1");
        p.setUser(u);
        p.setPublic(false);
        doThrow(BadRequestException.class).when(transactionRepository).findTransactionsByPortfolio(p);
        assertThrows(BadRequestException.class, () -> {
            transactionService.getAllTransactionsByPortfolio(p);
        });
    }

    @Test
    void getAllTransactions() {
        LocalDateTime dateTime = null;
        User u = new User("email", "username", "pass");
        Portfolio p = new Portfolio();
        p.setName("Portfolio 1");
        p.setUser(u);
        p.setPublic(false);
        Transaction txn1 = new Transaction(p, "TSLA", 5.32, BigDecimal.valueOf(690.32), dateTime);
        Transaction txn2 = new Transaction(p, "MSFT", 3.32, BigDecimal.valueOf(32.41), dateTime);
        Transaction txn3 = new Transaction(p, "IBM", 4, BigDecimal.valueOf(6490.80), dateTime);
        List<Transaction> txns = Arrays.asList(txn1,txn2,txn3);
        doReturn(txns).when(transactionRepository).findAll();
        assertEquals(transactionService.getAllTransactions(), txns);
    }

    @Test
    void getTransactionById() {
        LocalDateTime dateTime = null;
        User u = new User("email", "username", "pass");
        Portfolio p = new Portfolio();
        p.setName("Portfolio 1");
        p.setUser(u);
        p.setPublic(false);
        Transaction txn1 = new Transaction(p, "TSLA", 5.32, BigDecimal.valueOf(690.32), dateTime);
        int tId = txn1.getTransactionId();
        doReturn(txn1).when(transactionRepository).findTransactionByTransactionId(tId);
        assertEquals(transactionService.getTransactionById(tId), txn1);
    }

    @Test
    void addTransaction() {
        LocalDateTime dateTime = null;
        User u = new User("email", "username", "pass");
        Portfolio p = new Portfolio();
        p.setName("Portfolio 1");
        p.setUser(u);
        p.setPublic(false);
        Transaction txn1 = new Transaction(p, "TSLA", 5.32, BigDecimal.valueOf(690.32), dateTime);
        doReturn(txn1).when(transactionRepository).save(txn1);
        assertEquals(transactionService.addTransaction(txn1), txn1);
    }

    @Test
    void updateTransaction() {
        LocalDateTime dateTime = LocalDateTime.now();
        User u = new User("email", "username", "pass");
        Portfolio p = new Portfolio();
        p.setName("Portfolio 1");
        p.setUser(u);
        p.setPublic(false);
        Transaction txn1 = new Transaction(p, "TSLA", 5.32, BigDecimal.valueOf(690.32), dateTime);
        Transaction txn2 = new Transaction(p, "MSFT", 3.32, BigDecimal.valueOf(32.41), dateTime);
        doReturn(txn1).when(transactionRepository).findTransactionByTransactionId(2);
        transactionService.updateTransaction(2, txn2);
        assertEquals(txn2, txn1);
    }

    @Test
    void deleteTransaction() {
        LocalDateTime dateTime = null;
        User u = new User("email", "username", "pass");
        Portfolio p = new Portfolio();
        p.setName("Portfolio 1");
        p.setUser(u);
        p.setPublic(false);
        Transaction txn1 = new Transaction(p, "TSLA", 5.32, BigDecimal.valueOf(690.32), dateTime);
        doNothing().when(transactionRepository).delete(txn1);
        transactionService.deleteTransaction(txn1);
        verify(transactionRepository, times(1)).delete(txn1);
    }
}