package com.newco.marketplace.dto.vo;

import com.newco.marketplace.webservices.base.CommonVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Activity")
public class ActivityVO extends CommonVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -385036942741283275L;
	@XStreamAlias("ActivityName")
	private String activityName;
	@XStreamAlias("ActivityId")
	private int activityId;
	@XStreamAlias("InactiveInd")
	private Integer inactiveInd;
	@XStreamAlias("RemoveFlag")
	private Integer removeFlag;
	@XStreamAlias("AddFlag")
	private Integer addFlag;
	
	 

	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public int getActivityId() {
		return activityId;
	}
	public Integer getRemoveFlag() {
		return removeFlag;
	}
	public void setRemoveFlag(Integer removeFlag) {
		this.removeFlag = removeFlag;
	}
	public Integer getAddFlag() {
		return addFlag;
	}
	public void setAddFlag(Integer addFlag) {
		this.addFlag = addFlag;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId.intValue();
	}
	public Integer getInactiveInd() {
		return inactiveInd;
	}
	public void setInactiveInd(Integer inactiveInd) {
		this.inactiveInd = inactiveInd;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	
	
}
