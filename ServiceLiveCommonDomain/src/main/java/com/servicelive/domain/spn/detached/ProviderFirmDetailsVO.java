package com.servicelive.domain.spn.detached;

import com.servicelive.domain.BaseDomain;

/**
 * details of provider firm
 * @author sldev
 *
 */
public class ProviderFirmDetailsVO extends BaseDomain{

	private static final long serialVersionUID = 20090202L;
	
	private String providerFirmName;
	private Integer providerFirmStatusId;
	// this is service live status
	private String providerFirmSLStatus;
	/**
	 * @return the providerFirmName
	 */
	public String getProviderFirmName() {
		return providerFirmName;
	}
	/**
	 * @param providerFirmName the providerFirmName to set
	 */
	public void setProviderFirmName(String providerFirmName) {
		this.providerFirmName = providerFirmName;
	}
	/**
	 * @return the providerFirmSLStatus
	 */
	public String getProviderFirmSLStatus() {
		return providerFirmSLStatus;
	}
	/**
	 * @param providerFirmSLStatus the providerFirmSLStatus to set
	 */
	public void setProviderFirmSLStatus(String providerFirmSLStatus) {
		this.providerFirmSLStatus = providerFirmSLStatus;
	}
	/**
	 * @return the providerFirmStatusId
	 */
	public Integer getProviderFirmStatusId() {
		return providerFirmStatusId;
	}
	/**
	 * @param providerFirmStatusId the providerFirmStatusId to set
	 */
	public void setProviderFirmStatusId(Integer providerFirmStatusId) {
		this.providerFirmStatusId = providerFirmStatusId;
	}
	



}
