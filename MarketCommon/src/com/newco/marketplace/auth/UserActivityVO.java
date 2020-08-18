/*
 * SecretQuestionVO.java    1.0     2007/05/30
 */
package com.newco.marketplace.auth;

import com.sears.os.vo.SerializableBaseVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;



/**
 * 
 * @version
 * @author Covansys - Offshore
 *
 */
public class UserActivityVO  extends SerializableBaseVO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1212416560759863164L;
	@XStreamAlias("UserName")
	protected String userName = null;
	@XStreamAlias("ActivityName")
	protected String activityName = null;
	@XStreamAlias("ActivityId")
	protected String activityId = null;
	@XStreamAlias("RoleActivityId")
	protected String roleActivityId = null;
	@XStreamAlias("Checked")
	protected  boolean checked = false;
	@XStreamOmitField
	protected  Integer permissionSetId;
	@XStreamOmitField
	protected  Integer adminInd;
	@XStreamOmitField
	protected String index;
	
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String toString(){
		return "activityName:" + activityName + "|activityId:" + activityId +"|roleActivityId:" + roleActivityId + "|checked" + checked + "|username"+ userName + "\n";
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String inUserName) {
		userName = inUserName;
	}                
	public String getRoleActivityId() {
		return roleActivityId;
	}
	public void setRoleActivityId(String roleActivityId) {
		this.roleActivityId = roleActivityId;
	}
	public Integer getPermissionSetId() {
		return permissionSetId;
	}
	public void setPermissionSetId(Integer permissionSetId) {
		this.permissionSetId = permissionSetId;
	}
	public Integer getAdminInd() {
		return adminInd;
	}
	public void setAdminInd(Integer adminInd) {
		this.adminInd = adminInd;
	}
	
}