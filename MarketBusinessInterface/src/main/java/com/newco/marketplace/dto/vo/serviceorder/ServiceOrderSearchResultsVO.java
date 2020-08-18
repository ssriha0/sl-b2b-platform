package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.mail.search.DateTerm;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.buyer.BuyerSubstatusAssocVO;
import com.newco.marketplace.dto.vo.incident.AssociatedIncidentVO;
import com.newco.marketplace.dto.vo.price.PendingCancelHistoryVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.DateUtils;
import com.sears.os.vo.SerializableBaseVO;

public class ServiceOrderSearchResultsVO extends SerializableBaseVO implements Cloneable {
	
	private static final long serialVersionUID = -7481774936176777371L;
	private static final Logger logger = Logger.getLogger(ServiceOrderSearchResultsVO.class);
	
	private String providerAltPhoneNumber;
	private String providerPrimaryPhoneNumber;
	private String soId;
	private String groupId;
	private String orignalGroupId;
	private Integer soStatus;
	private String soStatusString;
	private Integer soSubStatus;
	private String soSubStatusString;
	private String soTitle;
	private String soTitleDesc;
	private Timestamp appointStartDate;
	private Timestamp appointEndDate;
	private String serviceTimeStart;
	private String serviceTimeEnd;
	private Timestamp createdDate;
	private Integer buyerID;
	private Integer acceptedResourceId;
	private Integer acceptedVendorId;
	private String street1;
	private String street2;
	private String city;
	private String stateCd;
	private String zip;
	private String zip4;
	private String country;
	private Double locnClassId;
	private Double spendLimit = 0.00;
	private Double spendLimitLabor = 0.00;
	private Double spendLimitParts = 0.00;
	private Double finalPartsPrice = 0.00;
	private Double finalLaborPrice = 0.00;
	private Double addonPrice = 0.00;
	private Double totalFinalPrice = 0.00;
	private Double cancelAmount= 0.00;
	private Double originalSpendLimitLabor;
	private Double originalSpendLimitParts;
	private String providerFirstName;
	private String providerLastName;
	private String buyerFirstName;
	private String buyerLastName;
	private String endCustomerFirstName;
	private String endCustomerLastName;
	private String buyerCompanyName;
	private Integer buyerRoleId;
	private Timestamp routedDate;
	private Integer providerCounts = 0;
	private Integer condCounts = 0;
	private Integer rejectedCounts = 0;
	private String parentGroupId;
	private String parentGroupTitle;
	private Timestamp groupCreatedDate;
	private String groupCreatedTime; // Derived from groupCreatedDate
	private Double groupSpendLimitLabor;
	private Double groupSpendLimitParts;
	private Double groupSpendLimit;
	private String sortSOandGroupID;
	private String serviceLocationTimezone;
	
	private String searchByType;	
	private String searchByValue;
	private Integer roleId;
	private String roleType;
	private String routedResourceId;
	private String routedResourceFirstName;
	private String routedResourceLastName;
	private Timestamp serviceDate1;
	private String serviceStartTime;
	
	private Integer providerResponseId;
	private List<String> soIds;
	private String sortColumnName;
	boolean serviceDateSort = false;
	private String sortOrder;
	private Integer startIndex;
	private Integer endIndex;
	
	private String claimedByResource;
	private Double vendorResLat;		//either accepted resource or routed resource
	private Double vendorResLong;		//either accepted resource or routed resource
	private Double soLat;
	private Double soLong;
	private Double distanceInMiles;
	private Integer vendorId;
	private Integer vendBuyerResourceId;
	private String resStreet1;
	private String resStreet2;
	private String resCity;
	private String resStateCd;
	private String resZip;
	
	private Integer serviceDateTypeId;
	/* Primary Skill Category ID from this service order*/
	private Integer primarySkillCategoryId;
	/* Posting fee for the service order*/
	private Double accessFee;
	private String paymentType;
	// adding end customer primary phone no
	private String endCustomerPrimaryPhoneNumber;
	private List<AssociatedIncidentVO> associatedIncidents;
	
	private boolean claimable;
	private Integer filterId;
	private Integer spnId;
	private String resourceId;
	private Integer lockEditInd = OrderConstants.SO_VIEW_MODE_FLAG;
	
	//specific information required for zero bid orders only
	private Double bidRangeMax;
	private Double bidRangeMin;
	private Timestamp bidEarliestStartDate;
	private Timestamp bidLatestEndDate;
	private Double currentBid;
	private Integer bidCount;
	private Timestamp bidExpirationDate;
	private String primarySkillCategory;
	private List<ServiceOrderTaskDetail> tasks;
	
