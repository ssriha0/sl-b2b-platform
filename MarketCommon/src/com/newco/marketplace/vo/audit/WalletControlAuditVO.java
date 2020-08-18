package com.newco.marketplace.vo.audit;

import java.sql.Date;

public class WalletControlAuditVO {
	private String reviewedBy = null;
    private boolean sendEmailNotice = true;
    private Date modifiedDate;
    private Date createdDate;
    private Integer vendorId = null;
    private boolean walletOnHold=false;
    private String walletControlType=null;
    private Integer escheatmentTransferReasonCodeId=null;
    private double amount=-1;
	public String getReviewedBy() {
		return reviewedBy;
	}
	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}
	public boolean isSendEmailNotice() {
		return sendEmailNotice;
	}
	public void setSendEmailNotice(boolean sendEmailNotice) {
		this.sendEmailNotice = sendEmailNotice;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public boolean isWalletOnHold() {
		return walletOnHold;
	}
	public void setWalletOnHold(boolean walletOnHold) {
		this.walletOnHold = walletOnHold;
	}
	public String getWalletControlType() {
		return walletControlType;
	}
	public void setWalletControlType(String walletControlType) {
		this.walletControlType = walletControlType;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Integer getEscheatmentTransferReasonCodeId() {
		return escheatmentTransferReasonCodeId;
	}
	public void setEscheatmentTransferReasonCodeId(Integer escheatmentTransferReasonCodeId) {
		this.escheatmentTransferReasonCodeId = escheatmentTransferReasonCodeId;
	}
}
