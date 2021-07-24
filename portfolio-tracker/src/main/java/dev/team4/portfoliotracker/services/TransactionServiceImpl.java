package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.models.Transaction;
import dev.team4.portfoliotracker.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository txnRepo;



    @Override
    public List<Transaction> getAllTransactionsByUserId(int userId) {
        return txnRepo.findAllTransactionsByUserId(userId);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return txnRepo.findAll();
    }

    @Override
    public Transaction getTransactionById(int transactionId) {
        return txnRepo.findByTransactionId(transactionId);
    }

    @Override
    public Transaction addTransaction(Transaction txn) {
        return txnRepo.save(txn);
    }

    @Override
    public void updateTransaction(int transactionId, int userId, double shareAmount, double sharePrice, String note) {
        Transaction txn = txnRepo.findByTransactionId(transactionId);
        txn.setShareAmount(shareAmount);
        txn.setSharePrice(sharePrice);
        txn.setNote(note);
    }

    @Override
    public void deleteTransaction(int transactionId) {
        txnRepo.deleteById(transactionId);
    }
}
