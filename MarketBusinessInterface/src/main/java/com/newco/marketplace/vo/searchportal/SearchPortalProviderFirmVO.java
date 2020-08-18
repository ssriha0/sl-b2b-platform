/**
 *
 */
package com.newco.marketplace.vo.searchportal;

import org.apache.commons.lang.StringUtils;

/**
 * @author hoza
 *
 */
public class SearchPortalProviderFirmVO extends BaseSearchPortalVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 3096400993329810194L;
	//I could have moved these two into the base one.. but then I am afraid that the bseVO might be too heavy loaded
	private SearchPortalUserVO user;
	private SearchPortalLocationVO location;
	private Integer primaryIndustryId;
	private Integer workflowStateId;
	private String primaryIndustry;
	private String workflowState;
	private Integer referralId;
	private Integer spnetId;
	private String sortOrder;
	private String sortKey;
	/**
	 * @return the user
	 */
	public SearchPortalUserVO getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(SearchPortalUserVO user) {
		this.user = user;
	}
	/**
	 * @return the location
	 */
	public SearchPortalLocationVO getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(SearchPortalLocationVO location) {
		this.location = location;
	}
	/**
	 * @return the primaryIndustryId
	 */
	public Integer getPrimaryIndustryId() {
		return primaryIndustryId;
	}
	/**
	 * @param primaryIndustryId the primaryIndustryId to set
	 */
	public void setPrimaryIndustryId(Integer primaryIndustryId) {
		this.primaryIndustryId = primaryIndustryId;
	}
	/**
	 * @return the workflowStateId
	 */
	public Integer getWorkflowStateId() {
		return workflowStateId;
	}
	/**
	 * @param workflowStateId the workflowStateId to set
	 */
	public void setWorkflowStateId(Integer workflowStateId) {
		this.workflowStateId = workflowStateId;
	}
	/**
	 * @return the primaryIndustry
	 */
	public String getPrimaryIndustry() {
		return primaryIndustry;
	}
	/**
	 * @param primaryIndustry the primaryIndustry to set
	 */
	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}
	/**
	 * @return the workflowState
	 */
	public String getWorkflowState() {
		return workflowState;
	}
	/**
	 * @param workflowState the workflowState to set
	 */
	public void setWorkflowState(String workflowState) {
		this.workflowState = workflowState;
	}

	public boolean isFilterEmpty() {
		boolean result = false;
		if(
				( user.isFilterEmpty() )
				&&
				(location.isFilterEmpty())
				 &&
				( StringUtils.isBlank(getSoId()))
				&&

				(workflowStateId == null || ( new Integer(-1).compareTo(workflowStateId)) == 0  )
				&&
				(referralId == null || ( new Integer(-1).compareTo(referralId)) == 0  )
				&&
				(primaryIndustryId == null || ( new Integer(-1).compareTo(primaryIndustryId)) == 0  )
				&&
				(spnetId == null || ( new Integer(-1).compareTo(spnetId)) == 0) 

		){

			result = true;
		}
		return result;
	}
	/**
	 * @return the referralId
	 */
	public Integer getReferralId() {
		return referralId;
	}
	/**
	 * @param referralId the referralId to set
	 */
	public void setReferralId(Integer referralId) {
		this.referralId = referralId;
	}
	/**
	 * @return the spnetId
	 */
	public Integer getSpnetId() {
		return spnetId;
	}
	/**
	 * @param spnetId the spnetId to set
	 */
	public void setSpnetId(Integer spnetId) {
		this.spnetId = spnetId;
	}
	@Override
	public String getSortOrder() {
		return sortOrder;
	}
	@Override
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getSortKey() {
		return sortKey;
	}
	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

}
