package dev.team4.portfoliotracker.models;

import com.sun.istack.NotNull;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int transactionId;

    // maybe we don't need this
//    @Column(name = "user_Id")
//    private int userId;

//    @Column(name = "ticker")
//    private String ticker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @NotNull
    @Column(name = "stock_symbol")
    private String stockSymbol;

//    @Column(name = "share_amount")
//    private double shareAmount;
//
//    @Column(name = "share_price")
//    private double sharePrice;

    @NotNull
    @Column(name = "transaction_amount")
    private double shareAmount;

    @NotNull
    @Column(name = "transaction_price")
    private BigDecimal sharePrice;

    @NotNull
    @Column(name = "date_time")
    private LocalDateTime dateTime;


//    @Column(name = "note")
//    private String note;
//
//    @Transient
//    private String token;
//
//    @Transient
//    private boolean isBuy;

    public Transaction() {
    }

    public Transaction(Portfolio portfolio, String stockSymbol, double shareAmount, BigDecimal sharePrice, LocalDateTime dateTime) {
        this.portfolio = portfolio;
        this.stockSymbol = stockSymbol;
        this.shareAmount = shareAmount;
        this.sharePrice = sharePrice;
        this.dateTime = dateTime;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public double getShareAmount() {
        return shareAmount;
    }

    public void setShareAmount(double shareAmount) {
        this.shareAmount = shareAmount;
    }

    public BigDecimal getSharePrice() {
        return sharePrice;
    }

    public void setSharePrice(BigDecimal sharePrice) {
        this.sharePrice = sharePrice;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionId == that.transactionId && Double.compare(that.shareAmount, shareAmount) == 0 && portfolio.equals(that.portfolio) && stockSymbol.equals(that.stockSymbol) && sharePrice.equals(that.sharePrice) && dateTime.equals(that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, portfolio, stockSymbol, shareAmount, sharePrice, dateTime);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", portfolio=" + portfolio +
                ", stockSymbol='" + stockSymbol + '\'' +
                ", shareAmount=" + shareAmount +
                ", sharePrice=" + sharePrice +
                ", dateTime=" + dateTime +
                '}';
    }
}
