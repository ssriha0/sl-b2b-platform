package com.newco.marketplace.dto.vo.serviceorder;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.sears.os.vo.SerializableBaseVO;

public class RoutedProvider extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5658069740402316295L;
	private String soId;
	private Integer resourceId;
	
	private Integer providerRespId;
	private Integer providerRespReasonId;
	private String providerRespComment;
	private Timestamp providerRespDate;
	private Timestamp conditionalChangeDate1;
	private Timestamp conditionalChangeDate2;
	private String conditionalStartTime;
	private String conditionalEndTime;
	private String serviceLocationTimeZone;
	private Timestamp conditionalExpirationDate;
	private String conditionalExpirationTime;
	private Double conditionalSpendLimit;
	private List<Integer> selectedCounterOfferReasonsList;
	private Integer selectedCounterOfferReasonId;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private String modifiedBy;
	private Integer vendorId;
	private Contact providerContact;
	private SoLocation providerLocation;
	private String priceModel;
	private BigDecimal totalLaborBid;
	private BigDecimal partsAndMaterialsBid;
	private BigDecimal totalHoursBid;
	
	//Market Maker Provider Response
	private MarketMakerProviderResponse mktMakerProvResponse;
	
	private Integer postedSOCount;
	private boolean isGroupedOrder;
	private List<String> soIds = new ArrayList<String>();
	
	//Keep the calculated ratings for this Provider
	private SurveyRatingsVO providerStarRating;
	
	//The Description for Provider Response Id
	private String providerRespDescription;
	private Boolean emailSent;
	private Integer tierId;
	private Integer spnId;
	private String spnName;
	private Integer performanceId;
	private String performanceDesc;
	private Long distanceFromBuyer;
	private Timestamp routedDate;
	private Double perfScore;
	private Double firmPerfScore;
	private String rank;
	private Date completedDate;
	private String firstName;
	private String lastName;
	private String firmName;
	
	public String getFirmName() {
		return firmName;
	}
	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}
	public Double getPerfScore() {
		return perfScore;
	}
	public void setPerfScore(Double perfScore) {
		this.perfScore = perfScore;
	}
	public Long getDistanceFromBuyer() {
		return distanceFromBuyer;
	}
	public void setDistanceFromBuyer(Long distanceFromBuyer) {
		this.distanceFromBuyer = distanceFromBuyer;
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
	public Integer getProviderRespId() {
		return providerRespId;
	}
	public void setProviderRespId(Integer providerRespId) {
		this.providerRespId = providerRespId;
	}
	public Integer getProviderRespReasonId() {
		return providerRespReasonId;
	}
	public void setProviderRespReasonId(Integer providerRespReasonId) {
		this.providerRespReasonId = providerRespReasonId;
	}
	public String getProviderRespComment() {
		return providerRespComment;
	}
	public void setProviderRespComment(String providerRespComment) {
		this.providerRespComment = providerRespComment;
	}
	public Timestamp getProviderRespDate() {
		return providerRespDate;
	}
	public void setProviderRespDate(Timestamp providerRespDate) {
		this.providerRespDate = providerRespDate;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Contact getProviderContact() {
		return providerContact;
	}
	public void setProviderContact(Contact providerContact) {
		this.providerContact = providerContact;
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
	public SurveyRatingsVO getProviderStarRating() {
		return providerStarRating;
	}
	public void setProviderStarRating(SurveyRatingsVO providerStarRating) {
		this.providerStarRating = providerStarRating;
	}
	public String getServiceLocationTimeZone() {
		return serviceLocationTimeZone;
	}
	public void setServiceLocationTimeZone(String serviceLocationTimeZone) {
		this.serviceLocationTimeZone = serviceLocationTimeZone;
	}
	public SoLocation getProviderLocation() {
		return providerLocation;
	}
	public void setProviderLocation(SoLocation providerLocation) {
		this.providerLocation = providerLocation;
	}
	public String getConditionalExpirationTime() {
		return conditionalExpirationTime;
	}
	public void setConditionalExpirationTime(String conditionalExpirationTime) {
		this.conditionalExpirationTime = conditionalExpirationTime;
	}
	
	public Integer getPostedSOCount() {
		return postedSOCount;
	}
	public void setPostedSOCount(Integer postedSOCount) {
		this.postedSOCount = postedSOCount;
	}
	public String getProviderRespDescription() {
		return providerRespDescription;
	}
	public void setProviderRespDescription(String providerRespDescription) {
		this.providerRespDescription = providerRespDescription;
	}
	
		public boolean isGroupedOrder() {
		return isGroupedOrder;
	}
	public void setGroupedOrder(boolean isGroupedOrder) {
		this.isGroupedOrder = isGroupedOrder;
	}
	public List<String> getSoIds() {
		return soIds;
	}
	public void setSoIds(List<String> soIds) {
		this.soIds = soIds;
	}
	/**
	 * @return the emailSent
	 */
	public Boolean getEmailSent() {
		return emailSent;
	}
	/**
	 * @param emailSent the emailSent to set
	 */
	public void setEmailSent(Boolean emailSent) {
		this.emailSent = emailSent;
	}
	/**
	 * @return the tierId
	 */
	public Integer getTierId() {
		return tierId;
	}
	/**
	 * @param tierId the tierId to set
	 */
	public void setTierId(Integer tierId) {
		this.tierId = tierId;
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
	 * @return the spnaName
	 */
	public String getSpnName() {
		return spnName;
	}
	/**
	 * @param spnaName the spnaName to set
	 */
	public void setSpnName(String spnName) {
		this.spnName = spnName;
	}
	/**
	 * @return the performanceId
	 */
	public Integer getPerformanceId() {
		return performanceId;
	}
	/**
	 * @param performanceId the performanceId to set
	 */
	public void setPerformanceId(Integer performanceId) {
		this.performanceId = performanceId;
	}
	/**
	 * @return the performanceDesc
	 */
	public String getPerformanceDesc() {
		return performanceDesc;
	}
	/**
	 * @param performanceDesc the performanceDesc to set
	 */
	public void setPerformanceDesc(String performanceDesc) {
		this.performanceDesc = performanceDesc;
	}
	
	/**
	 * @return
	 */
	public Timestamp getRoutedDate() {
		return routedDate;
	}
	/**
	 * @param routedDate
	 */
	public void setRoutedDate(Timestamp routedDate) {
		this.routedDate = routedDate;
	}
	/**
	 * @return
	 */
	public String getPriceModel() {
		return priceModel;
	}
	/**
	 * @param priceModel
	 */
	public void setPriceModel(String priceModel) {
		this.priceModel = priceModel;
	}
	/**
	 * @return
	 */
	public BigDecimal getTotalLaborBid() {
		return totalLaborBid;
	}
	/** 
	 * @param totalLaborBid
	 */
	public void setTotalLaborBid(BigDecimal totalLaborBid) {
		this.totalLaborBid = totalLaborBid;
	}
	/**
	 * @return
	 */
	public BigDecimal getPartsAndMaterialsBid() {
		return partsAndMaterialsBid;
	}
	/**
	 * @param partsAndMaterialsBid
	 */
	public void setPartsAndMaterialsBid(BigDecimal partsAndMaterialsBid) {
		this.partsAndMaterialsBid = partsAndMaterialsBid;
	}
	/**
	 * @return
	 */
	public BigDecimal getTotalHoursBid() {
		return totalHoursBid;
	}
	/**
	 * @param totalHoursBid
	 */
	public void setTotalHoursBid(BigDecimal totalHoursBid) {
		this.totalHoursBid = totalHoursBid;
	}
	public List<Integer> getSelectedCounterOfferReasonsList() {
		return selectedCounterOfferReasonsList;
	}
	public void setSelectedCounterOfferReasonsList(
			List<Integer> selectedCounterOfferReasonsList) {
		this.selectedCounterOfferReasonsList = selectedCounterOfferReasonsList;
	}
	public Integer getSelectedCounterOfferReasonId() {
		return selectedCounterOfferReasonId;
	}
	public void setSelectedCounterOfferReasonId(Integer selectedCounterOfferReasonId) {
		this.selectedCounterOfferReasonId = selectedCounterOfferReasonId;
	}
	/**
	 * @return the mktMakerProvResponse
	 */
	public MarketMakerProviderResponse getMktMakerProvResponse() {
		return mktMakerProvResponse;
	}
	/**
	 * @param mktMakerProvResponse the mktMakerProvResponse to set
	 */
	public void setMktMakerProvResponse(
			MarketMakerProviderResponse mktMakerProvResponse) {
		this.mktMakerProvResponse = mktMakerProvResponse;
	}
	public Double getFirmPerfScore() {
		return firmPerfScore;
	}
	public void setFirmPerfScore(Double firmPerfScore) {
		this.firmPerfScore = firmPerfScore;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public Date getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
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
	//comparator to rank the providers/firms
	public static Comparator<RoutedProvider> getComparator(SortParameter... sortParameters) {
        return new RoutingComparator(sortParameters);
    }
	
	public enum SortParameter {
	    SCORE_DESCENDING, FIRM_SCORE_DESCENDING, DATE_ASCENDING, PROVIDER_NAME_ASCENDING,FIRM_NAME_ASCENDING
	}
	
	private static class RoutingComparator implements Comparator<RoutedProvider> {
        private SortParameter[] parameters;

        private RoutingComparator(SortParameter[] parameters) {
            this.parameters = parameters;
        }

        public int compare(RoutedProvider o1, RoutedProvider o2) {
            int comparison;
            for (SortParameter parameter : parameters) {
                switch (parameter) {
                    case SCORE_DESCENDING:
                    	if(null!= o1.getPerfScore() && null!= o2.getPerfScore()){
                        comparison = (o2.getPerfScore() < o1.getPerfScore())? -1 : ((o2.getPerfScore() > o1.getPerfScore())? 1 : 0);
                        if (comparison != 0) return comparison;
                    	}
                        break;
                    case FIRM_SCORE_DESCENDING:
                    	if(null!= o1.getFirmPerfScore() && null!= o2.getFirmPerfScore()){
                        comparison = (o2.getFirmPerfScore() < o1.getFirmPerfScore())? -1 : ((o2.getFirmPerfScore() > o1.getFirmPerfScore())? 1 : 0);
                        if (comparison != 0) return comparison;
                    	}
                        break;
                    case DATE_ASCENDING:
                    	if(null!= o1.getCompletedDate() && null!= o2.getCompletedDate()){
                        comparison = o1.getCompletedDate().compareTo(o2.getCompletedDate());
                        if (comparison != 0) return comparison;
                    	}
                        break;
                    case PROVIDER_NAME_ASCENDING:
                    	if(null!=o1.getFirstName() && null!= o1.getLastName() && null!= o2.getFirstName() && null!=o2.getLastName()){
                        comparison = o1.getFirstName().concat(o1.getLastName()).compareTo(o2.getFirstName().concat(o2.getLastName()));
                        if (comparison != 0) return comparison;
                    	}
                        break;
                    case FIRM_NAME_ASCENDING:
                    	if(null!=o1.getFirmName() && null!=o2.getFirmName()){
                        comparison = o1.getFirmName().compareTo(o2.getFirmName());
                        if (comparison != 0) return comparison;
                    	}
                        break;
                }
            }
            return 0;
        }
    }
}
