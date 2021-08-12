package dev.team4.portfoliotracker.models;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import dev.team4.portfoliotracker.services.ApiService;

public class Stock {
    //THIS CLASS IS NOT AN ENTITY IN THE DATABASE, DO NOT MAP TO A TABLE

    private String symbol;
    private int portfolioId;
    private double quantity;
    private BigDecimal avgBuyPrice;
    private BigDecimal currentPrice;
    private BigDecimal changePercentage;

    public Stock() {
        super();
    }

    public Stock(String symbol, int portfolioId) {
        this.symbol = symbol;
        this.portfolioId = portfolioId;
    }

    public Stock(Transaction transaction) {
        this.symbol = transaction.getStockSymbol();
        this.portfolioId = transaction.getPortfolio().getPortfolioId();
    }

    public Stock(String symbol,int portfolioId, double quantity, BigDecimal avgBuyPrice, BigDecimal currentPrice, BigDecimal changePercentage) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.avgBuyPrice = avgBuyPrice;
        this.currentPrice = currentPrice;
        this.changePercentage = changePercentage;
        this.portfolioId = portfolioId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(List<Transaction> transactions) {
        //Loop through a pre-processed list of transactions and sum the quantity column
        for (Transaction transaction : transactions) {
            this.quantity += transaction.getTransactionQuantity();
        }
    }

    public BigDecimal getAvgBuyPrice() {
        return avgBuyPrice;
    }

    public void setAvgBuyPrice(List<Transaction> transactions) {
        //the list of transactions passed in must only contain transactions for this stock
        double totalShareBuys = 0;
        BigDecimal totalSpent = BigDecimal.ZERO;
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionQuantity()>0) {
                totalShareBuys += transaction.getTransactionQuantity();
                totalSpent = totalSpent.add(transaction.getSharePrice().multiply(BigDecimal.valueOf(transaction.getTransactionQuantity())));
            }
        }

        this.avgBuyPrice = totalSpent.divide(BigDecimal.valueOf(totalShareBuys)).setScale(2, RoundingMode.CEILING);


    }

    public BigDecimal getCurrentPrice() {
        //make a call to the setPrice method for updated info
        this.setCurrentPrice();
        return currentPrice;
    }

    public void setCurrentPrice() {
        //Calls to web scraper for updated pricing
        ApiService apiService = new ApiService();
        String[] symbolInput = new String[]{this.symbol};
        BigDecimal apiOut = apiService.getSymbolPrices(symbolInput).get(this.symbol);
        this.currentPrice = apiOut.setScale(2, RoundingMode.CEILING);
    }

    public BigDecimal getChangePercentage() {
        this.setChangePercentage();
        return changePercentage;
    }

    public void setChangePercentage() {
        ApiService apiService = new ApiService();
        String[] symbolInput = new String[]{this.symbol};
        BigDecimal apiOut = apiService.getSymbolPnl(symbolInput).get(this.symbol);
        MathContext m = new MathContext(2);
        this.changePercentage = apiOut.round(m);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return portfolioId == stock.portfolioId && Double.compare(stock.quantity, quantity) == 0 && Objects.equals(symbol, stock.symbol) && Objects.equals(avgBuyPrice, stock.avgBuyPrice) && Objects.equals(currentPrice, stock.currentPrice) && Objects.equals(changePercentage, stock.changePercentage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, quantity, avgBuyPrice, currentPrice, changePercentage);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                ", quantity=" + quantity +
                ", avgBuyPrice=" + avgBuyPrice +
                ", currentPrice=" + currentPrice +
                ", changePercentage=" + changePercentage +
                '}';
    }
}
