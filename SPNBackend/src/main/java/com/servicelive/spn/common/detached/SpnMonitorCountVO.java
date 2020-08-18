package com.servicelive.spn.common.detached;

public class SpnMonitorCountVO {

	private Integer spnId;
	private Integer providerFirmId;
	private String providerFirmSpnState;
	private Integer serviceProviderId;
	private String serviceProviderSpnState;
	private Integer aliasOriginalSpnId;

	/**
	 * @return the spnId
	 */
	public Integer getSpnId() {
		return spnId;
	}
	/**
	 * @param spnId the spnId to set
	 */
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
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
	 * @return the providerFirmSpnState
	 */
	public String getProviderFirmSpnState() {
		return providerFirmSpnState;
	}
	/**
	 * @param providerFirmSpnState the providerFirmSpnState to set
	 */
	public void setProviderFirmSpnState(String providerFirmSpnState) {
		this.providerFirmSpnState = providerFirmSpnState;
	}
	/**
	 * @return the serviceProviderId
	 */
	public Integer getServiceProviderId() {
		return serviceProviderId;
	}
	/**
	 * @param serviceProviderId the serviceProviderId to set
	 */
	public void setServiceProviderId(Integer serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}
	/**
	 * @return the serviceProviderSpnState
	 */
	public String getServiceProviderSpnState() {
		return serviceProviderSpnState;
	}
	/**
	 * @param serviceProviderSpnState the serviceProviderSpnState to set
	 */
	public void setServiceProviderSpnState(String serviceProviderSpnState) {
		this.serviceProviderSpnState = serviceProviderSpnState;
	}
	public Integer getAliasOriginalSpnId() {
		return aliasOriginalSpnId;
	}
	public void setAliasOriginalSpnId(Integer aliasOriginalSpnId) {
		this.aliasOriginalSpnId = aliasOriginalSpnId;
	}
	
}
