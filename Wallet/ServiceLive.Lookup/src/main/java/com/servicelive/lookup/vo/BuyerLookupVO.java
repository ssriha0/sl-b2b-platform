package com.servicelive.lookup.vo;

import java.io.Serializable;


/**
 * Class BuyerLookupVO.
 */
public class BuyerLookupVO implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = -6121340151112525500L;

	/** buyerFundingTypeId. */
	private Long buyerFundingTypeId;

	/** buyerState. */
	private String buyerState;

	/** buyerV1AccountNumber. */
	private Long buyerV1AccountNumber;

	/** buyerV2AccountNumber. */
	private Long buyerV2AccountNumber;

	
	/**
	 * getBuyerFundingTypeId.
	 * 
	 * @return Long
	 */
	public Long getBuyerFundingTypeId() {
	
		return buyerFundingTypeId;
	}

	
	/**
	 * setBuyerFundingTypeId.
	 * 
	 * @param buyerFundingTypeId 
	 * 
	 * @return void
	 */
	public void setBuyerFundingTypeId(Long buyerFundingTypeId) {
	
		this.buyerFundingTypeId = buyerFundingTypeId;
	}

	
	/**
	 * getBuyerState.
	 * 
	 * @return String
	 */
	public String getBuyerState() {
	
		return buyerState;
	}

	
	/**
	 * setBuyerState.
	 * 
	 * @param buyerState 
	 * 
	 * @return void
	 */
	public void setBuyerState(String buyerState) {
	
		this.buyerState = buyerState;
	}

	
	/**
	 * getBuyerV1AccountNumber.
	 * 
	 * @return Long
	 */
	public Long getBuyerV1AccountNumber() {
	
		return buyerV1AccountNumber;
	}

	
	/**
	 * setBuyerV1AccountNumber.
	 * 
	 * @param buyerV1AccountNumber 
	 * 
	 * @return void
	 */
	public void setBuyerV1AccountNumber(Long buyerV1AccountNumber) {
	
		this.buyerV1AccountNumber = buyerV1AccountNumber;
	}

	
	/**
	 * getBuyerV2AccountNumber.
	 * 
	 * @return Long
	 */
	public Long getBuyerV2AccountNumber() {
	
		return buyerV2AccountNumber;
	}

	
	/**
	 * setBuyerV2AccountNumber.
	 * 
	 * @param buyerV2AccountNumber 
	 * 
	 * @return void
	 */
	public void setBuyerV2AccountNumber(Long buyerV2AccountNumber) {
	
		this.buyerV2AccountNumber = buyerV2AccountNumber;
	}

}
