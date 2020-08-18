package com.newco.marketplace.business.businessImpl.alert;

public class AlertBuyerEmail {

	private long contactIds[];
	private String buyerEmail;
	
	
	private String smsNo;
	private Integer altContactMethodId;
	
	
	private String buyerAdminEmail;
	private Integer vendorId;
	private Integer contactId;
	private Integer primaryInd;
	private String soId;
	private String altEmailId;

	
	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public Integer getPrimaryInd() {
		return primaryInd;
	}

	public void setPrimaryInd(Integer primaryInd) {
		this.primaryInd = primaryInd;
	}

	public String getBuyerAdminEmail() {
		return buyerAdminEmail;
	}

	public void setBuyerAdminEmail(String BuyerAdminEmail) {
		this.buyerAdminEmail = BuyerAdminEmail;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public long[] getContactIds() {
		return contactIds;
	}

	public void setContactIds(long[] contactIds) {
		this.contactIds = contactIds;
	}

	public String getSmsNo() {
		return smsNo;
	}

	public void setSmsNo(String smsNo) {
		this.smsNo = smsNo;
	}

	public Integer getAltContactMethodId() {
		return altContactMethodId;
	}

	public void setAltContactMethodId(Integer altContactMethodId) {
		this.altContactMethodId = altContactMethodId;
	}

	public String getAltEmailId() {
		return altEmailId;
	}

	public void setAltEmailId(String altEmailId) {
		this.altEmailId = altEmailId;
	}

		
}
