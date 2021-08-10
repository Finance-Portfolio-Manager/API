package dev.team4.portfoliotracker.models;

import java.math.BigDecimal;
import java.math.MathContext;
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
    private double changePercentage;

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
        this.quantity = transaction.getShareAmount();
    }

    public Stock(String symbol,int portfolioId, double quantity, BigDecimal avgBuyPrice, BigDecimal currentPrice, double changePercentage) {
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
            this.quantity += transaction.getShareAmount();
        }
    }

    public BigDecimal getAvgBuyPrice() {
        return avgBuyPrice;
    }

    public void setAvgBuyPrice(BigDecimal avgBuyPrice) {
        this.avgBuyPrice = avgBuyPrice;
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
        MathContext m = new MathContext(2);
        this.currentPrice = apiOut.round(m);
    }

    public double getChangePercentage() {
        return changePercentage;
    }

    public void setChangePercentage(double changePercentage) {
        this.changePercentage = changePercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return portfolioId == stock.portfolioId && Double.compare(stock.quantity, quantity) == 0 && Double.compare(stock.changePercentage, changePercentage) == 0 && Objects.equals(symbol, stock.symbol) && Objects.equals(avgBuyPrice, stock.avgBuyPrice) && Objects.equals(currentPrice, stock.currentPrice);
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
