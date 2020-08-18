/**
 * 
 */
package com.newco.marketplace.dto.vo.alert;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author sahmad7
 *
 */
public class WfStateTransitionVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1517866261713073249L;
	private Integer stateTransitionId;
	private Integer targetState;
	private Integer sourceState;
	private Integer alertLevel;
	private Integer templateId;
	
	public Integer getAlertLevel() {
		return alertLevel;
	}
	public void setAlertLevel(Integer alertLevel) {
		this.alertLevel = alertLevel;
	}
	public Integer getSourceState() {
		return sourceState;
	}
	public void setSourceState(Integer sourceState) {
		this.sourceState = sourceState;
	}
	public Integer getStateTransitionId() {
		return stateTransitionId;
	}
	public void setStateTransitionId(Integer stateTransitionId) {
		this.stateTransitionId = stateTransitionId;
	}
	public Integer getTargetState() {
		return targetState;
	}
	public void setTargetState(Integer targetState) {
		this.targetState = targetState;
	}
	public Integer getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}	

}
