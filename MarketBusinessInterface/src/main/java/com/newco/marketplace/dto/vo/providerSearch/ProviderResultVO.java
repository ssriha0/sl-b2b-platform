package com.newco.marketplace.dto.vo.providerSearch;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.dto.vo.provider.AdditionalInsuranceVO;
import com.newco.marketplace.dto.vo.provider.VendorCredentialsVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.vo.MPBaseVO;

/**
 * @author zizrale
 *
 */
public class ProviderResultVO extends MPBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2381160269187382921L;
	private String providerFirstName;
	private String providerLastName;
	private String providerFullName;
	private ArrayList<SkillNodeVO> skillNodes;
	
	private ArrayList<ProviderCredentialResultsVO> credentials;
	private ArrayList<ProviderLanguageResultsVO> languages;
	private List<InsuranceResultVO> vendorInsuranceTypes;
	private List<VendorCredentialsVO> vendorCredentials;
	private List<ProviderSPNetStateResultsVO> spnetStates;
	private List<InsuranceResultVO> addtionalInsuranceTypes;

	private int resourceId;
	private double providerRating;
	private double distanceFromBuyer;
	private double providerLatitude;
	private double providerLongitude;
	private SurveyRatingsVO providerStarRating;
	private Integer backgroundCheckStatus;
	private Boolean isSLVerified;
	private Integer vendorID;
	private Double distance;
	private String city;
	private String state;
	private Integer percentageMatch;
	private Boolean selected = false;
	private Date createdDate;
	private Integer totalSOCompleted;
	private Integer performanceLevel = -1;
	
	private Integer filteredPerfLevel;
	private Integer filterSpnetId;
	private String  filteredSpnName;
	private String  filteredPerfLevelDesc;
	
	// for storing Counter offer details for provider
	private Integer providerRespid;
	private Double incrSpendLimit; 
	private Double totalLabor;
	private Date serviceDate1;
	private String serviceTimeStart;
	private Date serviceDate2;
	private String serviceTimeEnd;
	private Timestamp offerExpirationDate;
	private String offerExpirationDateString;
	// for storing dispatch address needed for Google directions link
	private String resourceDispatchAddr;
	private String street1;
	private String street2;
	private String zip;
	private Timestamp conditionalChangeDate1;
	private Timestamp conditionalChangeDate2;
	private String conditionalStartTime;
	private String conditionalEndTime;
	private Double groupCondIncrSpendLimit;
	private Double performanceScore;
	private Double firmPerformanceScore;
	private String firmName;
	
	private List<AdditionalInsuranceVO> additionalInsuranceList;
	
	public String getFirmName() {
		return firmName;
	}
	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}
	public Timestamp getConditionalChangeDate1() {
		return conditionalChangeDate1;
	}
	public void setConditionalChangeDate1(Timestamp conditionalChangeDate1) {
		this.conditionalChangeDate1 = conditionalChangeDate1;
	}
	public Timestamp getConditionalChangeDate2() {
		return conditionalChangeDate2;
	}
	public void setConditionalChangeDate2(Timestamp conditionalChangeDate2) {
		this.conditionalChangeDate2 = conditionalChangeDate2;
	}
	public String getConditionalStartTime() {
		return conditionalStartTime;
	}
	public void setConditionalStartTime(String conditionalStartTime) {
		this.conditionalStartTime = conditionalStartTime;
	}
	public String getConditionalEndTime() {
		return conditionalEndTime;
	}
	public void setConditionalEndTime(String conditionalEndTime) {
		this.conditionalEndTime = conditionalEndTime;
	}
	public Integer getPerformanceLevel()
	{
		return performanceLevel;
	}
	public void setPerformanceLevel(Integer performanceLevel)
	{
		this.performanceLevel = performanceLevel;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public ArrayList<ProviderLanguageResultsVO> getLanguages() {
		return languages;
	}
	public void setLanguages(ArrayList<ProviderLanguageResultsVO> languages) {
		this.languages = languages;
	}
	public int getResourceId() {
		return resourceId;
	}
	public void setIResourceId(Integer inResourceId) {
		this.resourceId= (inResourceId == null)?0:inResourceId.intValue();
	}
	public double getDistanceFromBuyer() {
		return distanceFromBuyer;
	}
	public void setIDistanceFromBuyer(Double idistanceFromBuyer) {
		this.distanceFromBuyer = idistanceFromBuyer==null?0:idistanceFromBuyer.doubleValue();
	}
	public String getProviderFirstName() {
		return StringUtils.capitalize(providerFirstName.toLowerCase());
	}
	public void setProviderFirstName(String providerFirstName) {
		this.providerFirstName = providerFirstName;
	}
	public String getProviderLastName() {
		return StringUtils.capitalize(providerLastName.toLowerCase());
	}
	public void setProviderLastName(String providerLastName) {
		this.providerLastName = providerLastName;
	}
	public double getProviderRating() {
		return providerRating;
	}
	public void setIProviderRating(Double iproviderRating) {
		this.providerRating = (iproviderRating==null)?0:iproviderRating.doubleValue();
	}
	public ArrayList<SkillNodeVO> getSkillNodes() {
		return skillNodes;
	}
	public void setSkillNodes(ArrayList<SkillNodeVO> skillNodes) {
		this.skillNodes = skillNodes;
	}

	public ArrayList<ProviderCredentialResultsVO> getCredentials() {
		return credentials;
	}
	public void setCredentials(ArrayList<ProviderCredentialResultsVO> credentials) {
		this.credentials = credentials;
	}
	public double getProviderLatitude() {
		return providerLatitude;
	}
	public void setProviderLatitude(double providerLatitude) {
		this.providerLatitude = providerLatitude;
	}
	public void setIProviderLatitude(Double iproviderLatitude) {
		this.providerLatitude = iproviderLatitude==null?0:iproviderLatitude.doubleValue();
	}
	public double getProviderLongitude() {
		return providerLongitude;
	}
	public void setProviderLongitude(double providerLongitude) {
		this.providerLongitude = providerLongitude;
	}
	public void setIProviderLongitude(Double iproviderLongitude) {
		this.providerLongitude = iproviderLongitude==null?0:iproviderLongitude.doubleValue();
	}
	public SurveyRatingsVO getProviderStarRating() {
		return providerStarRating;
	}
	public void setProviderStarRating(SurveyRatingsVO providerStarRating) {
		this.providerStarRating = providerStarRating;
	}	
	
	public Integer getBackgroundCheckStatus() {
		return backgroundCheckStatus;
	}
	public void setBackgroundCheckStatus(Integer backgroundCheckStatus) {
		this.backgroundCheckStatus = backgroundCheckStatus;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public Integer getPercentageMatch() {
		return percentageMatch;
	}
	public void setPercentageMatch(Integer percentageMatch) {
		this.percentageMatch = percentageMatch;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getVendorID() {
		return vendorID;
	}
	public void setVendorID(Integer vendorID) {
		this.vendorID = vendorID;
	}		
	public List<InsuranceResultVO> getVendorInsuranceTypes() {
		return vendorInsuranceTypes;
	}
	public void setVendorInsuranceTypes(List<InsuranceResultVO> vendorInsuranceTypes) {
		this.vendorInsuranceTypes = vendorInsuranceTypes;
	}
	
	public Boolean getIsSLVerified() {
		return isSLVerified;
	}
	public void setIsSLVerified(Boolean isSLVerified) {
		this.isSLVerified = isSLVerified;
	}
	
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	
	/**
	 * @return the vendorCredentials
	 */
	public List<VendorCredentialsVO> getVendorCredentials() {
		return vendorCredentials;
	}
	/**
	 * @param vendorCredentials the vendorCredentials to set
	 */
	public void setVendorCredentials(List<VendorCredentialsVO> vendorCredentials) {
		this.vendorCredentials = vendorCredentials;
	}
	/**
	 * @return the totalSOCompleted
	 */
	public Integer getTotalSOCompleted() {
		return totalSOCompleted;
	}
	/**
	 * @param totalSOCompleted the totalSOCompleted to set
	 */
	public void setTotalSOCompleted(Integer totalSOCompleted) {
		this.totalSOCompleted = totalSOCompleted;
	}
	@Override
	public String toString () {
	    return ("<ProviderResultVO>" 
	            + " | " + providerFirstName
	            + " | " + providerLastName
	            + " | " + providerRating
	            + " | " + distanceFromBuyer
	            + " | " + skillNodes
	            + " | " + resourceId
	            + " | " + providerStarRating
	            + " | " + providerLatitude
	            + " | " + providerLongitude
	            + " | \n " + credentials
	            + " | \n " + languages
	            + " | \n " + skillNodes
	            + "</ProviderResultVO>");
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public void setProviderRating(double providerRating) {
		this.providerRating = providerRating;
	}
	public void setDistanceFromBuyer(double distanceFromBuyer) {
		this.distanceFromBuyer = distanceFromBuyer;
	}
	/**
	 * @return the spnetStates
	 */
	public List<ProviderSPNetStateResultsVO> getSpnetStates() {
		return spnetStates;
	}
	/**
	 * @param spnetStates the spnetStates to set
	 */
	public void setSpnetStates(List<ProviderSPNetStateResultsVO> spnetStates) {
		this.spnetStates = spnetStates;
	}
	public Integer getFilteredPerfLevel() {
		return filteredPerfLevel;
	}
	public void setFilteredPerfLevel(Integer filteredPerfLevel) {
		this.filteredPerfLevel = filteredPerfLevel;
	}
	public Integer getFilterSpnetId() {
		return filterSpnetId;
	}
	public void setFilterSpnetId(Integer filterSpnetId) {
		this.filterSpnetId = filterSpnetId;
	}
	public String getFilteredSpnName() {
		return filteredSpnName;
	}
	public void setFilteredSpnName(String filteredSpnName) {
		this.filteredSpnName = filteredSpnName;
	}
	/**
	 * description for spnet performance level 
	 * @return
	 */
	public String getFilteredPerfLevelDesc() {
		return filteredPerfLevelDesc;
	}
	public void setFilteredPerfLevelDesc(String filteredPerfLevelDesc) {
		this.filteredPerfLevelDesc = filteredPerfLevelDesc;
	}
	public Double getIncrSpendLimit() {
		return incrSpendLimit;
	}
	public void setIncrSpendLimit(Double incrSpendLimit) {
		this.incrSpendLimit = incrSpendLimit;
	}

	public String getServiceTimeStart() {
		return serviceTimeStart;
	}
	public void setServiceTimeStart(String serviceTimeStart) {
		this.serviceTimeStart = serviceTimeStart;
	}
	
	public String getServiceTimeEnd() {
		return serviceTimeEnd;
	}
	public void setServiceTimeEnd(String serviceTimeEnd) {
		this.serviceTimeEnd = serviceTimeEnd;
	}
	public String getResourceDispatchAddr() {
		return resourceDispatchAddr;
	}
	public void setResourceDispatchAddr(String resourceDispatchAddr) {
		this.resourceDispatchAddr = resourceDispatchAddr;
	}
	public Integer getProviderRespid() {
		return providerRespid;
	}
	public void setProviderRespid(Integer providerRespid) {
		this.providerRespid = (providerRespid == null)? 0:providerRespid.intValue();
	}
	
	public Double getTotalLabor() {
		return totalLabor;
	}
	public void setTotalLabor(Double totalLabor) {
		this.totalLabor = totalLabor;
	}
	public String getStreet1() {
		return street1;
	}
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getServiceDate1() {
		if(serviceDate1!=null){
			return DateUtils.getFormatedDate(serviceDate1, "MM/dd/yyyy");
		}else{
			return StringUtils.EMPTY;
		}
	}
	public void setServiceDate1(Date serviceDate1) {
		this.serviceDate1 = serviceDate1;
	}
	public String getServiceDate2() {
		if(serviceDate2!=null){
			return DateUtils.getFormatedDate(serviceDate2, "MM/dd/yyyy");
		}else{
			return StringUtils.EMPTY;
		}
	}
	public void setServiceDate2(Date serviceDate2) {
		this.serviceDate2 = serviceDate2;
	}
	public Double getGroupCondIncrSpendLimit() {
		return groupCondIncrSpendLimit;
	}
	public void setGroupCondIncrSpendLimit(Double groupCondIncrSpendLimit) {
		this.groupCondIncrSpendLimit = groupCondIncrSpendLimit;
	}
	public Timestamp getOfferExpirationDate() {
		return offerExpirationDate;
	}
	public void setOfferExpirationDate(Timestamp offerExpirationDate) {
		this.offerExpirationDate = offerExpirationDate;
	}
	
	public String getOfferExpirationDateString() {
		return offerExpirationDateString;
	}
	public void setOfferExpirationDateString(String offerExpirationDateString) {
		this.offerExpirationDateString = offerExpirationDateString;
	}
	public String getProviderFullName() {
		return providerFullName;
	}
	public void setProviderFullName(String providerFullName) {
		this.providerFullName = providerFullName;
	}
	public Double getPerformanceScore() {
		return performanceScore;
	}
	public void setPerformanceScore(Double performanceScore) {
		this.performanceScore = performanceScore;
	}
	public Double getFirmPerformanceScore() {
		return firmPerformanceScore;
	}
	public void setFirmPerformanceScore(Double firmPerformanceScore) {
		this.firmPerformanceScore = firmPerformanceScore;
	}
	public List<AdditionalInsuranceVO> getAdditionalInsuranceList() {
		return additionalInsuranceList;
	}
	public void setAdditionalInsuranceList(
			List<AdditionalInsuranceVO> additionalInsuranceList) {
		this.additionalInsuranceList = additionalInsuranceList;
	}
	public List<InsuranceResultVO> getAddtionalInsuranceTypes() {
		return addtionalInsuranceTypes;
	}
	public void setAddtionalInsuranceTypes(
			List<InsuranceResultVO> addtionalInsuranceTypes) {
		this.addtionalInsuranceTypes = addtionalInsuranceTypes;
	}
	
	
	

}
