/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */

package com.newco.marketplace.api.beans.search.providerProfile;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.api.beans.firmDetail.FirmLocation;
import com.newco.marketplace.api.beans.firmDetail.FirmServices;
import com.newco.marketplace.search.vo.ProviderSearchResponseVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a bean class for storing all information of 
 * the company 
 * @author Infosys
 *
 */
@XStreamAlias("companyList")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderCompany {
	
	@XStreamAlias("companyId")   
	@XStreamAsAttribute()   
	private Integer companyId; 
	
	@XStreamAlias("businessName")
	private String businessName;
	
	@XStreamAlias("yearsInBusiness")
	private Integer yearsInBusiness;
	
	@XStreamAlias("businessStructure")
	private String businessStructure;
	
	@XStreamAlias("size")
	private String size;
	
	@XStreamAlias("overview")
	private String overview;
	
	@XStreamAlias("overallRating")	
	private float overallRating;
	
	@XStreamAlias("firmReviewCount")
	private Integer firmReviewCount;
	
	@XStreamAlias("numOfSLProjectCompleted")
	private Integer numOfSLProjectCompleted;
	
	@XStreamAlias("primaryIndustry")
	private  String primaryIndustry;
	
	@XStreamAlias("companyLogoURL")
	private String companyLogoURL;
	
	
	@XStreamAlias("firmOwner")
	private String firmOwner;
	
	/*@XStreamAlias("reviewCount")
	private String reviewCount;*/
	
	@XStreamAlias("hourlyRate")
	private String hourlyRate; 
	
	@XStreamAlias("location")
	private FirmLocation location;
	
	@XStreamAlias("services")
	private FirmServices services;
	
	@XStreamAlias("policy")
	private CompanyPolicy policy;
	
	@XStreamAlias("insurances")
	private CompanyInsurances insurances;
	
	@OptionalParam
	@XStreamImplicit(itemFieldName="providers")
	@XStreamAlias("providers")
	private List<SearchProviderType> providers;
	
	public ProviderCompany () {
		
	}
	
	public ProviderCompany (ProviderSearchResponseVO providerSearchResponseVO, boolean minInd) {
		this.setCompanyId(providerSearchResponseVO.getCompanyId());
		this.setBusinessName(providerSearchResponseVO.getBusinessName());
		this.setYearsInBusiness(providerSearchResponseVO
				.getYearsInBusiness());
		this.setBusinessStructure(providerSearchResponseVO
				.getBusinessStructure());
		this.setSize(providerSearchResponseVO.getCompanySize());
		this.setOverview(providerSearchResponseVO.getOverview());
		this.setOverallRating(providerSearchResponseVO
				.getOverallRating());
		this.setNumOfSLProjectCompleted(providerSearchResponseVO
				.getNumberOfSLProjectsCompleted());
		if(!(minInd)){
			//insurance and policy list not needed for Minimum result mode 
			this.policy = new CompanyPolicy(providerSearchResponseVO);			
			this.insurances = new CompanyInsurances(providerSearchResponseVO);	
		}
		this.primaryIndustry = providerSearchResponseVO.getPrimaryIndustry();
	}


	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public Integer getYearsInBusiness() {
		return yearsInBusiness;
	}

	public void setYearsInBusiness(Integer yearsInBusiness) {
		this.yearsInBusiness = yearsInBusiness;
	}

	public String getBusinessStructure() {
		return businessStructure;
	}

	public void setBusinessStructure(String businessStructure) {
		this.businessStructure = businessStructure;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public float getOverallRating() {
		return overallRating;
	}

	public void setOverallRating(float overallRating) {
		this.overallRating = overallRating;
	}

	public Integer getFirmReviewCount() {
		return firmReviewCount;
	}

	public void setFirmReviewCount(Integer firmReviewCount) {
		this.firmReviewCount = firmReviewCount;
	}

	public Integer getNumOfSLProjectCompleted() {
		return numOfSLProjectCompleted;
	}

	public void setNumOfSLProjectCompleted(Integer numOfSLProjectCompleted) {
		this.numOfSLProjectCompleted = numOfSLProjectCompleted;
	}

	public String getPrimaryIndustry() {
		return primaryIndustry;
	}

	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}
	
	

	public String getCompanyLogoURL() {
		return companyLogoURL;
	}

	public void setCompanyLogoURL(String companyLogoURL) {
		this.companyLogoURL = companyLogoURL;
	}
	
	

	public String getFirmOwner() {
		return firmOwner;
	}

	public void setFirmOwner(String firmOwner) {
		this.firmOwner = firmOwner;
	}

	
	
	/*public String getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(String reviewCount) {
		this.reviewCount = reviewCount;
	}
*/
	public String getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(String hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
	
	

	public FirmLocation getLocation() {
		return location;
	}

	public void setLocation(FirmLocation location) {
		this.location = location;
	}

	public FirmServices getServices() {
		return services;
	}

	public void setServices(FirmServices services) {
		this.services = services;
	}

	public CompanyPolicy getPolicy() {
		return policy;
	}

	public void setPolicy(CompanyPolicy policy) {
		this.policy = policy;
	}

	public CompanyInsurances getInsurances() {
		return insurances;
	}

	public void setInsurances(CompanyInsurances insurances) {
		this.insurances = insurances;
	}

	public List<SearchProviderType> getProviders() {
		return providers;
	}

	public void setProviders(List<SearchProviderType> providers) {
		this.providers = providers;
	}

	

}
