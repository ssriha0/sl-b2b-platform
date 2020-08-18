package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("routedProviders")
public class RoutedProvidersVO {
	
	@XStreamAlias("id")
	private Integer id;
	@XStreamAlias("firstName")
	private String firstName;
	@XStreamAlias("lastName")
	private String lastName;
	@XStreamAlias("respId")
	private Integer respId;
	
	@OptionalParam
	@XStreamAlias("spendLimit")
	private String spendLimit;
	
	@OptionalParam
	@XStreamAlias("offerExpirationDate") 
	private String offerExpirationDate;
	
	@OptionalParam
	@XStreamAlias("soId") 
	private String soId;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return firstName;
	}
	public void setCode(String firstName) {
		this.firstName = firstName;
	}
	public String getDescr() {
		return lastName;
	}
	public void setDescr(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getRespId() {
		return respId;
	}
	public void setRespId(Integer respId) {
		this.respId = respId;
	}
	public String getSpendLimit() {
		return spendLimit;
	}
	public void setSpendLimit(String spendLimit) {
		this.spendLimit = spendLimit;
	}
	public String getOfferExpirationDate() {
		return offerExpirationDate;
	}
	public void setOfferExpirationDate(String offerExpirationDate) {
		this.offerExpirationDate = offerExpirationDate;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	

}
