package com.servicelive.domain.spn.detached;


/**
 * 
 * @author svanloon
 *
 */
public class ProviderMatchingCountExtendVO extends ProviderMatchingCountsVO {

	private static final long serialVersionUID = 20090123L;

	private Integer spnId;
	private String state;

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
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

}
