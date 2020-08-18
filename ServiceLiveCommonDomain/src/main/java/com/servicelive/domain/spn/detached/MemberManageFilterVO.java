/**
 * 
 */
package com.servicelive.domain.spn.detached;

import java.io.Serializable;

/**
 * @author hoza
 *
 */
public class MemberManageFilterVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9075788232072472920L;
	
	private Integer spnId;
	private String stateCode;
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
	 * @return the stateCode
	 */
	public String getStateCode() {
		return stateCode;
	}
	/**
	 * @param stateCode the stateCode to set
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
}
