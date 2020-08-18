/**
 *
 */
package com.newco.marketplace.vo.searchportal;

import org.apache.commons.lang.StringUtils;

/**
 * @author hoza
 *
 */
public class SearchPortalServiceProviderVO extends BaseSearchPortalVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1868280739496261050L;

	//I could have moved these two into the base one.. but then I am afraid that the bseVO might be too heavy loaded
	private SearchPortalUserVO user;
	private SearchPortalLocationVO location;
	private Integer primaryVerticleId;
	private Integer secondarySkillId;
	private Integer skillCategoryId;
	private Integer workflowStateId;
	private String serviceProStatus;
	private Integer bgCheckStateId;
	private Integer spnId;
	private Integer spnetId;
	private Integer primaryIndustryId;
	private String primaryIndustry;
	private String sortOrder;
    private String sortKey;
   
  //code changes for SLT-1602
  	private Boolean slNameChange;
  	  	
	public Boolean getSlNameChange() {
		return slNameChange;
	}
	public void setSlNameChange(Boolean slNameChange) {
		this.slNameChange = slNameChange;
	}
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
	 * @return the primaryVerticleId
	 */
	public Integer getPrimaryVerticleId() {
		return primaryVerticleId;
	}
	/**
	 * @param primaryVerticleId the primaryVerticleId to set
	 */
	public void setPrimaryVerticleId(Integer primaryVerticleId) {
		this.primaryVerticleId = primaryVerticleId;
	}
	/**
	 * @return the secondarySkillId
	 */
	public Integer getSecondarySkillId() {
		return secondarySkillId;
	}
	/**
	 * @param secondarySkillId the secondarySkillId to set
	 */
	public void setSecondarySkillId(Integer secondarySkillId) {
		this.secondarySkillId = secondarySkillId;
	}
	/**
	 * @return the skillCategoryId
	 */
	public Integer getSkillCategoryId() {
		return skillCategoryId;
	}
	/**
	 * @param skillCategoryId the skillCategoryId to set
	 */
	public void setSkillCategoryId(Integer skillCategoryId) {
		this.skillCategoryId = skillCategoryId;
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
	 * @return the bgCheckStateId
	 */
	public Integer getBgCheckStateId() {
		return bgCheckStateId;
	}
	/**
	 * @param bgCheckStateId the bgCheckStateId to set
	 */
	public void setBgCheckStateId(Integer bgCheckStateId) {
		this.bgCheckStateId = bgCheckStateId;
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
	 * @return the serviceProStatus
	 */
	public String getServiceProStatus() {
		return serviceProStatus;
	}
	/**
	 * @param serviceProStatus the serviceProStatus to set
	 */
	public void setServiceProStatus(String serviceProStatus) {
		this.serviceProStatus = serviceProStatus;
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

	public boolean isFilterEmpty() {
		boolean result = false;
		if(
				( user.isFilterEmpty() )
				&&
				(location.isFilterEmpty())
				 &&
				( StringUtils.isBlank(getSoId()))
				&&
				(primaryVerticleId == null || ( new Integer(-1).compareTo(primaryVerticleId)) == 0  )
				&&
				(secondarySkillId == null || ( new Integer(-1).compareTo(secondarySkillId)) == 0  )
				&&
				(skillCategoryId == null || ( new Integer(-1).compareTo(skillCategoryId)) == 0  )
				&&
				(workflowStateId == null || ( new Integer(-1).compareTo(workflowStateId)) == 0  )
				&&
				(bgCheckStateId == null || ( new Integer(-1).compareTo(bgCheckStateId)) == 0  )
				&&
				(primaryIndustryId == null || ( new Integer(-1).compareTo(primaryIndustryId)) == 0  )
				&&
				( (spnId == null || ( new Integer(-1).compareTo(spnId)) == 0) && (spnetId == null || ( new Integer(-1).compareTo(spnetId)) == 0)  )
		){

			result = true;
		}
		return result;
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
