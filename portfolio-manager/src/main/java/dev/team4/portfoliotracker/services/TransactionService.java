package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.models.Transaction;

import java.util.List;

public interface TransactionService {

    /**
     * @return
     */
    List<Transaction> getAllTransactions();
    /**
     * @param userId
     * @return
     */
    List<Transaction> getAllTransactionsByUserId(int userId);
    /**
     * @param transactionId
     * @return
     */
    Transaction getTransactionById(int transactionId);
    /**
     * @param txn
     * @return
     */
    Transaction addTransaction(Transaction txn);
    /**
     * @param transactionId
     * @param userId
     * @param shareAmount
     * @param sharePrice
     * @param note
     */
    void updateTransaction(int transactionId, int userId, double shareAmount, double sharePrice, String note);
    /**
     * @param transactionId
     */
    void deleteTransaction(int transactionId);
}