	private List<String> selectedStateCodes;
	private List<String> selectedSkills;
	private List<Integer> selectedMarkets;
	private List<String> selectedAcceptanceTypes;
	private List<String> selectedPricingTypes;
	private List<String> selectedAssignmentTypes;
	private List<String> selectedPostingMethods;
	private List<ServiceOrderCustomRefVO> selectedCustomRefs;
	private List<String> selectedCheckNumbers;
	private List<String> selectedCustomerNames;
	private List<String> selectedPhones;
	private List<String> selectedProviderFirmIds;
	private List<String> selectedServiceOrderIds;
	private List<String> selectedServiceProIds;
	private List<String> selectedServiceProNames;
	private List<String> selectedZipCodes;
	private List<String> selectedMainCatIdList;
	private List<String> selectedCatAndSubCatIdList;
	private List<Date> startDateList;
	private List<Date> endDateList;		
	private List<BuyerSubstatusAssocVO> selectedStatuses;
	private List<ProviderResultVO> availableProviders;
	private List<PendingCancelHistoryVO> pendingCancel;
	
	
	private Timestamp completedDate;
	private Timestamp closedDate;
	private Timestamp acceptedDate;
	private Timestamp activatedDate;
	private String priceModel;
	private Integer pageNumber;
	private Integer pageSize;
	private Integer pageLimit;
	private boolean sealedBidInd;
	
	private boolean manageSOFlag;
	//for autoclose search
	private Integer autocloseAlone;
	private List<String> autocloseRuleList;
	private Double totalPermitPrice;
	
	private Double buyerPrice;
	private String buyerComments;
	private Date  buyerEntryDate;
	private Double providerPrice;
	private String providerComments;
	private Date  providerEntryDate;
	private String pendingCancelSubstatus;
	private PendingCancelDetailsVO buyerEntryDetails;
	private PendingCancelDetailsVO providerEntryDetails;
	private String priceType;
	
	private boolean checkGroupedOrders;
	
	//SL-19728 Non Funded Buyer
	private boolean nonFundedInd;
	
	private String invoicePartsInd;

	// Added for SL-17511
	private List<ProviderInvoicePartsVO> invoiceParts;

	//Added 15642
	private String assignmentType;
	
	//SL-18830 Added for firm details
    private String firmBusinessPhoneNumber;
	private String firmBusinessName;
	/*Currently we are setting providerprimaryPhone no 
	  as Firms Main Phone no from vendor_resource.This 
	  variable holds resources main phone no.
	  */
	private String providerMainPhoneNo;
	private String providerMobilePhoneNo;
	
	
	//R12_1
	//SL-20362
	private List<String> selectedPendingReschedule;
	//R12_1
	//SL-20554
	private List<String> selectedClosureMethod;
	
	private Double totalEstimationPrice = 0.00;
	
	
	public List<String> getSelectedPendingReschedule() {
		return selectedPendingReschedule;
	}
	public void setSelectedPendingReschedule(List<String> selectedPendingReschedule) {
		this.selectedPendingReschedule = selectedPendingReschedule;
	}
	
