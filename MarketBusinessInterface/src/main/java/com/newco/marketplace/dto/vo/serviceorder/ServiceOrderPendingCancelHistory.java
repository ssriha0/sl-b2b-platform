package com.newco.marketplace.dto.vo.serviceorder;

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
public class ServiceOrderPendingCancelHistory extends CommonVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5607371121739495695L;
	
    private String price;
    private String userId;
    private String userName;
    private String comments;
    private String adminResourceId;
    private String adminUserName;
    private String wfStateId;
    private String modifiedDate;
    private String modifiedTime;
    
    
    
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
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
	public String getAdminResourceId() {
		return adminResourceId;
	}
	public void setAdminResourceId(String adminResourceId) {
		this.adminResourceId = adminResourceId;
	}
	public String getAdminUserName() {
		return adminUserName;
	}
	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}
	public String getWfStateId() {
		return wfStateId;
	}
	public void setWfStateId(String wfStateId) {
		this.wfStateId = wfStateId;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
    
    
    
    
    

	


	
}
