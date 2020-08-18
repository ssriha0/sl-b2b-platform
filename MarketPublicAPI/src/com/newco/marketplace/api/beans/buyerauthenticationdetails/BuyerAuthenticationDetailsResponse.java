package com.newco.marketplace.api.beans.buyerauthenticationdetails;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.common.BaseResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="buyerAuthenticationDetailsResponse.xsd", path="/resources/schemas/buyerauthenticationdetails/")
@XStreamAlias("buyerAuthenticationDetailsResponse")
public class BuyerAuthenticationDetailsResponse extends BaseResponse{
	
	@XStreamAlias("buyerResourceId")
	private Integer buyerResourceId;
	
	@XStreamAlias("consumerKey")
	private String consumerKey;
	
	@XStreamAlias("secretKey")
	private String secretKey;
	
	public BuyerAuthenticationDetailsResponse() {
		super();
	}
	
	public BuyerAuthenticationDetailsResponse(Results results) {
		super(results);
	}

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
