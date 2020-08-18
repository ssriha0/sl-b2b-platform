package com.newco.marketplace.dto.vo.powerbuyer;

import java.util.Date;

import com.newco.marketplace.webservices.base.CommonVO;

/**
 * @author rambewa
 *
 * $Revision$ $Author$ $Date$
 */

/*
 * Maintenance History
 * $Log: ClaimVO.java,v $
 * Revision 1.12  2008/04/26 00:40:07  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.10.12.1  2008/04/23 11:41:13  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.11  2008/04/23 05:17:01  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.10  2008/02/14 23:44:24  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.9.6.1  2008/02/08 02:31:52  spate05
 * serializing for session replication or updating serialuid
 *
 * Revision 1.9  2008/01/21 23:01:53  rambewa
 * expired claims
 *
 * Revision 1.8  2008/01/19 03:58:04  rambewa
 * *** empty log message ***
 *
 * Revision 1.7  2008/01/18 22:39:27  mhaye05
 * updated to make sure we use the buyer id when claiming the next so
 *
 * Revision 1.6  2008/01/17 17:16:46  dmill03
 * added current modofied date field
 *
 * Revision 1.5  2008/01/16 22:55:27  mhaye05
 * added methods and attributes to check if passed in user is claimer
 *
 * Revision 1.4  2008/01/16 17:16:24  mhaye05
 * added statusId attribute
 *
 * Revision 1.3  2008/01/16 16:28:51  mhaye05
 * added service order title
 *
 * Revision 1.2  2008/01/16 15:53:28  mhaye05
 * added logic to retrieve the claimed service orders
 *
 */
public class ClaimVO extends CommonVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4093165934062517460L;
	private String soId;
	private Integer resourceId;
	private String soTitle;
	private Date claimDate;
	private Integer statusId;
	private Date soModifiedDate; 
	private Date currentSOModifiedDate;
	private Integer buyerId;
	private int reasonCode;
	private Integer queueId;
	private String queueDestinationTab;
	private String queueDestinationSubTab;
	private String parentGroupId;
	
	public String getSoId() {
		return soId;
	}
	public String getQueueDestinationTab() {
		return queueDestinationTab;
	}
	public void setQueueDestinationTab(String queueDestinationTab) {
		this.queueDestinationTab = queueDestinationTab;
	}
	
	public String getQueueDestinationSubTab() {
		return queueDestinationSubTab;
	}
	public void setQueueDestinationSubTab(String queueDestinationSubTab) {
		this.queueDestinationSubTab = queueDestinationSubTab;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Date getClaimDate() {
		return claimDate;
	}
	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}
	public String getSoTitle() {
		return soTitle;
	}
	public void setSoTitle(String soTitle) {
		this.soTitle = soTitle;
	}
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	public Date getSoModifiedDate() {
		return soModifiedDate;
	}
	public void setSoModifiedDate(Date soModifiedDate) {
		this.soModifiedDate = soModifiedDate;
	}
	public Date getCurrentSOModifiedDate() {
		return currentSOModifiedDate;
	}
	public void setCurrentSOModifiedDate(Date currentSOModifiedDate) {
		this.currentSOModifiedDate = currentSOModifiedDate;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public int getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(int reasonCode) {
		this.reasonCode = reasonCode;
	}
	public Integer getQueueId() {
		return queueId;
	}
	public void setQueueId(Integer queueId) {
		this.queueId = queueId;
	}
	public String getParentGroupId() {
		return parentGroupId;
	}
	public void setParentGroupId(String parentGroupId) {
		this.parentGroupId = parentGroupId;
	}
	
	@Override
	public String toString() {
		StringBuffer toString = new StringBuffer();
		toString.append("ClaimVO:: soId: ").append(soId).
		append(", resourceId: ").append(resourceId).
		append(", buyerId: ").append(buyerId).
		append(", queueId: ").append(queueId).
		append(", reasonCode: ").append(reasonCode).
		append(", statusId: ").append(statusId).
		append(", parentGroupId: ").append(parentGroupId);
		return toString.toString();
	}
	
}
