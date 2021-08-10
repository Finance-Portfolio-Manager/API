package dev.team4.portfoliotracker.models;

import java.math.BigDecimal;
import java.util.Objects;
import dev.team4.portfoliotracker.services.ApiService;

public class Stock {
    //THIS CLASS IS NOT AN ENTITY IN THE DATABASE, DO NOT MAP TO A TABLE

    private String symbol;
    private BigDecimal quantity;
    private BigDecimal avgBuyPrice;
    private BigDecimal currentPrice;
    private double changePercentage;

    Stock() {
        super();
    }

    Stock(String symbol) {
        this.symbol = symbol;
    }

    Stock(String symbol, BigDecimal quantity, BigDecimal avgBuyPrice, BigDecimal currentPrice, double changePercentage) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.avgBuyPrice = avgBuyPrice;
        this.currentPrice = currentPrice;
        this.changePercentage = changePercentage;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAvgBuyPrice() {
        return avgBuyPrice;
    }

    public void setAvgBuyPrice(BigDecimal avgBuyPrice) {
        this.avgBuyPrice = avgBuyPrice;
    }

    public BigDecimal getCurrentPrice() {
        //make a call to the API for stock price by symbol
        ApiService apiService = new ApiService();
        String[] symbolInput = new String[]{this.symbol};
        BigDecimal apiOut = apiService.getSymbolPrices(symbolInput).get(this.symbol);
        return apiOut;
    }

    public void setCurrentPrice(double currentPrice) {
        ApiService apiService = new ApiService();
        String[] symbolInput = new String[]{this.symbol};
        BigDecimal apiOut = apiService.getSymbolPrices(symbolInput).get(this.symbol);
        this.currentPrice = apiOut;
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
        return Double.compare(stock.changePercentage, changePercentage) == 0 && Objects.equals(symbol, stock.symbol) && Objects.equals(quantity, stock.quantity) && Objects.equals(avgBuyPrice, stock.avgBuyPrice) && Objects.equals(currentPrice, stock.currentPrice);
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
