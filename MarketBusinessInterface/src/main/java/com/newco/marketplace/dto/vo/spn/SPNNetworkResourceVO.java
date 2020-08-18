package com.newco.marketplace.dto.vo.spn;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision$ $Author$ $Date$
 */

/*
 * Maintenance History: See bottom of file.
 */
public class SPNNetworkResourceVO extends SerializableBaseVO {

	private static final long serialVersionUID = 6490233461614131411L;
	
	private Integer spnNetworkId;
	private Integer spnId;
	private Integer resourceId;
	private Integer spnStatusId;
	private Date inviteDate;
	private Date applicationDate;
	private Date memberDate;
	private Date lastInactiveDate;
	private Date removedDate;
	private Double skillPercentMathc;
	private String resourceZip;
	private String firstName;
	private String lastName;
	private Integer totalOrders;

	/**
	 * @return the spnNetworkId
	 */
	public Integer getSpnNetworkId() {
		return spnNetworkId;
	}
	/**
	 * @param spnNetworkId the spnNetworkId to set
	 */
	public void setSpnNetworkId(Integer spnNetworkId) {
		this.spnNetworkId = spnNetworkId;
	}
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
	 * @return the resourceId
	 */
	public Integer getResourceId() {
		return resourceId;
	}
	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	/**
	 * @return the spnStatusId
	 */
	public Integer getSpnStatusId() {
		return spnStatusId;
	}
	/**
	 * @param spnStatusId the spnStatusId to set
	 */
	public void setSpnStatusId(Integer spnStatusId) {
		this.spnStatusId = spnStatusId;
	}
	/**
	 * @return the inviteDate
	 */
	public Date getInviteDate() {
		return inviteDate;
	}
	/**
	 * @param inviteDate the inviteDate to set
	 */
	public void setInviteDate(Date inviteDate) {
		this.inviteDate = inviteDate;
	}
	/**
	 * @return the applicationDate
	 */
	public Date getApplicationDate() {
		return applicationDate;
	}
	/**
	 * @param applicationDate the applicationDate to set
	 */
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}
	/**
	 * @return the memberDate
	 */
	public Date getMemberDate() {
		return memberDate;
	}
	/**
	 * @param memberDate the memberDate to set
	 */
	public void setMemberDate(Date memberDate) {
		this.memberDate = memberDate;
	}
	/**
	 * @return the lastInactiveDate
	 */
	public Date getLastInactiveDate() {
		return lastInactiveDate;
	}
	/**
	 * @param lastInactiveDate the lastInactiveDate to set
	 */
	public void setLastInactiveDate(Date lastInactiveDate) {
		this.lastInactiveDate = lastInactiveDate;
	}
	/**
	 * @return the removedDate
	 */
	public Date getRemovedDate() {
		return removedDate;
	}
	/**
	 * @param removedDate the removedDate to set
	 */
	public void setRemovedDate(Date removedDate) {
		this.removedDate = removedDate;
	}
	/**
	 * @return the skillPercentMathc
	 */
	public Double getSkillPercentMathc() {
		return skillPercentMathc;
	}
	/**
	 * @param skillPercentMathc the skillPercentMathc to set
	 */
	public void setSkillPercentMathc(Double skillPercentMathc) {
		this.skillPercentMathc = skillPercentMathc;
	}
	/**
	 * @return the resourceZip
	 */
	public String getResourceZip() {
		return resourceZip;
	}
	/**
	 * @param resourceZip the resourceZip to set
	 */
	public void setResourceZip(String resourceZip) {
		this.resourceZip = resourceZip;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the totalOrders
	 */
	public Integer getTotalOrders() {
		return totalOrders;
	}
	/**
	 * @param totalOrders the totalOrders to set
	 */
	public void setTotalOrders(Integer totalOrders) {
		this.totalOrders = totalOrders;
	}
	
	
	

}
/*
 * Maintenance History:
 * $Log: SPNNetworkResourceVO.java,v $
 * Revision 1.2  2008/05/02 21:23:57  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.1.2.2  2008/04/29 14:38:20  mhaye05
 * added additional attributes
 *
 * Revision 1.1.2.1  2008/04/08 20:48:50  mhaye05
 * Initial check in
 *
 */