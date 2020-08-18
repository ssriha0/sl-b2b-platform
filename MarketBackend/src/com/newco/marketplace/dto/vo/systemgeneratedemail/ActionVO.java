/**
 * 
 */
package com.newco.marketplace.dto.vo.systemgeneratedemail;

/**
 * @author lprabha
 *
 */
public class ActionVO {
	private Integer actionId;
	private Integer wfStateId;
	private Integer soSubstatusId;
	
	/**
	 * @return the actionId
	 */
	public Integer getActionId() {
		return actionId;
	}
	/**
	 * @param actionId the actionId to set
	 */
	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}
	/**
	 * @return the wfStateId
	 */
	public Integer getWfStateId() {
		return wfStateId;
	}
	/**
	 * @param wfStateId the wfStateId to set
	 */
	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}
	/**
	 * @return the soSubstatusId
	 */
	public Integer getSoSubstatusId() {
		return soSubstatusId;
	}
	/**
	 * @param soSubstatusId the soSubstatusId to set
	 */
	public void setSoSubstatusId(Integer soSubstatusId) {
		this.soSubstatusId = soSubstatusId;
	}
}
