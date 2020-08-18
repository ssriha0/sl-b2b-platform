/**
 * 
 */
package com.newco.marketplace.web.dto.provider;

import java.util.Map;

import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.vo.provider.CompanyProfileVO;
import com.newco.marketplace.vo.provider.PublicProfileVO;
import static com.newco.marketplace.web.action.provider.ProviderProfilePageConstants.MAX_SIDEWELL_FEEDBACKS;
import static com.newco.marketplace.web.action.provider.ProviderProfilePageConstants.MAX_MIDDLE_FEEDBACKS;

/**
 * This Class is a helper DTO which contains bunch of VO from Backend
 * @author hoza
 *
 */
public class ProviderInfoPagesDto extends BaseDto {
	private PublicProfileVO providerPublicInfo;
	private CompanyProfileVO companyPublicInfo;
	private Map<Integer,ResourceSkillAssignDto> skillsInfo;
	private LocationVO zipData;
	private Boolean isExternal =  Boolean.FALSE;
	private String googleMapKey;
	private Integer numFeedback;
	private Integer maxSWFeedbackShown = MAX_SIDEWELL_FEEDBACKS;
	private Integer maxMiddleFeedbackShow = MAX_MIDDLE_FEEDBACKS;
	private Boolean hasErrors = Boolean.FALSE;
	private Boolean isSecuredInfoViewable =  Boolean.FALSE;
	private String sharedSecretString;

	public PublicProfileVO getProviderPublicInfo() {
		return providerPublicInfo;
	}

	public void setProviderPublicInfo(PublicProfileVO providerPublicInfo) {
		this.providerPublicInfo = providerPublicInfo;
		this.setNumFeedback(this.providerPublicInfo.getFeedbacks().size());
	}

	public CompanyProfileVO getCompanyPublicInfo() {
		return companyPublicInfo;
	}

	public void setCompanyPublicInfo(CompanyProfileVO companyPublicInfo) {
		this.companyPublicInfo = companyPublicInfo;
	}

	

	public LocationVO getZipData() {
		return zipData;
	}

	public void setZipData(LocationVO zipData) {
		this.zipData = zipData;
	}

	public Map<Integer, ResourceSkillAssignDto> getSkillsInfo() {
		return skillsInfo;
	}

	public void setSkillsInfo(Map<Integer, ResourceSkillAssignDto> skillsInfo) {
		this.skillsInfo = skillsInfo;
	}
	public ResourceSkillAssignDto getSkillsInfoForNode(String nodeid) {
		return skillsInfo.get(new Integer(nodeid));
	}

	
	public Boolean getIsExternal() {
		return isExternal;
	}

	public void setIsExternal(Boolean isExternal) {
		this.isExternal = isExternal;
	}

	public String getGoogleMapKey() {
		return googleMapKey;
	}

	public void setGoogleMapKey(String googleMapKey) {
		this.googleMapKey = googleMapKey;
	}

	public Integer getNumFeedback() {
		return numFeedback;
	}

	public void setNumFeedback(Integer numFeedback) {
		this.numFeedback = numFeedback;
	}

	/**
	 * @return the maxSWFeedbackShown
	 */
	public Integer getMaxSWFeedbackShown() {
		return maxSWFeedbackShown;
	}

	public Integer getMaxMiddleFeedbackShow() {
		return maxMiddleFeedbackShow;
	}

	public Boolean getHasErrors() {
		return hasErrors;
	}

	public void setHasErrors(Boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	public Boolean getIsSecuredInfoViewable() {
		return isSecuredInfoViewable;
	}

	public void setIsSecuredInfoViewable(Boolean isSecuredInfoViewable) {
		this.isSecuredInfoViewable = isSecuredInfoViewable;
	}

	public String getSharedSecretString()
	{
		return sharedSecretString;
	}

	public void setSharedSecretString(String sharedSecretString)
	{
		this.sharedSecretString = sharedSecretString;
	}

}
