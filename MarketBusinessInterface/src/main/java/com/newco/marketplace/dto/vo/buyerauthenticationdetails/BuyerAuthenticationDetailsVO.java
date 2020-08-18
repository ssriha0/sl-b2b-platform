package com.newco.marketplace.dto.vo.buyerauthenticationdetails;

import java.io.Serializable;

public class BuyerAuthenticationDetailsVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9049927308466019255L;
	private Integer buyerResourceId;
	private String consumerKey;
	private String secretKey;
	
	
	public Integer getBuyerResourceId() {
		return buyerResourceId;
	}
	public void setBuyerResourceId(Integer buyerResourceId) {
		this.buyerResourceId = buyerResourceId;
	}
	public String getConsumerKey() {
		return consumerKey;
	}
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	

}
