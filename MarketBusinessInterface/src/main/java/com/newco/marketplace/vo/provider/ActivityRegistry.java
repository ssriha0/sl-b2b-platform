/**
 * 
 */
package com.newco.marketplace.vo.provider;

import java.util.List;


public class ActivityRegistry extends BaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -806460858612238484L;
	private int actId = -1; 
	private int activityId = -1; 
	private int actLinkKey = -1;
	private int actCompleted = -1;
	private String actLinkKeyType;
	
	private List<Integer> activityIds;
	
	
	
	public List<Integer> getActivityIds() {
		return activityIds;
	}
	public void setActivityIds(List<Integer> activityIds) {
		this.activityIds = activityIds;
	}
	public String getActLinkKeyType() {
		return actLinkKeyType;
	}
	public void setActLinkKeyType(String actLinkKeyType) {
		this.actLinkKeyType = actLinkKeyType;
	}
	public int getActCompleted() {
		return actCompleted;
	}
	public void setActCompleted(int actCompleted) {
		this.actCompleted = actCompleted;
	}
	public int getActId() {
		return actId;
	}
	public void setActId(int actId) {
		this.actId = actId;
	}
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	public int getActLinkKey() {
		return actLinkKey;
	}
	public void setActLinkKey(int actLinkKey) {
		this.actLinkKey = actLinkKey;
	}
	
	
}
