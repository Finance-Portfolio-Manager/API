package dev.team4.portfoliotracker.models;

import javax.persistence.*;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Entity
@Table(name = "user_stocks")
public class Stock {
	
	@Column(name = "user_id")
	private int userId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stock_id")
	private int stockId;
	
	@Column(name = "stock_symbol")
	private String stockSymbol;
	
	@Column(name = "stock_quantity")
	private double stockQuantity;

	public Stock() {
	}

	public Stock(int userId, String stockSymbol, double stockQuantity) {
		super();
		this.userId = userId;
		this.stockSymbol = stockSymbol;
		this.stockQuantity = stockQuantity;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public double getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(double stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Stock stock = (Stock) o;
		return userId == stock.userId && stockId == stock.stockId && 
				stockSymbol.equalsIgnoreCase(stock.stockSymbol) && 
				Double.compare(stock.stockQuantity, stockQuantity) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, stockId, stockQuantity);
	}

	@Override
	public String toString() {
		return "\nStock: \n" +
				"userId: " + userId + "\n" +
				"stockId: " + stockId + "\n" +
				"stockSymbol: " + stockSymbol + "\n" +
				"stockQuantity: " + stockQuantity;
	}

}