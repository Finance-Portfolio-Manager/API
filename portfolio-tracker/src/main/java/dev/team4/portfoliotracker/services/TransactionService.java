package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.models.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> getAllTransactions();
    List<Transaction> getAllTransactionsByUserId(int userId);
    Transaction getTransactionById(int transactionId);
    Transaction addTransaction(Transaction txn);
    void updateTransaction(int transactionId, int userId, double shareAmount, double sharePrice, String note);
    void deleteTransaction(int transactionId);
}
