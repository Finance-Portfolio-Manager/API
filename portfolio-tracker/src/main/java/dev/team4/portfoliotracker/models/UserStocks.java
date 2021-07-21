package dev.team4.portfoliotracker.models;

import javax.persistence.*;

public class UserStocks {
	
	@Column
	private int userID;
	
	@Column
	private int stockID;
	
	@Column
	private int stockQuantity;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getStockID() {
		return stockID;
	}

	public void setStockID(int stockID) {
		this.stockID = stockID;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

}
