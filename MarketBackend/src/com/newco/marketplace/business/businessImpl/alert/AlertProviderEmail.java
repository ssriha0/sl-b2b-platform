package com.newco.marketplace.business.businessImpl.alert;

public class AlertProviderEmail {

	private long contactIds[];
	private String providerEmail;
	
	
	private String smsNo;
	private Integer altContactMethodId;
	
	
	private String providerAdminEmail;
	private Integer vendorId;
	private Integer contactId;
	private Integer primaryInd;
	private String soId;
	private Integer tierId;

	
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

	public String getProviderAdminEmail() {
		return providerAdminEmail;
	}

	public void setProviderAdminEmail(String providerAdminEmail) {
		this.providerAdminEmail = providerAdminEmail;
	}

	public String getProviderEmail() {
		return providerEmail;
	}

	public void setProviderEmail(String providerEmail) {
		this.providerEmail = providerEmail;
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

	/**
	 * @return the tierId
	 */
	public Integer getTierId() {
		return tierId;
	}

	/**
	 * @param tierId the tierId to set
	 */
	public void setTierId(Integer tierId) {
		this.tierId = tierId;
	}

		
}
