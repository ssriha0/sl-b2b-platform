/**
 *
 */
package com.newco.marketplace.vo.provider;

import java.util.List;

/**
 * @author hoza
 *
 */
public class PowerAuditorSearchVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer primaryIndustry;
	private Integer credentialType;
	private Integer categoryOfCredential;
	private Integer roleType; //1 for Company  2 for Team member
	private List<String> excludedQueueNames;
	private List<String> stickyQueueNames;
	private List<String> primaryQueueNames;
	private String workFlowQueue;
	
	// SL-20645 : new reasoncodes under 'Approved' status
	private String reasonNameQueue;
	
	//SL-20645
	private String firmId;
	
	
	/**
	 * @return the primaryIndustry
	 */
	public Integer getPrimaryIndustry() {
		return primaryIndustry;
	}
	/**
	 * @param primaryIndustry the primaryIndustry to set
	 */
	public void setPrimaryIndustry(Integer primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}
	/**
	 * @return the credentialType
	 */
	public Integer getCredentialType() {
		return credentialType;
	}
	/**
	 * @param credentialType the credentialType to set
	 */
	public void setCredentialType(Integer credentialType) {
		this.credentialType = credentialType;
	}
	/**
	 * @return the categoryOfCredential
	 */
	public Integer getCategoryOfCredential() {
		return categoryOfCredential;
	}
	/**
	 * @param categoryOfCredential the categoryOfCredential to set
	 */
	public void setCategoryOfCredential(Integer categoryOfCredential) {
		this.categoryOfCredential = categoryOfCredential;
	}
	/**
	 * @return the excludedQueueNames
	 */
	public List<String> getExcludedQueueNames() {
		return excludedQueueNames;
	}
	/**
	 * @param excludedQueueNames the excludedQueueNames to set
	 */
	public void setExcludedQueueNames(List<String> excludedQueueNames) {
		this.excludedQueueNames = excludedQueueNames;
	}
	/**
	 * @return the workFlowQueue
	 */
	public String getWorkFlowQueue() {
		return workFlowQueue;
	}
	/**
	 * @param workFlowQueue the workFlowQueue to set
	 */
	public void setWorkFlowQueue(String workFlowQueue) {
		this.workFlowQueue = workFlowQueue;
	}
	public List<String> getStickyQueueNames() {
		return stickyQueueNames;
	}
	public void setStickyQueueNames(List<String> stickyQueueNames) {
		this.stickyQueueNames = stickyQueueNames;
	}
	/**
	 * @return the roleType
	 */
	public Integer getRoleType() {
		return roleType;
	}
	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
	//SL-20645 adding the new reasoncodes
	public String getReasonNameQueue() {
		return reasonNameQueue;
	}
	public void setReasonNameQueue(String reasonNameQueue) {
		this.reasonNameQueue = reasonNameQueue;
	}
	public String getFirmId() {
		return firmId;
	}
	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}
	public List<String> getPrimaryQueueNames() {
		return primaryQueueNames;
	}
	public void setPrimaryQueueNames(List<String> primaryQueueNames) {
		this.primaryQueueNames = primaryQueueNames;
	}
	
	
	
}
