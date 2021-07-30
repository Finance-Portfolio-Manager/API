package dev.team4.portfoliotracker.models;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Objects;

@Component
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int transactionId;

    // maybe we don't need this
    @Column(name = "user_Id")
    private int userId;

    @Column(name = "ticker")
    private String ticker;

    @Column(name = "share_amount")
    private double shareAmount;

    @Column(name = "share_price")
    private double sharePrice;

    @Column(name = "note")
    private String note;

    @Transient
    private String token;

    @Transient
    private boolean isBuy;

    public Transaction() {
    }

    public Transaction(int userId, String ticker, double shareAmount, double sharePrice, String note, boolean isBuy) {
        this.userId = userId;
        this.ticker = ticker;
        this.shareAmount = shareAmount;
        this.sharePrice = sharePrice;
        this.note = note;
        this.isBuy = isBuy;
    }

    public Transaction(int userId, String ticker, double shareAmount, double sharePrice, String note, String token, boolean isBuy) {
        this.userId = userId;
        this.ticker = ticker;
        this.shareAmount = shareAmount;
        this.sharePrice = sharePrice;
        this.note = note;
        this.token = token;
        this.isBuy = isBuy;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getToken() {
        return token;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionId == that.transactionId && userId == that.userId && Double.compare(that.shareAmount, shareAmount) == 0 && Double.compare(that.sharePrice, sharePrice) == 0 && isBuy == that.isBuy && Objects.equals(ticker, that.ticker) && Objects.equals(note, that.note) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, userId, ticker, shareAmount, sharePrice, note, token, isBuy);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", userId=" + userId +
                ", ticker='" + ticker + '\'' +
                ", shareAmount=" + shareAmount +
                ", sharePrice=" + sharePrice +
                ", note='" + note + '\'' +
                ", token='" + token + '\'' +
                ", isBuy=" + isBuy +
                '}';
    }
}
