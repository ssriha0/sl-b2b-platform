/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */

package com.newco.marketplace.api.beans.search.providerProfile;

import com.newco.marketplace.search.vo.ProviderSearchResponseVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class for storing all information of 
 * the company 
 * @author Infosys
 *
 */
@XStreamAlias("company")
public class Company {
	
	@XStreamAlias("companyId")   
	@XStreamAsAttribute()   
	private Integer companyId; 
	
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
	
	@XStreamAlias("numOfSLProjectCompleted")
	private Integer numOfSLProjectCompleted;
	
	@XStreamAlias("primaryIndustry")
	private  String primaryIndustry;
	
	@XStreamAlias("policy")
	private Policy policy;
	
	@XStreamAlias("insurances")
	private Insurances insurances;
	
	public Company () {
		
	}
	
	public Company (ProviderSearchResponseVO providerSearchResponseVO) {
		this.setCompanyId(providerSearchResponseVO.getCompanyId());
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
		
		this.policy = new Policy(providerSearchResponseVO);			
		this.insurances = new Insurances(providerSearchResponseVO);	
		this.primaryIndustry = providerSearchResponseVO.getPrimaryIndustry();
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

	public Integer getNumOfSLProjectCompleted() {
		return numOfSLProjectCompleted;
	}

	public void setNumOfSLProjectCompleted(Integer numOfSLProjectCompleted) {
		this.numOfSLProjectCompleted = numOfSLProjectCompleted;
	}

	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	public Insurances getInsurances() {
		return insurances;
	}

	public void setInsurances(Insurances insurances) {
		this.insurances = insurances;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public float getOverallRating() {
		return overallRating;
	}

	public void setOverallRating(float overallRating) {
		this.overallRating = overallRating;
	}

	public String getPrimaryIndustry() {
		return primaryIndustry;
	}

	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}

}
