package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.exceptions.BadRequestException;
import dev.team4.portfoliotracker.models.Transaction;
import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TransactionServiceImplTest {

    @InjectMocks
    TransactionService transactionService = new TransactionServiceImpl();

    @Mock
    TransactionRepository transactionRepository;

    @Test
    void getAllTransactionsByUserId() {
        Transaction txn1 = new Transaction(201, "TSLA", 5.32, 690.32, "test1", true);
        Transaction txn2 = new Transaction(201, "MSFT", 150.6752, 32.41, "test2", true);
        Transaction txn3 = new Transaction(201, "IBM", 13500, 6490.80, "test3", true);
        List<Transaction> txns = Arrays.asList(txn1,txn2,txn3);

        doReturn(txns).when(transactionRepository).findAllTransactionsByUserId(201);
        assertEquals(transactionService.getAllTransactionsByUserId(201), txns);

    }

    @Test
    void getAllTransactionsByInvalidUserIdShouldFail() {

        doThrow(BadRequestException.class).when(transactionRepository).findAllTransactionsByUserId(201);
        assertThrows(BadRequestException.class, () -> {
            transactionService.getAllTransactionsByUserId(201);
        });
    }

    @Test
    void getAllTransactions() {
        Transaction txn1 = new Transaction(201, "TSLA", 5.32, 690.32, "test1", true);
        Transaction txn2 = new Transaction(201, "MSFT", 150.6752, 32.41, "test2", true);
        Transaction txn3 = new Transaction(201, "IBM", 13500, 6490.80, "test3", true);
        List<Transaction> txns = Arrays.asList(txn1,txn2,txn3);

        doReturn(txns).when(transactionRepository).findAll();
        assertEquals(transactionService.getAllTransactions(), txns);
    }

    @Test
    void getTransactionById() {
        Transaction txn1 = new Transaction(201, "TSLA", 5.32, 690.32, "test1", true);

        doReturn(txn1).when(transactionRepository).findByTransactionId(15);
        assertEquals(transactionService.getTransactionById(15), txn1);
    }

    @Test
    void addTransaction() {
        Transaction txn1 = new Transaction(201, "TSLA", 5.32, 690.32, "test1", true);

        doReturn(txn1).when(transactionRepository).save(txn1);
        assertEquals(transactionService.addTransaction(txn1), txn1);
    }

    @Test
    void updateTransaction() {
        Transaction txn1 = new Transaction(201, "TSLA", 5.32, 690.32, "test1", true);
        Transaction txn2 = new Transaction(201, "MSFT", 150.6752, 32.41, "test2", true);

        doReturn(txn1).when(transactionRepository).findByTransactionId(2);
        transactionService.updateTransaction(2, txn2);
        assertEquals(txn2, txn1);
    }

    @Test
    void deleteTransaction() {
        Transaction txn1 = new Transaction(201, "TSLA", 5.32, 690.32, "test1", true);

        doNothing().when(transactionRepository).deleteById(201);
        transactionService.deleteTransaction(201);
        verify(transactionRepository, times(1)).deleteById(201);
    }
}