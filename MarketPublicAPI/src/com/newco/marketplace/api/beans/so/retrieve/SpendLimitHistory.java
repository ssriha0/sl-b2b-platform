package com.newco.marketplace.api.beans.so.retrieve;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing response information
 * of  Spend Limit Increases for
 * SORetrieveSpendLimitService
 * @author Infosys
 *
 */
@XStreamAlias("spendLimitHistory")
public class SpendLimitHistory {
	
	
	@XStreamAlias("date")
	private String date; 
	
	@XStreamAlias("newPrice")
	private String newPrice;
	
	@XStreamAlias("oldPrice")
	private String oldPrice;
	
	@XStreamAlias("reason")
	private String reason;
	
	@XStreamAlias("user")
	private String user;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	

	
	public String getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(String newPrice) {
		this.newPrice = newPrice;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(String oldPrice) {
		this.oldPrice = oldPrice;
	}
	
	
}
