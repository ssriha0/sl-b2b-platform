package com.servicelive.lookup.vo;

import java.io.Serializable;


// TODO: Auto-generated Javadoc
/**
 * Class ProviderLookupVO.
 */
public class ProviderLookupVO implements Serializable {
	
	/** serialVersionUID. */
	private static final long serialVersionUID = -5570467993573763271L;

	/** providerFundingTypeId. */
	private Long providerFundingTypeId;

	/** providerState. */
	private String providerState;

	/** providerV1AccountNumber. */
	private Long providerV1AccountNumber;

	
	/**
	 * getProviderFundingTypeId.
	 * 
	 * @return Long
	 */
	public Long getProviderFundingTypeId() {
	
		return providerFundingTypeId;
	}

	
	/**
	 * setProviderFundingTypeId.
	 * 
	 * @param providerFundingTypeId 
	 * 
	 * @return void
	 */
	public void setProviderFundingTypeId(Long providerFundingTypeId) {
	
		this.providerFundingTypeId = providerFundingTypeId;
	}

	
	/**
	 * getProviderState.
	 * 
	 * @return String
	 */
	public String getProviderState() {
	
		return providerState;
	}

	
	/**
	 * setProviderState.
	 * 
	 * @param providerState 
	 * 
	 * @return void
	 */
	public void setProviderState(String providerState) {
	
		this.providerState = providerState;
	}

	
	/**
	 * getProviderV1AccountNumber.
	 * 
	 * @return Long
	 */
	public Long getProviderV1AccountNumber() {
	
		return providerV1AccountNumber;
	}

	
	/**
	 * setProviderV1AccountNumber.
	 * 
	 * @param providerV1AccountNumber 
	 * 
	 * @return void
	 */
	public void setProviderV1AccountNumber(Long providerV1AccountNumber) {
	
		this.providerV1AccountNumber = providerV1AccountNumber;
	}

}
