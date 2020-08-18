package com.servicelive.spn.common.detached;

import java.util.Date;

import com.servicelive.domain.BaseDomain;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;

/**
 * 
 * @author svanloo
 *
 */
public class MemberMaintenanceBaseVO extends BaseDomain {

	private static final long serialVersionUID = 20100415L;
	private Integer spnId;
	private LookupSPNWorkflowState spnWorkflowState;
	private Date newModifiedDate;
	private Integer statusUpdateActionId;

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
	 * @return the spnWorkflowState
	 */
	public LookupSPNWorkflowState getSpnWorkflowState() {
		return spnWorkflowState;
	}
	/**
	 * @param spnWorkflowState the spnWorkflowState to set
	 */
	public void setSpnWorkflowState(LookupSPNWorkflowState spnWorkflowState) {
		this.spnWorkflowState = spnWorkflowState;
	}
	/**
	 * 
	 * @return Date
	 */
	public Date getNewModifiedDate() {
		return newModifiedDate;
	}
	/**
	 * 
	 * @param newModifiedDate
	 */
	public void setNewModifiedDate(Date newModifiedDate) {
		this.newModifiedDate = newModifiedDate;
	}
	/**
	 * @return the statusUpdateActionId
	 */
	public Integer getStatusUpdateActionId() {
		return statusUpdateActionId;
	}
	/**
	 * @param statusUpdateActionId the statusUpdateActionId to set
	 */
	public void setStatusUpdateActionId(Integer statusUpdateActionId) {
		this.statusUpdateActionId = statusUpdateActionId;
	}
}
