package com.newco.marketplace.translator.dto;

public class BuyerCredentials extends BaseDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1900749443299358641L;
	private Integer buyerID;
	private String username;
	private String password;
	
	public Integer getBuyerID() {
		return buyerID;
	}
	public void setBuyerID(Integer buyerID) {
		this.buyerID = buyerID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
}
