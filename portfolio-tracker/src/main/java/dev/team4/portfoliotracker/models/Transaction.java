package dev.team4.portfoliotracker.models;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Objects;

@Component
@Entity
@Table(name = "stock-transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int transactionId;

    @Column(name = "share_amount")
    private double shareAmount;

    @Column(name = "share_price")
    private double sharePrice;

    public Transaction() {
    }

    public Transaction(double shareAmount, double sharePrice) {
        this.shareAmount = shareAmount;
        this.sharePrice = sharePrice;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public double getShareAmount() {
        return shareAmount;
    }

    public void setShareAmount(double shareAmount) {
        this.shareAmount = shareAmount;
    }

    public double getSharePrice() {
        return sharePrice;
    }

    public void setSharePrice(double sharePrice) {
        this.sharePrice = sharePrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionId == that.transactionId && Double.compare(that.shareAmount, shareAmount) == 0 && Double.compare(that.sharePrice, sharePrice) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, shareAmount, sharePrice);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", shareAmount=" + shareAmount +
                ", sharePrice=" + sharePrice +
                '}';
    }
}