	public String getInvoicePartsInd() {
		return invoicePartsInd;
	}
	public void setInvoicePartsInd(String invoicePartsInd) {
		this.invoicePartsInd = invoicePartsInd;
	}
	public List<ProviderInvoicePartsVO> getInvoiceParts() {
		return invoiceParts;
	}
	public void setInvoiceParts(List<ProviderInvoicePartsVO> invoiceParts) {
		this.invoiceParts = invoiceParts;
	}
	public Double getTotalPermitPrice() {
		return totalPermitPrice;
	}
	public void setTotalPermitPrice(Double totalPermitPrice) {
		this.totalPermitPrice = totalPermitPrice;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageLimit() {
		return pageLimit;
	}
	public void setPageLimit(Integer pageLimit) {
		this.pageLimit = pageLimit;
	}
	public String getPrimarySkillCategory() {
		return primarySkillCategory;
	}
	public void setPrimarySkillCategory(String primarySkillCategory) {
		this.primarySkillCategory = primarySkillCategory;
	}
	public List<ServiceOrderTaskDetail> getTasks() {
		return tasks;
	}
	public void setTasks(List<ServiceOrderTaskDetail> tasks) {
		this.tasks = tasks;
	}
	public Double getBidRangeMax() {
		return bidRangeMax;
	}
	public void setBidRangeMax(Double bidRangeMax) {
		this.bidRangeMax = bidRangeMax;
	}
	public Double getBidRangeMin() {
		return bidRangeMin;
	}
	public void setBidRangeMin(Double bidRangeMin) {
		this.bidRangeMin = bidRangeMin;
	}
	public Double getCurrentBid() {
		return currentBid;
	}
	public void setCurrentBid(Double currentBid) {
		this.currentBid = currentBid;
	}
	public Integer getBidCount() {
		return bidCount;
	}
	public void setBidCount(Integer bidCount) {
		this.bidCount = bidCount;
	}
	public Timestamp getBidExpirationDate() {
		return bidExpirationDate;
	}
	public void setBidExpirationDate(Timestamp bidExpirationDate) {
		this.bidExpirationDate = bidExpirationDate;
	}
	public List<AssociatedIncidentVO> getAssociatedIncidents() {
		return associatedIncidents;
	}
	public void setAssociatedIncidents(
			List<AssociatedIncidentVO> associatedIncidents) {
		this.associatedIncidents = associatedIncidents;
	}
	public Integer getProviderResponseId() {
		return providerResponseId;
	}
	public void setProviderResponseId(Integer providerResponseId) {
		this.providerResponseId = providerResponseId;
	}
	public String getRoutedResourceId() {
		return routedResourceId;
	}
	public void setRoutedResourceId(String routedResourceId) {
		this.routedResourceId = routedResourceId;
	}
	public Integer getAcceptedVendorId() {
		return acceptedVendorId;
	}
	public void setAcceptedVendorId(Integer acceptedVendorId) {
		this.acceptedVendorId = acceptedVendorId;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public String getSearchByType() {
		return searchByType;
	}
	public void setSearchByType(String searchByType) {
		this.searchByType = searchByType;
	}
	public String getSearchByValue() {
		return searchByValue;
	}
	public void setSearchByValue(String searchByValue) {
		this.searchByValue = searchByValue;
	}
	public String getEndCustomerFirstName() {
		return endCustomerFirstName;
	}
	public void setEndCustomerFirstName(String endCustomerFirstName) {
		this.endCustomerFirstName = endCustomerFirstName;
	}
	public String getEndCustomerLastName() {
		return endCustomerLastName;
	}
	public void setEndCustomerLastName(String endCustomerLastName) {
		this.endCustomerLastName = endCustomerLastName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProviderFirstName() {
        return providerFirstName;
    }
    public void setProviderFirstName(String providerFirstName) {
        this.providerFirstName = providerFirstName;
    }
    public String getProviderLastName() {
        return providerLastName;
    }
    public void setProviderLastName(String providerLastName) {
        this.providerLastName = providerLastName;
    }
    public String getBuyerFirstName() {
        return buyerFirstName;
    }
    public void setBuyerFirstName(String buyerFirstName) {
        this.buyerFirstName = buyerFirstName;
    }
    public String getBuyerLastName() {
        return buyerLastName;
    }
    public void setBuyerLastName(String buyerLastName) {
        this.buyerLastName = buyerLastName;
    }
	public Timestamp getAppointEndDate() {
		return appointEndDate;
	}
	public void setAppointEndDate(Timestamp appointEndDate) {
		this.appointEndDate = appointEndDate;
	}
	public Timestamp getAppointStartDate() {
		return appointStartDate;
	}
	public void setAppointStartDate(Timestamp appointStartDate) {
		this.appointStartDate = appointStartDate;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getSoStatus() {
		return soStatus;
	}
	public void setSoStatus(Integer soStatus) {
		this.soStatus = soStatus;
	}
	public String getSoTitle() {
		return soTitle;
	}
	public void setSoTitle(String soTitle) {
		this.soTitle = soTitle;
	}
	public String getStateCd() {
		return stateCd;
	}
	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
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
	public String getZip4() {
		return zip4;
	}
	public void setZip4(String zip4) {
		this.zip4 = zip4;
	}
	public Integer getAcceptedResourceId() {
		return acceptedResourceId;
	}
	public void setAcceptedResourceId(Integer acceptedResourceId) {
		this.acceptedResourceId = acceptedResourceId;
	}
	public Integer getBuyerID() {
		return buyerID;
	}
	public void setBuyerID(Integer buyerID) {
		this.buyerID = buyerID;
	}
	public Double getSpendLimit() {
		return spendLimit;
	}
	public void setSpendLimit(Double spendLimit) {
		this.spendLimit = spendLimit;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getSoSubStatus() {
		return soSubStatus;
	}
	public void setSoSubStatus(Integer soSubStatus) {
		this.soSubStatus = soSubStatus;
	}
	public String getSoTitleDesc() {
		return soTitleDesc;
	}
	public void setSoTitleDesc(String soTitleDesc) {
		this.soTitleDesc = soTitleDesc;
	}
	public Timestamp getRoutedDate() {
		return routedDate;
	}
	public void setRoutedDate(Timestamp routedDate) {
		this.routedDate = routedDate;
	}
	public Integer getCondCounts() {
		return condCounts;
	}
	public void setCondCounts(Integer condCounts) {
		this.condCounts = condCounts;
	}
	public Integer getRejectedCounts() {
		return rejectedCounts;
	}
	public void setRejectedCounts(Integer rejectedCounts) {
		this.rejectedCounts = rejectedCounts;
	}
	public Integer getProviderCounts() {
		return providerCounts;
	}
	public void setProviderCounts(Integer providerCounts) {
		this.providerCounts = providerCounts;
	}
	public String getSoStatusString() {
		return soStatusString;
	}
	public void setSoStatusString(String soStatusString) {
		this.soStatusString = soStatusString;
	}
	public String getSoSubStatusString() {
		return soSubStatusString;
	}
	public void setSoSubStatusString(String soSubStatusString) {
		this.soSubStatusString = soSubStatusString;
	}
	public String getRoutedResourceFirstName() {
		return routedResourceFirstName;
	}
	public void setRoutedResourceFirstName(String routedResourceFirstName) {
		this.routedResourceFirstName = routedResourceFirstName;
	}
	public String getRoutedResourceLastName() {
		return routedResourceLastName;
	}
	public void setRoutedResourceLastName(String routedResourceLastName) {
		this.routedResourceLastName = routedResourceLastName;
	}
	public List<String> getSoIds() {
		return soIds;
	}
	public void setSoIds(List<String> soIds) {
		this.soIds = soIds;
	}
	public String getSortColumnName() {
		return sortColumnName;
	}
	public void setSortColumnName(String sortColumnName) {
		this.sortColumnName = sortColumnName;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Integer getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
	public Integer getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}
	public String getBuyerCompanyName() {
		return buyerCompanyName;
	}
	public void setBuyerCompanyName(String buyerCompanyName) {
		this.buyerCompanyName = buyerCompanyName;
	}
	@Override
	public String toString() {
		String lineSeparator = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();

		sb.append("ServiceOrderSearchResultsVO: ").append(lineSeparator)
		.append("soId: ").append(getSoId()).append(lineSeparator)
		.append("soStatus: ").append(getSoStatus()).append(lineSeparator)
		.append("soTitle: ").append(getSoTitle()).append(lineSeparator)
		.append("appointStartDate: ").append(getAppointStartDate()).append(lineSeparator)
		.append("appointEndDate: ").append(getAppointEndDate()).append(lineSeparator)
		.append("serviceTimeStart: ").append(getServiceTimeStart()).append(lineSeparator)
		.append("serviceTimeEnd: ").append(getServiceTimeEnd()).append(lineSeparator)
		;
		
		return sb.toString();
	}
	public Timestamp getServiceDate1() {
		return serviceDate1;
	}
	public void setServiceDate1(Timestamp serviceDate1) {
		this.serviceDate1 = serviceDate1;
	}
	public String getServiceStartTime() {
		return serviceStartTime;
	}
	public void setServiceStartTime(String serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}
	public String getServiceTimeEnd() {
		return serviceTimeEnd;
	}
	public void setServiceTimeEnd(String serviceTimeEnd) {
		this.serviceTimeEnd = serviceTimeEnd;
	}
	public String getServiceTimeStart() {
		return serviceTimeStart;
	}
	public void setServiceTimeStart(String serviceTimeStart) {
		this.serviceTimeStart = serviceTimeStart;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Double getSpendLimitLabor() {
		return spendLimitLabor;
	}
	public void setSpendLimitLabor(Double spendLimitLabor) {
		this.spendLimitLabor = spendLimitLabor;
	}
	public Double getSpendLimitParts() {
		return spendLimitParts;
	}
	public void setSpendLimitParts(Double spendLimitParts) {
		this.spendLimitParts = spendLimitParts;
	}
	public String getClaimedByResource() {
		return claimedByResource;
	}
	public void setClaimedByResource(String claimedByResource) {
		this.claimedByResource = claimedByResource;
	}
	public String getServiceLocationTimezone() {
		return serviceLocationTimezone;
	}
	public void setServiceLocationTimezone(String serviceLocationTimezone) {
		this.serviceLocationTimezone = serviceLocationTimezone;
	}
	public Double getDistanceInMiles() {
		return distanceInMiles;
	}
	public void setDistanceInMiles(Double distanceInMiles) {
		this.distanceInMiles = distanceInMiles;
	}
	public Double getVendorResLat() {
		return vendorResLat;
	}
	public void setVendorResLat(Double vendorResLat) {
		this.vendorResLat = vendorResLat;
	}
	public Double getVendorResLong() {
		return vendorResLong;
	}
	public void setVendorResLong(Double vendorResLong) {
		this.vendorResLong = vendorResLong;
	}
	public Double getSoLat() {
		return soLat;
	}
	public void setSoLat(Double soLat) {
		this.soLat = soLat;
	}
	public Double getSoLong() {
		return soLong;
	}
	public void setSoLong(Double soLong) {
		this.soLong = soLong;
	}
	public Double getFinalPartsPrice() {
		return finalPartsPrice;
	}
	public void setFinalPartsPrice(Double finalPartsPrice) {
		this.finalPartsPrice = finalPartsPrice;
	}
	public Double getFinalLaborPrice() {
		return finalLaborPrice;
	}
	public void setFinalLaborPrice(Double finalLaborPrice) {
		this.finalLaborPrice = finalLaborPrice;
	}
	public Double getTotalFinalPrice() {
		return totalFinalPrice;
	}
	public void setTotalFinalPrice(Double totalFinalPrice) {
		this.totalFinalPrice = totalFinalPrice;
	}
	public Integer getServiceDateTypeId() {
		return serviceDateTypeId;
	}
	public void setServiceDateTypeId(Integer serviceDateTypeId) {
		this.serviceDateTypeId = serviceDateTypeId;
	}
	public String getParentGroupId() {
		return parentGroupId;
	}
	public void setParentGroupId(String parentGroupId) {
		this.parentGroupId = parentGroupId;
	}
	public Integer getPrimarySkillCategoryId() {
		return primarySkillCategoryId;
	}
	public void setPrimarySkillCategoryId(Integer primarySkillCategoryId) {
		this.primarySkillCategoryId = primarySkillCategoryId;
	}
	public String getParentGroupTitle() {
		return parentGroupTitle;
	}
	public void setParentGroupTitle(String parentGroupTitle) {
		this.parentGroupTitle = parentGroupTitle;
	}
	public String getSortSOandGroupID() {
		return sortSOandGroupID;
	}
	public void setSortSOandGroupID(String sortSOandGroupID) {
		this.sortSOandGroupID = sortSOandGroupID;
	}
	public Double getGroupSpendLimitLabor() {
		return groupSpendLimitLabor;
	}
	public void setGroupSpendLimitLabor(Double groupSpendLimitLabor) {
		this.groupSpendLimitLabor = groupSpendLimitLabor;
	}
	public Double getGroupSpendLimitParts() {
		return groupSpendLimitParts;
	}
	public void setGroupSpendLimitParts(Double groupSpendLimitParts) {
		this.groupSpendLimitParts = groupSpendLimitParts;
	}
	public Double getGroupSpendLimit() {
		return groupSpendLimit;
	}
	public void setGroupSpendLimit(Double groupSpendLimit) {
		this.groupSpendLimit = groupSpendLimit;
	}
	
	@Override
	public ServiceOrderSearchResultsVO clone() {
		try {
			return (ServiceOrderSearchResultsVO)super.clone();
		} catch (CloneNotSupportedException cnsEx) {
			logger.error("Unexpected CloneNotSupportedException in ServiceOrderSearchResultsVO.clone()", cnsEx);
			return null;
		}
	}
	/**
	 * @return the accessFee
	 */
	public Double getAccessFee() {
		return accessFee;
	}
	/**
	 * @param accessFee the accessFee to set
	 */
	public void setAccessFee(Double accessFee) {
		this.accessFee = accessFee;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Double getOriginalSpendLimitLabor() {
		return originalSpendLimitLabor;
	}
	public void setOriginalSpendLimitLabor(Double originalSpendLimitLabor) {
		this.originalSpendLimitLabor = originalSpendLimitLabor;
	}
	public Double getOriginalSpendLimitParts() {
		return originalSpendLimitParts;
	}
	public void setOriginalSpendLimitParts(Double originalSpendLimitParts) {
		this.originalSpendLimitParts = originalSpendLimitParts;
	}
	public Timestamp getGroupCreatedDate() {
		return groupCreatedDate;
	}
	public void setGroupCreatedDate(Timestamp groupCreatedDate) {
		this.groupCreatedDate = groupCreatedDate;
	}
	public String getGroupCreatedTime() {
		if (groupCreatedDate == null) {
			groupCreatedTime = null;
		} else {
			groupCreatedTime = DateUtils.getFormatedDate(groupCreatedDate, "hh:mm a");
		}
		return groupCreatedTime;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public Double getAddonPrice() {
		return addonPrice;
	}
	public void setAddonPrice(Double addonPrice) {
		this.addonPrice = addonPrice;
	}
	public String getEndCustomerPrimaryPhoneNumber() {
		return endCustomerPrimaryPhoneNumber;
	}
	public void setEndCustomerPrimaryPhoneNumber(
			String endCustomerPrimaryPhoneNumber) {
		this.endCustomerPrimaryPhoneNumber = endCustomerPrimaryPhoneNumber;
	}
	public String getProviderPrimaryPhoneNumber() {
		return providerPrimaryPhoneNumber;
	}
	public void setProviderPrimaryPhoneNumber(String providerPrimaryPhoneNumber) {
		this.providerPrimaryPhoneNumber = providerPrimaryPhoneNumber;
	}
	public String getProviderAltPhoneNumber() {
		return providerAltPhoneNumber;
	}
	public void setProviderAltPhoneNumber(String providerAltPhoneNumber) {
		this.providerAltPhoneNumber = providerAltPhoneNumber;
	}
	public boolean isClaimable() {
		return claimable;
	}
	public void setClaimable(boolean claimable) {
		this.claimable = claimable;
	}
	public Integer getFilterId() {
		return filterId;
	}
	public void setFilterId(Integer filterId) {
		this.filterId = filterId;
	}
	public Integer getVendBuyerResourceId() {
		return vendBuyerResourceId;
	}
	public void setVendBuyerResourceId(Integer vendBuyerResourceId) {
		this.vendBuyerResourceId = vendBuyerResourceId;
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
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getLockEditInd() {
		return lockEditInd;
	}
	public void setLockEditInd(Integer lockEditInd) {
		this.lockEditInd = lockEditInd;
	}
	public String getResStreet1() {
		return resStreet1;
	}
	public void setResStreet1(String resStreet1) {
		this.resStreet1 = resStreet1;
	}
	public String getResStreet2() {
		return resStreet2;
	}
	public void setResStreet2(String resStreet2) {
		this.resStreet2 = resStreet2;
	}
	public String getResCity() {
		return resCity;
	}
	public void setResCity(String resCity) {
		this.resCity = resCity;
	}
	public String getResStateCd() {
		return resStateCd;
	}
	public void setResStateCd(String resStateCd) {
		this.resStateCd = resStateCd;
	}
	public String getResZip() {
		return resZip;
	}
	public void setResZip(String resZip) {
		this.resZip = resZip;
	}
	public String getOrignalGroupId() {
		return orignalGroupId;
	}
	public void setOrignalGroupId(String orignalGroupId) {
		this.orignalGroupId = orignalGroupId;
	}
	public Integer getBuyerRoleId()
	{
		return buyerRoleId;
	}
	public void setBuyerRoleId(Integer buyerRoleId)
	{
		this.buyerRoleId = buyerRoleId;
	}
	public List<String> getSelectedStateCodes() {
		return selectedStateCodes;
	}
	public void setSelectedStateCodes(List<String> selectedStateCodes) {
		this.selectedStateCodes = selectedStateCodes;
	}
	public List<String> getSelectedSkills() {
		return selectedSkills;
	}
	public void setSelectedSkills(List<String> selectedSkills) {
		this.selectedSkills = selectedSkills;
	}
	public List<Integer> getSelectedMarkets() {
		return selectedMarkets;
	}
	public void setSelectedMarkets(List<Integer> selectedMarkets) {
		this.selectedMarkets = selectedMarkets;
	}
	public List<ServiceOrderCustomRefVO> getSelectedCustomRefs() {
		return selectedCustomRefs;
	}
	public void setSelectedCustomRefs(
			List<ServiceOrderCustomRefVO> selectedCustomRefs) {
		this.selectedCustomRefs = selectedCustomRefs;
	}
	public List<String> getSelectedCheckNumbers() {
		return selectedCheckNumbers;
	}
	public void setSelectedCheckNumbers(List<String> selectedCheckNumbers) {
		this.selectedCheckNumbers = selectedCheckNumbers;
	}
	public List<String> getSelectedCustomerNames() {
		return selectedCustomerNames;
	}
	public void setSelectedCustomerNames(List<String> selectedCustomerNames) {
		this.selectedCustomerNames = selectedCustomerNames;
	}
	public List<String> getSelectedPhones() {
		return selectedPhones;
	}
	public void setSelectedPhones(List<String> selectedPhones) {
		this.selectedPhones = selectedPhones;
	}
	public List<String> getSelectedProviderFirmIds() {
		return selectedProviderFirmIds;
	}
	public void setSelectedProviderFirmIds(List<String> selectedProviderFirmIds) {
		this.selectedProviderFirmIds = selectedProviderFirmIds;
	}
	public List<String> getSelectedServiceOrderIds() {
		return selectedServiceOrderIds;
	}
	public void setSelectedServiceOrderIds(List<String> selectedServiceOrderIds) {
		this.selectedServiceOrderIds = selectedServiceOrderIds;
	}
	public List<String> getSelectedServiceProIds() {
		return selectedServiceProIds;
	}
	public void setSelectedServiceProIds(List<String> selectedServiceProIds) {
		this.selectedServiceProIds = selectedServiceProIds;
	}
	public List<String> getSelectedServiceProNames() {
		return selectedServiceProNames;
	}
	public void setSelectedServiceProNames(List<String> selectedServiceProNames) {
		this.selectedServiceProNames = selectedServiceProNames;
	}
	public List<String> getSelectedZipCodes() {
		return selectedZipCodes;
	}
	public void setSelectedZipCodes(List<String> selectedZipCodes) {
		this.selectedZipCodes = selectedZipCodes;
	}
	public List<String> getSelectedMainCatIdList() {
		return selectedMainCatIdList;
	}
	public void setSelectedMainCatIdList(List<String> selectedMainCatIdList) {
		this.selectedMainCatIdList = selectedMainCatIdList;
	}
	public List<String> getSelectedCatAndSubCatIdList() {
		return selectedCatAndSubCatIdList;
	}
	public void setSelectedCatAndSubCatIdList(
			List<String> selectedCatAndSubCatIdList) {
		this.selectedCatAndSubCatIdList = selectedCatAndSubCatIdList;
	}
	public List<Date> getStartDateList() {
		return startDateList;
	}
	public void setStartDateList(List<Date> startDateList) {
		this.startDateList = startDateList;
	}
	public List<Date> getEndDateList() {
		return endDateList;
	}
	public void setEndDateList(List<Date> endDateList) {
		this.endDateList = endDateList;
	}
	public List<BuyerSubstatusAssocVO> getSelectedStatuses() {
		return selectedStatuses;
	}
	public void setSelectedStatuses(List<BuyerSubstatusAssocVO> selectedStatuses) {
		this.selectedStatuses = selectedStatuses;
	}
	public List<ProviderResultVO> getAvailableProviders() {
		return availableProviders;
	}
	public void setAvailableProviders(List<ProviderResultVO> availableProviders) {
		this.availableProviders = availableProviders;
	}
	public String getPriceModel() {
		return priceModel;
	}
	public void setPriceModel(String priceModel) {
		this.priceModel = priceModel;
	}
	public Timestamp getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(Timestamp completedDate) {
		this.completedDate = completedDate;
	}
	public Timestamp getClosedDate() {
		return closedDate;
	}
	public void setClosedDate(Timestamp closedDate) {
		this.closedDate = closedDate;
	}
	public Timestamp getAcceptedDate() {
		return acceptedDate;
	}
	public void setAcceptedDate(Timestamp acceptedDate) {
		this.acceptedDate = acceptedDate;
	}
	public Timestamp getActivatedDate() {
		return activatedDate;
	}
	public void setActivatedDate(Timestamp activatedDate) {
		this.activatedDate = activatedDate;
	}
	public void setGroupCreatedTime(String groupCreatedTime) {
		this.groupCreatedTime = groupCreatedTime;
	}
	public Double getLocnClassId() {
		return locnClassId;
	}
	public void setLocnClassId(Double locnClassId) {
		this.locnClassId = locnClassId;
	}
	public Timestamp getBidEarliestStartDate()
	{
		return bidEarliestStartDate;
	}
	public void setBidEarliestStartDate(Timestamp bidEarliestStartDate)
	{
		this.bidEarliestStartDate = bidEarliestStartDate;
	}
	public Timestamp getBidLatestEndDate()
	{
		return bidLatestEndDate;
	}
	public void setBidLatestEndDate(Timestamp bidLatestEndDate)
	{
		this.bidLatestEndDate = bidLatestEndDate;
	}
	public boolean isSealedBidInd() {
		return sealedBidInd;
}
	public void setSealedBidInd(boolean sealedBidInd) {
		this.sealedBidInd = sealedBidInd;
	}
	public boolean isServiceDateSort() {
		return serviceDateSort;
	}
	public void setServiceDateSort(boolean serviceDateSort) {
		this.serviceDateSort = serviceDateSort;
	}
	public boolean isManageSOFlag() {
		return manageSOFlag;
	}
	public void setManageSOFlag(boolean manageSOFlag) {
		this.manageSOFlag = manageSOFlag;
	}
	public Integer getAutocloseAlone() {
		return autocloseAlone;
	}
	public void setAutocloseAlone(Integer autocloseAlone) {
		this.autocloseAlone = autocloseAlone;
	}
	public List<String> getAutocloseRuleList() {
		return autocloseRuleList;
	}
	public void setAutocloseRuleList(List<String> autocloseRuleList) {
		this.autocloseRuleList = autocloseRuleList;
	}
	public void setCheckGroupedOrders(boolean checkGroupedOrders) {
		this.checkGroupedOrders = checkGroupedOrders;
	}
	public boolean isCheckGroupedOrders() {
		return checkGroupedOrders;
	}
	public List<PendingCancelHistoryVO> getPendingCancel() {
		return pendingCancel;
		}
	
	public void setPendingCancel(List<PendingCancelHistoryVO> pendingCancel) {
		this.pendingCancel = pendingCancel;
	}
	public Double getCancelAmount() {
		return cancelAmount;
	}
	public void setCancelAmount(Double cancelAmount) {
		this.cancelAmount = cancelAmount;
	}
	public Double getBuyerPrice() {
		return buyerPrice;
	}
	public void setBuyerPrice(Double buyerPrice) {
		this.buyerPrice = buyerPrice;
	}
	public String getBuyerComments() {
		return buyerComments;
	}
	public void setBuyerComments(String buyerComments) {
		this.buyerComments = buyerComments;
	}
	public Date getBuyerEntryDate() {
		return buyerEntryDate;
	}
	public void setBuyerEntryDate(Date buyerEntryDate) {
		this.buyerEntryDate = buyerEntryDate;
	}
	public Double getProviderPrice() {
		return providerPrice;
	}
	public void setProviderPrice(Double providerPrice) {
		this.providerPrice = providerPrice;
	}
	public String getProviderComments() {
		return providerComments;
	}
	public void setProviderComments(String providerComments) {
		this.providerComments = providerComments;
	}
	public Date getProviderEntryDate() {
		return providerEntryDate;
	}
	public void setProviderEntryDate(Date providerEntryDate) {
		this.providerEntryDate = providerEntryDate;
	}
	public String getPendingCancelSubstatus() {
		return pendingCancelSubstatus;
	}
	public void setPendingCancelSubstatus(String pendingCancelSubstatus) {
		this.pendingCancelSubstatus = pendingCancelSubstatus;
	}
	public PendingCancelDetailsVO getBuyerEntryDetails() {
		return buyerEntryDetails;
	}
	public void setBuyerEntryDetails(PendingCancelDetailsVO buyerEntryDetails) {
		this.buyerEntryDetails = buyerEntryDetails;
	}
	public PendingCancelDetailsVO getProviderEntryDetails() {
		return providerEntryDetails;
	}
	public void setProviderEntryDetails(PendingCancelDetailsVO providerEntryDetails) {
		this.providerEntryDetails = providerEntryDetails;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;	
	}
	public List<String> getSelectedAcceptanceTypes() {
		return selectedAcceptanceTypes;
	}
	public void setSelectedAcceptanceTypes(List<String> selectedAcceptanceTypes) {
		this.selectedAcceptanceTypes = selectedAcceptanceTypes;
	}
	public List<String> getSelectedPricingTypes() {
		return selectedPricingTypes;
	}
	public void setSelectedPricingTypes(List<String> selectedPricingTypes) {
		this.selectedPricingTypes = selectedPricingTypes;
	}
	public List<String> getSelectedAssignmentTypes() {
		return selectedAssignmentTypes;
	}
	public void setSelectedAssignmentTypes(List<String> selectedAssignmentTypes) {
		this.selectedAssignmentTypes = selectedAssignmentTypes;
	}
	public List<String> getSelectedPostingMethods() {
		return selectedPostingMethods;
	}
	public void setSelectedPostingMethods(List<String> selectedPostingMethods) {
		this.selectedPostingMethods = selectedPostingMethods;
	}
	public String getAssignmentType() {
		return assignmentType;
	}
	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}
	//SL-18830 Added for firm details
	public String getFirmBusinessPhoneNumber() {
		return firmBusinessPhoneNumber;
	}
	public void setFirmBusinessPhoneNumber(String firmBusinessPhoneNumber) {
		this.firmBusinessPhoneNumber = firmBusinessPhoneNumber;
	}
	public String getFirmBusinessName() {
		return firmBusinessName;
	}
	public void setFirmBusinessName(String firmBusinessName) {
		this.firmBusinessName = firmBusinessName;
	}
	public String getProviderMainPhoneNo() {
		return providerMainPhoneNo;
	}
	public void setProviderMainPhoneNo(String providerMainPhoneNo) {
		this.providerMainPhoneNo = providerMainPhoneNo;
	}
	public String getProviderMobilePhoneNo() {
		return providerMobilePhoneNo;
	}
	public void setProviderMobilePhoneNo(String providerMobilePhoneNo) {
		this.providerMobilePhoneNo = providerMobilePhoneNo;
	}
	public boolean isNonFundedInd() {
		return nonFundedInd;
	}
	public void setNonFundedInd(boolean nonFundedInd) {
		this.nonFundedInd = nonFundedInd;
	}
	public List<String> getSelectedClosureMethod() {
		return selectedClosureMethod;
	}
	public void setSelectedClosureMethod(List<String> selectedClosureMethod) {
		this.selectedClosureMethod = selectedClosureMethod;
	}
	
	public Double getTotalEstimationPrice() {
		return totalEstimationPrice;
	}
	public void setTotalEstimationPrice(Double totalEstimationPrice) {
		this.totalEstimationPrice = totalEstimationPrice;
	}
	
	

}
