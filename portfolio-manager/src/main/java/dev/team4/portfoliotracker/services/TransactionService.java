package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.models.Transaction;

import java.util.List;

public interface TransactionService {

    /**
     * Gets all transactions in database
     * @return all transactions
     */
    List<Transaction> getAllTransactions();
    /**
     * Gets all transactions matching userId
     * @param userId
     * @return List of transactions matching userId
     */
    List<Transaction> getAllTransactionsByUserId(int userId);
    /**
     * Gets Transaction matching transactionId
     * @param transactionId
     * @return Transaction matching transactionId
     */
    Transaction getTransactionById(int transactionId);
    /**
     * Adds txn to database
     * @param txn
     * @return added Transaction
     */
    Transaction addTransaction(Transaction txn);

    /**
     * Updates Transaction matching id with transactionId
     * @param id
     * @param txn
     */
    void updateTransaction(int id, Transaction txn);

    /**
     * Deletes Transaction matching transactionId
     * @param transactionId
     */
    void deleteTransaction(int transactionId);
}
