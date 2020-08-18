package com.servicelive.spn.common.detached;

import java.io.Serializable;
import java.util.Date;

import com.servicelive.domain.lookup.LookupSPNWorkflowState;

/**
 * 
 * @author svanloon
 *
 */
public class SPNAuditorQueueCountVO  implements Serializable {

	private static final long serialVersionUID = -20090115L;

	private LookupSPNWorkflowState state;
	private Integer count;

	/**
	 * This is an optional field will/will not be filled in depending on whether or not the query is resolved down to the providerFirm level.
	 */
	private Integer spnId;

	/**
	 * This is an optional field will/will not be filled in depending on whether or not the query is resolved down to the providerFirm level.
	 */
	private Integer providerFirmId;
	
	/**
	 * This is an optional field will/will not be filled in depending on whether or not the query is resolved down to the providerFirm level.
	 */
	private Date modifiedDate;

	/**
	 * @return the state
	 */
	public LookupSPNWorkflowState getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(LookupSPNWorkflowState state) {
		this.state = state;
	}
	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
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
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}
