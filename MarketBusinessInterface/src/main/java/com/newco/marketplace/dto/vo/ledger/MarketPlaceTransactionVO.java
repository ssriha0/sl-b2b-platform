package com.newco.marketplace.dto.vo.ledger;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.sears.os.vo.SerializableBaseVO;



public class MarketPlaceTransactionVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3940774708102571017L;
	private Integer ledgerEntryRuleId;
	private Integer userTypeID;
	private Integer buyerID;
	private Integer vendorId;
	private Integer businessTransId;
	private Integer feeTypeID;
	private String adminUser;
	private ServiceOrder serviceOrder;
	private boolean CCInd;
	private Integer fundingTypeId;
	private String transactionNote;
	private Long accountTypeId;
	private double preTxnAvailableBalance;
	private String userName;
	private String autoACHInd;
	private Long accountId;
	private Double achAmount;
	
	public boolean isCCInd() {
		return CCInd;
	}
	public void setCCInd(boolean CCInd) {
		this.CCInd = CCInd;
	}
	public Integer getUserTypeID() {
		return userTypeID;
	}
	public void setUserTypeID(Integer userTypeID) {
		this.userTypeID = userTypeID;
	}
	
	public Integer getfeeTypeID() {
		return feeTypeID;
	}
	public void setFeeTypeID(Integer feeTypeID) {
		this.feeTypeID = feeTypeID;
	}
	
	public ServiceOrder getServiceOrder() {
		return serviceOrder;
	}
	public void setServiceOrder(ServiceOrder serviceOrder) {
		this.serviceOrder = serviceOrder;
	}
	public Integer getLedgerEntryRuleId() {
		return ledgerEntryRuleId;
	}
	public void setLedgerEntryRuleId(Integer ledgerEntryRuleId) {
		this.ledgerEntryRuleId = ledgerEntryRuleId;
	}
	public Integer getBusinessTransId() {
		return businessTransId;
	}
	public void setBusinessTransId(Integer businessTransId) {
		this.businessTransId = businessTransId;
	}
	public Integer getBuyerID() {
		return buyerID;
	}
	public void setBuyerID(Integer buyerID) {
		this.buyerID = buyerID;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("ledgerEntryRuleId", getLedgerEntryRuleId())
			.append("userTypeID", getUserTypeID())
			.append("buyerID", getBuyerID())
			.append("vendorId", getVendorId())	
			.append("businessTransId", getBusinessTransId())
			.append("feeTypeID", getfeeTypeID())
			.append("fundigTypeId", getFundingTypeId())
			.toString();
	}
	public Integer getFundingTypeId() {
		return fundingTypeId;
	}
	public void setFundingTypeId(Integer fundingTypeId) {
		this.fundingTypeId = fundingTypeId;
	}
	public Integer getFeeTypeID() {
		return feeTypeID;
	}
	public String getTransactionNote() {
		return transactionNote;
	}
	public void setTransactionNote(String transactionNote) {
		this.transactionNote = transactionNote;
	}
	public Long getAccountTypeId() {
		return accountTypeId;
	}
	public void setAccountTypeId(Long accountTypeId) {
		this.accountTypeId = accountTypeId;
	}
	public String getAdminUser() {
		return adminUser;
	}
	public void setAdminUser(String adminUser) {
		this.adminUser = adminUser;
	}
	public double getPreTxnAvailableBalance() {
		return preTxnAvailableBalance;
	}
	public void setPreTxnAvailableBalance(double preTxnAvailableBalance) {
		this.preTxnAvailableBalance = preTxnAvailableBalance;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAutoACHInd() {
		return autoACHInd;
	}
	public void setAutoACHInd(String autoACHInd) {
		this.autoACHInd = autoACHInd;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Double getAchAmount() {
		return achAmount;
	}
	public void setAchAmount(Double achAmount) {
		this.achAmount = achAmount;
	}

}
