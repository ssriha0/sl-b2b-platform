/**
 * 
 */
package com.newco.marketplace.vo.provider;

/**
 * @author sahmad7
 *
 */
public class LuActivity extends BaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2732408305273824331L;
	private int activityId = -1; 
	private String actLinkKeyType;
	private String actCategory;
	private String actName;
	
	public String getActCategory() {
		return actCategory;
	}
	public void setActCategory(String actCategory) {
		this.actCategory = actCategory;
	}
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	public String getActLinkKeyType() {
		return actLinkKeyType;
	}
	public void setActLinkKeyType(String actLinkKeyType) {
		this.actLinkKeyType = actLinkKeyType;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}

}
