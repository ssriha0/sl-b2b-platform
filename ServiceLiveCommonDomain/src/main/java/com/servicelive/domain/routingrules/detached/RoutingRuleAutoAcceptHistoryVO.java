/**
 * 
 */
package com.servicelive.domain.routingrules.detached;

import java.util.Date;

/**
 * @author madhup_chand
 *
 */
public class RoutingRuleAutoAcceptHistoryVO {
	private String autoAcceptance;
	private String updatedBy;
	private Date updatedOn;
	private String comments;
	private String role;
	private String adoptedBy;
	private Boolean opportunityEmailInd;

	/**
	 * @return the adoptedBy
	 */
	public String getAdoptedBy() {
		return adoptedBy;
	}
	/**
	 * @param adoptedBy the adoptedBy to set
	 */
	public void setAdoptedBy(String adoptedBy) {
		this.adoptedBy = adoptedBy;
	}
	/**
	 * @return the autoAcceptance
	 */
	public String getAutoAcceptance() {
		return autoAcceptance;
	}
	/**
	 * @param autoAcceptance the autoAcceptance to set
	 */
	public void setAutoAcceptance(String autoAcceptance) {
		this.autoAcceptance = autoAcceptance;
	}
	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}
	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	/**
	 * @return the updatedOn
	 */
	public Date getUpdatedOn() {
		return updatedOn;
	}
	/**
	 * @param updatedOn the updatedOn to set
	 */
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Boolean getOpportunityEmailInd() {
		return opportunityEmailInd;
	}
	public void setOpportunityEmailInd(Boolean opportunityEmailInd) {
		this.opportunityEmailInd = opportunityEmailInd;
	}
	
	

}
