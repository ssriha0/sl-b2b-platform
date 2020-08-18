package com.newco.marketplace.vo.provider;

public class FinancialProfileVO extends BaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5808239003714264439L;
	private String taxPayerId;
	private Integer foreignOwned;
	private Integer foreignOwnedPercentage;
	private Contact contact = new Contact();
	private Location location = new Location();
	private Double providerMaxWithdrawalLimit; 
	private Integer providerMaxWithdrawalNo;
	
	
	public Double getProviderMaxWithdrawalLimit() {
		return providerMaxWithdrawalLimit;
	}
	public void setProviderMaxWithdrawalLimit(Double providerMaxWithdrawalLimit) {
		this.providerMaxWithdrawalLimit = providerMaxWithdrawalLimit;
	}
	public Integer getProviderMaxWithdrawalNo() {
		return providerMaxWithdrawalNo;
	}
	public void setProviderMaxWithdrawalNo(Integer providerMaxWithdrawalNo) {
		this.providerMaxWithdrawalNo = providerMaxWithdrawalNo;
	}
	public String getTaxPayerId() {
		return taxPayerId;
	}
	public void setTaxPayerId(String taxPayerId) {
		this.taxPayerId = taxPayerId;
	}
	public Integer getForeignOwned() {
		return foreignOwned;
	}
	public void setForeignOwned(Integer foreignOwned) {
		this.foreignOwned = foreignOwned;
	}
	
	public Integer getForeignOwnedPercentage() {
		return foreignOwnedPercentage;
	}
	public void setForeignOwnedPercentage(Integer foreignOwnedPercentage) {
		this.foreignOwnedPercentage = foreignOwnedPercentage;
	}
	
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
	
	

}