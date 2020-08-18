package com.servicelive.domain.spn.detached;

import com.servicelive.domain.BaseDomain;

/**
 * This is a place holder VO
 * @author SVANLOO
 *
 */
public class ProviderFirmVO extends BaseDomain {

	private static final long serialVersionUID = 20081215L;

	private String emailAddress;
	private Integer providerFirmId;
	private Integer providerFirmAdminId;
	private String providerFirmAdminName; //this is the name email has been sent to 

	/**
	 * 
	 */
	public ProviderFirmVO() {
		super();
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	
	/**
	 * @return the providerFirmId
	 */
	public Integer getProviderFirmId() {
		return providerFirmId;
	}

	/**
	 * @param providerFirmId the providerFirmId to set
	 */
	public void setProviderFirmId(Integer providerFirmId) {
		this.providerFirmId = providerFirmId;
	}

	/**
	 * @return the providerFirmAdminId
	 */
	public Integer getProviderFirmAdminId() {
		return providerFirmAdminId;
	}

	/**
	 * @param providerFirmAdminId the providerFirmAdminId to set
	 */
	public void setProviderFirmAdminId(Integer providerFirmAdminId) {
		this.providerFirmAdminId = providerFirmAdminId;
	}

	/**
	 * @return the providerFirmAdminName
	 */
	public String getProviderFirmAdminName() {
		return providerFirmAdminName;
	}

	/**
	 * @param providerFirmAdminName the providerFirmAdminName to set
	 */
	public void setProviderFirmAdminName(String providerFirmAdminName) {
		this.providerFirmAdminName = providerFirmAdminName;
	}
}
