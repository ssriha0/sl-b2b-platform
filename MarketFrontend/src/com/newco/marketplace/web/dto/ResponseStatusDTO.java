package com.newco.marketplace.web.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.activitylog.domain.ActivityViewStatusName;

public class ResponseStatusDTO extends SOWBaseTabDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8835852878039152726L;
	private String soId;
	private Integer resourceId;
	private Integer vendorId;
	private String conditionalChangeDate1;
	private String conditionalChangeDate2;
	private String conditionalStartTime;
	private String conditionalEndTime;
	private String serviceLocationTimeZone;
	private Timestamp conditionalExpirationDate;
	private String conditionalExpirationTime;
	private Double conditionalSpendLimit;
	private Integer responseId;
	private String responseDesc;
	private Integer responseReasonId;
	private String responseReasonDesc;
	private String responseComment;
	private Timestamp responseDate;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private Double serviceLiveRating;
	private Double myRating;
	//fields from mkt_maker_providers_response table
	private String mktMakerComments;
	private String callStatusId;
	private String modifyingAdmin;
	private String modifiedDateTime;
	private Integer postedSOCount;
	private String callStatusDescription;
	//Rating numbers that map to star images, which are used to show values from frontend
	private int slRatingNumber;
	private int myRatingNumber;
	private Integer slRatingsCount = 0;
	private Integer myRatingsCount = 0;	
	//Adding callStatusDescription, callStatusIdSelected,mktMakerComment to obtain proper data for multiple providers
	//private String callStatusDescription;
	private String mktMakerComment;
	private String callStatusIdSelected;
	
	private List<ProviderSPNDTO> networkAndPerformanceLevelList;
	private Long distanceFromBuyer;
	
	private Boolean showTieredRoutingInfo;

	private String priceModel = "";
	private BigDecimal partsAndMaterialsBid;
	private BigDecimal totalHoursBid;
	private BigDecimal totalLaborBid;
	
	private Long expiresInDays;
	private Long expiresInHours;
	private Long expiresInMinutes;
	
	private Integer selectedProvidersInFirm;
	
	// Activity Log Information
	private String viewStatusName;
	private Long activityId;
	
	private Timestamp routedDate;
	private Double score;
	private Double firmScore;
	private String rank;
	private Integer tierId;
	
	public Long getDistanceFromBuyer() {
		return distanceFromBuyer;
	}
	public void setDistanceFromBuyer(Long distanceFromBuyer) {
		this.distanceFromBuyer = distanceFromBuyer;
	}
	public String getServiceLocationTimeZone() {
		return serviceLocationTimeZone;
	}
	public void setServiceLocationTimeZone(String serviceLocationTimeZone) {
		this.serviceLocationTimeZone = serviceLocationTimeZone;
	}
	public String getSoId() {
		return soId;
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
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
		
	public String getConditionalChangeDate1() {
		return conditionalChangeDate1;
	}
	public void setConditionalChangeDate1(String conditionalChangeDate1) {
		this.conditionalChangeDate1 = conditionalChangeDate1;
	}
	public String getConditionalChangeDate2() {
		return conditionalChangeDate2;
	}
	public void setConditionalChangeDate2(String conditionalChangeDate2) {
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
	public Timestamp getConditionalExpirationDate() {
		return conditionalExpirationDate;
	}
	public void setConditionalExpirationDate(Timestamp conditionalExpirationDate) {
		this.conditionalExpirationDate = conditionalExpirationDate;
	}
	public Double getConditionalSpendLimit() {
		return conditionalSpendLimit;
	}
	public void setConditionalSpendLimit(Double conditionalSpendLimit) {
		this.conditionalSpendLimit = conditionalSpendLimit;
	}
	public String getResponseDesc() {
		return responseDesc;
	}
	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}
	public String getResponseReasonDesc() {
		return responseReasonDesc;
	}
	public void setResponseReasonDesc(String responseReasonDesc) {
		this.responseReasonDesc = responseReasonDesc;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Double getServiceLiveRating() {
		return serviceLiveRating;
	}
	public void setServiceLiveRating(Double serviceLiveRating) {
		this.serviceLiveRating = serviceLiveRating;
	}
	public Double getMyRating() {
		return myRating;
	}
	public void setMyRating(Double myRating) {
		this.myRating = myRating;
	}
	public Integer getResponseId() {
		return responseId;
	}
	public void setResponseId(Integer responseId) {
		this.responseId = responseId;
	}
	public Integer getResponseReasonId() {
		return responseReasonId;
	}
	public void setResponseReasonId(Integer responseReasonId) {
		this.responseReasonId = responseReasonId;
	}
	public int getSlRatingNumber() {
		return slRatingNumber;
	}
	public void setSlRatingNumber(int slRatingNumber) {
		this.slRatingNumber = slRatingNumber;
	}
	public int getMyRatingNumber() {
		return myRatingNumber;
	}
	public void setMyRatingNumber(int myRatingNumber) {
		this.myRatingNumber = myRatingNumber;
	}
	public Integer getSlRatingsCount() {
		return slRatingsCount;
	}
	public void setSlRatingsCount(Integer slRatingsCount) {
		this.slRatingsCount = slRatingsCount;
	}
	public Integer getMyRatingsCount() {
		return myRatingsCount;
	}
	public void setMyRatingsCount(Integer myRatingsCount) {
		this.myRatingsCount = myRatingsCount;
	}
	public String getConditionalExpirationTime() {
		return conditionalExpirationTime;
	}
	public void setConditionalExpirationTime(String conditionalExpirationTime) {
		this.conditionalExpirationTime = conditionalExpirationTime;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getMktMakerComments() {
		return mktMakerComments;
	}
	public void setMktMakerComments(String mktMakerComments) {
		this.mktMakerComments = mktMakerComments;
	}
	public String getCallStatusId() {
		return callStatusId;
	}
	public void setCallStatusId(String callStatusId) {
		this.callStatusId = callStatusId;
	}

	public String getModifiedDateTime() {
		return modifiedDateTime;
	}
	public void setModifiedDateTime(String modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}
	public String getModifyingAdmin() {
		return modifyingAdmin;
	}
	public void setModifyingAdmin(String modifyingAdmin) {
		this.modifyingAdmin = modifyingAdmin;
	}

	public String getTabIdentifier() {
		return "";
	}

	
	public void validate() {
		setErrors(new ArrayList<IError>());
		setWarnings(new ArrayList<IWarning>());
		//SecurityContext securityContext = (SecurityContext) ServletActionContext.getRequest().getSession().getAttribute("SecurityContext");
		//String role = securityContext.getRole();
		
		if (StringUtils.equals(this.callStatusId , "#"))
			{
		 	addError("CallStatus", OrderConstants.SO_MARKET_MAKER_CALL_STATUS_REQUIRED, OrderConstants.SOW_TAB_ERROR);
		 	

			}
		if ((StringUtils.isBlank(this.mktMakerComments))) 
		{
		 	addError("Comments", OrderConstants.SO_MARKET_MAKER_COMMENT_REQUIRED, OrderConstants.SOW_TAB_ERROR);
		}
	}
	public String getCallStatusDescription() {
		return callStatusDescription;
	}
	public void setCallStatusDescription(String callStatusDescription) {
		this.callStatusDescription = callStatusDescription;
	}
	public String getMktMakerComment() {
		return mktMakerComment;
	}
	public void setMktMakerComment(String mktMakerComment) {
		this.mktMakerComment = mktMakerComment;
	}
	public String getCallStatusIdSelected() {
		return callStatusIdSelected;
	}
	public void setCallStatusIdSelected(String callStatusIdSelected) {
		this.callStatusIdSelected = callStatusIdSelected;
	}
	public Integer getPostedSOCount() {
		return postedSOCount;
	}
	public void setPostedSOCount(Integer postedSOCount) {
		this.postedSOCount = postedSOCount;
	}
	public List<ProviderSPNDTO> getNetworkAndPerformanceLevelList()
	{
		return networkAndPerformanceLevelList;
	}
	public void setNetworkAndPerformanceLevelList(List<ProviderSPNDTO> networkAndPerformanceLevelList)
	{
		this.networkAndPerformanceLevelList = networkAndPerformanceLevelList;
	}
	public Boolean getShowTieredRoutingInfo() {
		return showTieredRoutingInfo;
	}
	public void setShowTieredRoutingInfo(Boolean showTieredRoutingInfo) {
		this.showTieredRoutingInfo = showTieredRoutingInfo;
	}
	
	public BigDecimal getTotalLaborBid() {
		return this.totalLaborBid;
	}
	public void setTotalLaborBid(BigDecimal totalLaborBid) {
		this.totalLaborBid = totalLaborBid;		
	}
	
	public BigDecimal getTotalHoursBid() {
		return this.totalHoursBid;
	}
	public void setTotalHousBid(BigDecimal totalHoursBid) {
		this.totalHoursBid = totalHoursBid;		
	}
	
	public BigDecimal getPartsAndMaterialsBid() {
		return this.partsAndMaterialsBid;
	}
	public void setPartsAndMaterialsBid(BigDecimal partsAndMaterialsBid) {
		this.partsAndMaterialsBid = partsAndMaterialsBid;
		
	}
	public void setPriceModel(String priceModel) {
		this.priceModel = priceModel;
	}
	public String getPriceModel() {
		return priceModel;
	}
	public void setExpiresInHours(Long expiresInHours) {
		this.expiresInHours = expiresInHours;
	}
	public Long getExpiresInHours() {
		return expiresInHours;
	}
	public void setExpiresInMinutes(Long expiresInMinutes) {
		this.expiresInMinutes = expiresInMinutes;
	}
	public Long getExpiresInMinutes() {
		return expiresInMinutes;
	}
	public void setExpiresInDays(Long expiresInDays) {
		this.expiresInDays = expiresInDays;
	}
	public Long getExpiresInDays() {
		return expiresInDays;
	}
	public void setSelectedProvidersInFirm(Integer selectedProvidersInFirm) {
		this.selectedProvidersInFirm = selectedProvidersInFirm;
	}
	public Integer getSelectedProvidersInFirm() {
		return selectedProvidersInFirm;
	}
	public void setResponseComment(String responseComment) {
		this.responseComment = responseComment;
	}
	public String getResponseComment() {
		return responseComment;
	}
	public void setResponseDate(Timestamp responseDate) {
		this.responseDate = responseDate;
	}
	public Timestamp getResponseDate() {
		return responseDate;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public Long getActivityId() {
		return activityId;
	}
	public String getViewStatusName() {
		return viewStatusName;
	}
	public void setViewStatusName(String viewStatusName) {
		this.viewStatusName = viewStatusName;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public Double getFirmScore() {
		return firmScore;
	}
	public void setFirmScore(Double firmScore) {
		this.firmScore = firmScore;
	}
	public Timestamp getRoutedDate() {
		return routedDate;
	}
	public void setRoutedDate(Timestamp routedDate) {
		this.routedDate = routedDate;
	}
	public Integer getTierId() {
		return tierId;
	}
	public void setTierId(Integer tierId) {
		this.tierId = tierId;
	}

}
