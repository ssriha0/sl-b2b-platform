package com.newco.marketplace.dto.vo.serviceorder;

import java.util.Date;


import com.newco.marketplace.webservices.base.CommonVO;


/**
 * 
 * $Revision: 16834 $ $Author: shunt25 $ $Date: 2010-01-11 22:01:00 +0530 (Mon, 11 Jan 2010) $
 */

/*
 * Maintenance History
 * $Log: ServiceOrderNote.java,v $
 * Revision 1.16  2008/04/26 00:40:08  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.14.12.1  2008/04/23 11:41:15  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.15  2008/04/23 05:17:05  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.14  2008/02/14 23:44:31  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.13.4.1  2008/02/08 02:32:06  spate05
 * serializing for session replication or updating serialuid
 *
 * Revision 1.13  2008/01/24 02:13:50  mhaye05
 * replaced List of noteIds with List of noteTypeIds
 *
 * Revision 1.12  2008/01/24 00:27:54  mhaye05
 * added attribute List<Integer> noteIds
 *
 */
public class PendingCancelHistoryVO extends CommonVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5607371121739495695L;
	
	
	private Integer pendingCancelHistoryId;
	private String soId;
	private Double price;
	private Integer userId;
	private String userName;
	private String comments;
	private Boolean withdrawFlag;
	private Integer roleId;
	private Integer adminResourceId;
	private String adminUserName;
	private Date modifiedDate; 
	
	public Integer getPendingCancelHistoryId() {
		return pendingCancelHistoryId;
	}
	public void setPendingCancelHistoryId(Integer pendingCancelHistoryId) {
		this.pendingCancelHistoryId = pendingCancelHistoryId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Boolean getWithdrawFlag() {
		return withdrawFlag;
	}
	public void setWithdrawFlag(Boolean withdrawFlag) {
		this.withdrawFlag = withdrawFlag;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getAdminResourceId() {
		return adminResourceId;
	}
	public void setAdminResourceId(Integer adminResourceId) {
		this.adminResourceId = adminResourceId;
	}
	public String getAdminUserName() {
		return adminUserName;
	}
	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
	
    
}
