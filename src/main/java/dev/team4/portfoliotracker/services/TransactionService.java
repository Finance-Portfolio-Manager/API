package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.models.Portfolio;
import dev.team4.portfoliotracker.models.Transaction;
import dev.team4.portfoliotracker.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.sampled.Port;
import java.util.List;

@Service
@Transactional
public class TransactionService {

    @Autowired
    TransactionRepository txnRepo;

    public List<Transaction> getAllTransactionsByPortfolio(Portfolio portfolio) {
        return txnRepo.findTransactionsByPortfolio(portfolio);
    }

    public List<Transaction> getAllTransactions() {
        return txnRepo.findAll();
    }

    public Transaction getTransactionById(int transactionId) {
        return txnRepo.findTransactionByTransactionId(transactionId);
    }

    public Transaction addTransaction(Transaction txn) {
        return txnRepo.save(txn);
    }

    public void updateTransaction(int id, Transaction transaction) {
        Transaction txn = txnRepo.findTransactionByTransactionId(id);
        txn.setPortfolio(transaction.getPortfolio());
        txn.setStockSymbol(transaction.getStockSymbol());
        txn.setTransactionQuantity(transaction.getTransactionQuantity());
        txn.setSharePrice(transaction.getSharePrice());
        txn.setDateTime(transaction.getDateTime());
        txnRepo.save(transaction);
    }

    public List<Transaction> getTransactionsByPortfolioAndStockSymbol(Portfolio portfolio, String stockSymbol){
        return txnRepo.findTransactionsByPortfolioAndStockSymbol(portfolio, stockSymbol);
    }

    public void deleteTransaction(Transaction transaction) {
        txnRepo.delete(transaction);
    }
}
