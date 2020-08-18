/**
 * 
 */
package com.newco.marketplace.vo.ordermanagement.so;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.InvoicePartVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.RoutedProvidersVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getCallInfo.Task;
import com.sears.os.vo.SerializableBaseVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


@XStreamAlias("soDetail")
public class OMServiceOrder extends SerializableBaseVO implements Cloneable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XStreamAlias("soId") 
	private String soId;

	@XStreamAlias("groupInd") 
	private Boolean groupInd;
	
	@OptionalParam
	@XStreamAlias("childSOList") 
	private ChildOrderListType childOrderList;
	
	@OptionalParam
	@XStreamAlias("childBidSoList") 
	private ChildBidOrderListType childBidSoList;
	
	@OptionalParam
	@XStreamAlias("parentGroupId") 
	private String parentGroupId;

	@OptionalParam
	@XStreamAlias("parentGroupTitle") 
	private String parentGroupTitle;

	@OptionalParam
	@XStreamAlias("groupCreatedDate") 
	private String groupCreatedDate;

	@OptionalParam
	@XStreamAlias("routedDate") 
	private String routedDate;

	@OptionalParam
	@XStreamAlias("groupSpendLimitLabor") 
	private String groupSpendLimitLabor;

	@OptionalParam
	@XStreamAlias("groupSpendLimitParts") 
	private String groupSpendLimitParts;
	
	@OptionalParam
	@XStreamAlias("bidMinSpendLimit") 
	private String bidMinSpendLimit;
	
	@OptionalParam
	@XStreamAlias("bidMaxSpendLimit") 
	private String bidMaxSpendLimit;


	@OptionalParam
	@XStreamAlias("groupSpendLimit") 
	private String groupSpendLimit;

	@OptionalParam
	@XStreamAlias("buyerID") 
	private String buyerID;

	@OptionalParam
	@XStreamAlias("priceModel") 
	private String priceModel;

	@OptionalParam
	@XStreamAlias("buyerCompanyName") 
	private String buyerCompanyName;

	@OptionalParam
	@XStreamAlias("buyerRoleId") 
	private String buyerRoleId;

	@OptionalParam
	@XStreamAlias("acceptedResourceId") 
	private String acceptedResourceId;

	@XStreamAlias("soTitle") 
	private String soTitle;
	
	@XStreamAlias("soTitleDesc") 
	private String soTitleDesc;
	
	@XStreamOmitField
	private Timestamp serviceStartDate;
	
	@XStreamOmitField
	private Timestamp serviceEndDate;
	
	@OptionalParam
	@XStreamAlias("reSchedStartDate") 
	private Timestamp reSchedStartDate;

	public Timestamp getReSchedStartDate() {
		return reSchedStartDate;
	}

	public void setReSchedStartDate(Timestamp reSchedStartDate) {
		this.reSchedStartDate = reSchedStartDate;
	}

	@OptionalParam
	@XStreamAlias("appointStartDate") 
	private String appointStartDate;

	@OptionalParam
	@XStreamAlias("appointEndDate") 
	private String appointEndDate;

	@OptionalParam
	@XStreamAlias("serviceTimeStart") 
	private String serviceTimeStart;

	@OptionalParam
	@XStreamAlias("serviceTimeEnd") 
	private String serviceTimeEnd;

	@OptionalParam
	@XStreamAlias("createdDate") 
	private String createdDate;

	@OptionalParam
	@XStreamAlias("spendLimit") 
	private String spendLimit;

	@OptionalParam
	@XStreamAlias("spendLimitParts") 
	private String spendLimitParts;

	@OptionalParam
	@XStreamAlias("finalPartsPrice") 
	private String finalPartsPrice;

	@OptionalParam
	@XStreamAlias("finalLaborPrice") 
	private String finalLaborPrice;

	@OptionalParam
	@XStreamAlias("soStatus") 
	private String soStatus;

	@OptionalParam
	@XStreamAlias("serviceLocationTimezone") 
	private String serviceLocationTimezone;

	@OptionalParam
	@XStreamAlias("soStatusString") 
	private String soStatusString;

	@OptionalParam
	@XStreamAlias("soSubStatus") 
	private String soSubStatus;

	@OptionalParam
	@XStreamAlias("soSubStatusString") 
	private String soSubStatusString;
	@OptionalParam
	@XStreamAlias("assignmentType") 
	private String assignmentType;

	@OptionalParam
	@XStreamAlias("street1") 
	private String street1;

	@OptionalParam
	@XStreamAlias("street2") 
	private String street2;

	@OptionalParam
	@XStreamAlias("city") 
	private String city;

	@OptionalParam
	@XStreamAlias("state") 
	private String state;

	@XStreamAlias("zip") 
	private String zip;
	
	@OptionalParam
	@XStreamAlias("dlsFlag") 
	private String dlsFlag;

	@OptionalParam
	@XStreamAlias("endCustomerPrimaryPhoneNumber") 
	private String endCustomerPrimaryPhoneNumber;	

	@OptionalParam
	@XStreamAlias("endCustomerAlternatePhoneNumber") 
	private String endCustomerAlternatePhoneNumber;

	@OptionalParam
	@XStreamAlias("endCustomerFirstName") 
	private String endCustomerFirstName;

	@OptionalParam
	@XStreamAlias("endCustomerLastName") 
	private String endCustomerLastName;

	@OptionalParam
	@XStreamAlias("resourceId") 
	private String resourceId;

	@OptionalParam
	@XStreamAlias("routedResourceId") 
	private String routedResourceId;

	@OptionalParam
	@XStreamAlias("providerResponseId") 
	private String providerResponseId;

	@OptionalParam
	@XStreamAlias("routedResourceFirstName") 
	private String routedResourceFirstName;

	@OptionalParam
	@XStreamAlias("routedResourceLastName") 
	private String routedResourceLastName;

	@OptionalParam
	@XStreamAlias("sealedBidInd") 
	private String sealedBidInd;
	
	@OptionalParam
	@XStreamAlias("soAttribute") 
	private String soAttribute;

	@OptionalParam
	@XStreamAlias("acceptanceMethod") 
	private String acceptanceMethod;

	@OptionalParam
	@XStreamAlias("vendorId") 
	private String vendorId;

	@OptionalParam
	@XStreamAlias("followUpFlag") 
	private String followUpFlag;

	@OptionalParam
	@XStreamImplicit(itemFieldName="routedProviders")
	private List<RoutedProvidersVO> routedProviders;

	@OptionalParam
	@XStreamAlias("soLocnNotes") 
	private String soLocnNotes;

	@OptionalParam
	@XStreamImplicit(itemFieldName="scope")
	private List<Task> scope;
	
	/*@OptionalParam
	@XStreamImplicit(itemFieldName="parts")
	private List<InvoicePartVO> parts;*/

	@XStreamAlias("actions")
	private List<String> actions ;

	@XStreamAlias("scheduleStatus") 
	private String scheduleStatus;
	
	@XStreamOmitField
	private String sortOrder;
	
	@OptionalParam
	@XStreamAlias("partStreet1") 
	private String partStreet1;
	
	@OptionalParam
	@XStreamAlias("partStreet2") 
	private String partStreet2;
	
	@OptionalParam
	@XStreamAlias("partCity") 
	private String partCity;
	
	@OptionalParam
	@XStreamAlias("partState") 
	private String partState;
	
	@OptionalParam
	@XStreamAlias("partZip") 
	private String partZip;

	@OptionalParam
	@XStreamAlias("availabilityDate") 
	private String availabilityDate;
	
	
	@OptionalParam
	@XStreamAlias("problemReportedBy") 
	private String problemReportedBy;
	
	@OptionalParam
	@XStreamAlias("problemType") 
	private String problemType;
	
	@OptionalParam
	@XStreamAlias("problemReportedDate") 
	private Date problemReportedDate;
	
	@OptionalParam
	@XStreamAlias("preCallAttemptedDate") 
	private Date preCallAttemptedDate;
	
	@OptionalParam
	@XStreamAlias("jobDoneOn") 
	private Date jobDoneOn;
	
	@OptionalParam
	@XStreamAlias("lastTripOn") 
	private Date lastTripOn;
	
	@OptionalParam
	@XStreamAlias("rescheduleRole") 
	private Integer rescheduleRole;
    
	@OptionalParam
	@XStreamAlias("primaryExtension")
	private String primaryExtension;
	    
	@OptionalParam
	@XStreamAlias("alternateExtension")
	private String alternateExtension;
	
	@XStreamOmitField
	private Timestamp resheduleStartDate;
	
	@XStreamOmitField
	private Timestamp resheduleEndDate;
	
	
	@OptionalParam
	@XStreamAlias("resheduleStartDate")
	private String resheduleStartDateString;
	
	@OptionalParam
	@XStreamAlias("resheduleEndDate")
	private String resheduleEndDateString;
	
	@OptionalParam
	@XStreamAlias("resheduleStartTime")
	private String resheduleStartTime;
	
	@OptionalParam
	@XStreamAlias("resheduleEndTime")
	private String resheduleEndTime;
	
	//SL-21465
	private Boolean isEstimationRequest;	
	private Integer estimationId;
	//private String estimationStatus;
	
	
	@Override
	public OMServiceOrder clone() {
		try {
			return (OMServiceOrder)super.clone();
		} catch (CloneNotSupportedException cnsEx) {
			//logger.error("Unexpected CloneNotSupportedException in ServiceOrderSearchResultsVO.clone()", cnsEx);
			return null;
		}
	}

	public Boolean getGroupInd() {
		return groupInd;
	}

	public void setGroupInd(Boolean groupInd) {
		this.groupInd = groupInd;
	}

	public ChildOrderListType getChildOrderList() {
		return childOrderList;
	}

	public void setChildOrderList(ChildOrderListType childOrderList) {
		this.childOrderList = childOrderList;
	}

	
	public ChildBidOrderListType getChildBidSoList() {
		return childBidSoList;
	}

	public void setChildBidSoList(ChildBidOrderListType childBidSoList) {
		this.childBidSoList = childBidSoList;
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
	public String getServiceLocationTimezone() {
		return serviceLocationTimezone;
	}
	public void setServiceLocationTimezone(String serviceLocationTimezone) {
		this.serviceLocationTimezone = serviceLocationTimezone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getSpendLimitParts() {
		return spendLimitParts;
	}
	public void setSpendLimitParts(String spendLimitParts) {
		this.spendLimitParts = spendLimitParts;
	}

	public String getAcceptedResourceId() {
		return acceptedResourceId;
	}
	public void setAcceptedResourceId(String acceptedResourceId) {
		this.acceptedResourceId = acceptedResourceId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}

	public List<RoutedProvidersVO> getRoutedProviders() {
		return routedProviders;
	}
	public void setRoutedProviders(List<RoutedProvidersVO> routedProviders) {
		this.routedProviders = routedProviders;
	}
	public String getSoTitle() {
		return soTitle;
	}
	public void setSoTitle(String soTitle) {
		this.soTitle = soTitle;
	}
	public String getSoTitleDesc() {
		return soTitleDesc;
	}

	public void setSoTitleDesc(String soTitleDesc) {
		this.soTitleDesc = soTitleDesc;
	}

	public String getAppointStartDate() {
		return appointStartDate;
	}
	public void setAppointStartDate(String appointStartDate) {
		this.appointStartDate = appointStartDate;
	}
	public String getAppointEndDate() {
		return appointEndDate;
	}
	public void setAppointEndDate(String appointEndDate) {
		this.appointEndDate = appointEndDate;
	}
	public String getFinalPartsPrice() {
		return finalPartsPrice;
	}
	public void setFinalPartsPrice(String finalPartsPrice) {
		this.finalPartsPrice = finalPartsPrice;
	}
	public String getFinalLaborPrice() {
		return finalLaborPrice;
	}
	public void setFinalLaborPrice(String finalLaborPrice) {
		this.finalLaborPrice = finalLaborPrice;
	}
	public String getSoStatusString() {
		return soStatusString;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
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
	public String getEndCustomerPrimaryPhoneNumber() {
		return endCustomerPrimaryPhoneNumber;
	}
	public void setEndCustomerPrimaryPhoneNumber(
			String endCustomerPrimaryPhoneNumber) {
		this.endCustomerPrimaryPhoneNumber = endCustomerPrimaryPhoneNumber;
	}
	public String getEndCustomerAlternatePhoneNumber() {
		return endCustomerAlternatePhoneNumber;
	}
	public void setEndCustomerAlternatePhoneNumber(
			String endCustomerAlternatePhoneNumber) {
		this.endCustomerAlternatePhoneNumber = endCustomerAlternatePhoneNumber;
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
	public String getParentGroupId() {
		return parentGroupId;
	}
	public void setParentGroupId(String parentGroupId) {
		this.parentGroupId = parentGroupId;
	}
	public String getParentGroupTitle() {
		return parentGroupTitle;
	}
	public void setParentGroupTitle(String parentGroupTitle) {
		this.parentGroupTitle = parentGroupTitle;
	}
	public String getGroupCreatedDate() {
		return groupCreatedDate;
	}
	public void setGroupCreatedDate(String groupCreatedDate) {
		this.groupCreatedDate = groupCreatedDate;
	}
	public String getRoutedDate() {
		return routedDate;
	}
	public void setRoutedDate(String routedDate) {
		this.routedDate = routedDate;
	}
	public String getGroupSpendLimitLabor() {
		return groupSpendLimitLabor;
	}
	public void setGroupSpendLimitLabor(String groupSpendLimitLabor) {
		this.groupSpendLimitLabor = groupSpendLimitLabor;
	}
	public String getGroupSpendLimitParts() {
		return groupSpendLimitParts;
	}
	public void setGroupSpendLimitParts(String groupSpendLimitParts) {
		this.groupSpendLimitParts = groupSpendLimitParts;
	}
	
	

	public String getBidMinSpendLimit() {
		return bidMinSpendLimit;
	}

	public void setBidMinSpendLimit(String bidMinSpendLimit) {
		this.bidMinSpendLimit = bidMinSpendLimit;
	}

	public String getBidMaxSpendLimit() {
		return bidMaxSpendLimit;
	}

	public void setBidMaxSpendLimit(String bidMaxSpendLimit) {
		this.bidMaxSpendLimit = bidMaxSpendLimit;
	}

	public String getGroupSpendLimit() {
		return groupSpendLimit;
	}
	public void setGroupSpendLimit(String groupSpendLimit) {
		this.groupSpendLimit = groupSpendLimit;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getBuyerCompanyName() {
		return buyerCompanyName;
	}
	public void setBuyerCompanyName(String buyerCompanyName) {
		this.buyerCompanyName = buyerCompanyName;
	}
	public String getBuyerRoleId() {
		return buyerRoleId;
	}
	public void setBuyerRoleId(String buyerRoleId) {
		this.buyerRoleId = buyerRoleId;
	}
	public String getSoStatus() {
		return soStatus;
	}
	public void setSoStatus(String soStatus) {
		this.soStatus = soStatus;
	}
	public String getBuyerID() {
		return buyerID;
	}
	public void setBuyerID(String buyerID) {
		this.buyerID = buyerID;
	}
	public String getSoSubStatus() {
		return soSubStatus;
	}
	public void setSoSubStatus(String soSubStatus) {
		this.soSubStatus = soSubStatus;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getRoutedResourceId() {
		return routedResourceId;
	}
	public void setRoutedResourceId(String routedResourceId) {
		this.routedResourceId = routedResourceId;
	}
	public String getProviderResponseId() {
		return providerResponseId;
	}
	public void setProviderResponseId(String providerResponseId) {
		this.providerResponseId = providerResponseId;
	}
	public String getSealedBidInd() {
		return sealedBidInd;
	}
	public void setSealedBidInd(String sealedBidInd) {
		this.sealedBidInd = sealedBidInd;
	}
	public String getSoAttribute() {
		return soAttribute;
	}
	public void setSoAttribute(String soAttribute) {
		this.soAttribute = soAttribute;
	}
	public String getFollowUpFlag() {
		return followUpFlag;
	}
	public void setFollowUpFlag(String followUpFlag) {
		this.followUpFlag = followUpFlag;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getSpendLimit() {
		return spendLimit;
	}
	public void setSpendLimit(String spendLimit) {
		this.spendLimit = spendLimit;
	}
	public List<String> getActions() {
		return actions;
	}
	public void setActions(List<String> actions) {
		this.actions = actions;
	}
	public String getScheduleStatus() {
		return scheduleStatus;
	}
	public void setScheduleStatus(String scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}
	public String getSoLocnNotes() {
		return soLocnNotes;
	}
	public void setSoLocnNotes(String soLocnNotes) {
		this.soLocnNotes = soLocnNotes;
	}
	public List<Task> getScope() {
		return scope;
	}
	public void setScope(List<Task> scope) {
		this.scope = scope;
	}
	
	
	/*public List<InvoicePartVO> getParts() {
		return parts;
	}

	public void setParts(List<InvoicePartVO> parts) {
		this.parts = parts;
	}
*/
	public String getAcceptanceMethod() {
		return acceptanceMethod;
	}
	public void setAcceptanceMethod(String acceptanceMethod) {
		this.acceptanceMethod = acceptanceMethod;
	}
	public String getPriceModel() {
		return priceModel;
	}
	public void setPriceModel(String priceModel) {
		this.priceModel = priceModel;
	}
	public String getAssignmentType() {
		return assignmentType;
	}
	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}
	public String getPartStreet1() {
		return partStreet1;
	}
	public void setPartStreet1(String partStreet1) {
		this.partStreet1 = partStreet1;
	}
	public String getPartStreet2() {
		return partStreet2;
	}
	public void setPartStreet2(String partStreet2) {
		this.partStreet2 = partStreet2;
	}
	public String getProblemReportedBy() {
		return problemReportedBy;
	}
	public void setProblemReportedBy(String problemReportedBy) {
		this.problemReportedBy = problemReportedBy;
	}
	public String getProblemType() {
		return problemType;
	}
	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}
	public Date getProblemReportedDate() {
		return problemReportedDate;
	}
	public void setProblemReportedDate(Date problemReportedDate) {
		this.problemReportedDate = problemReportedDate;
	}

	public Timestamp getServiceStartDate() {
		return serviceStartDate;
	}

	public void setServiceStartDate(Timestamp serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}

	public Timestamp getServiceEndDate() {
		return serviceEndDate;
	}

	public void setServiceEndDate(Timestamp serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}

	public String getDlsFlag() {
		return dlsFlag;
	}

	public void setDlsFlag(String dlsFlag) {
		this.dlsFlag = dlsFlag;
	}

	public Date getPreCallAttemptedDate() {
		return preCallAttemptedDate;
	}

	public void setPreCallAttemptedDate(Date preCallAttemptedDate) {
		this.preCallAttemptedDate = preCallAttemptedDate;
	}

	public Date getJobDoneOn() {
		return jobDoneOn;
	}

	public void setJobDoneOn(Date jobDoneOn) {
		this.jobDoneOn = jobDoneOn;
	}

	public String getPartCity() {
		return partCity;
	}

	public void setPartCity(String partCity) {
		this.partCity = partCity;
	}

	public String getPartState() {
		return partState;
	}

	public void setPartState(String partState) {
		this.partState = partState;
	}

	public String getPartZip() {
		return partZip;
	}

	public void setPartZip(String partZip) {
		this.partZip = partZip;
	}

	public String getAvailabilityDate() {
		return availabilityDate;
	}

	public void setAvailabilityDate(String availabilityDate) {
		this.availabilityDate = availabilityDate;
	}

	public Integer getRescheduleRole() {
		return rescheduleRole;
	}

	public void setRescheduleRole(Integer rescheduleRole) {
		this.rescheduleRole = rescheduleRole;
	}

	public String getPrimaryExtension() {
		return primaryExtension;
	}

	public void setPrimaryExtension(String primaryExtension) {
		this.primaryExtension = primaryExtension;
	}

	public String getAlternateExtension() {
		return alternateExtension;
	}

	public void setAlternateExtension(String alternateExtension) {
		this.alternateExtension = alternateExtension;
	}

	public Timestamp getResheduleStartDate() {
		return resheduleStartDate;
	}

	public void setResheduleStartDate(Timestamp resheduleStartDate) {
		this.resheduleStartDate = resheduleStartDate;
	}

	public Timestamp getResheduleEndDate() {
		return resheduleEndDate;
	}

	public void setResheduleEndDate(Timestamp resheduleEndDate) {
		this.resheduleEndDate = resheduleEndDate;
	}

	public String getResheduleStartTime() {
		return resheduleStartTime;
	}

	public void setResheduleStartTime(String resheduleStartTime) {
		this.resheduleStartTime = resheduleStartTime;
	}

	public String getResheduleEndTime() {
		return resheduleEndTime;
	}

	public void setResheduleEndTime(String resheduleEndTime) {
		this.resheduleEndTime = resheduleEndTime;
	}

	public String getResheduleStartDateString() {
		return resheduleStartDateString;
	}

	public void setResheduleStartDateString(String resheduleStartDateString) {
		this.resheduleStartDateString = resheduleStartDateString;
	}

	public String getResheduleEndDateString() {
		return resheduleEndDateString;
	}

	public void setResheduleEndDateString(String resheduleEndDateString) {
		this.resheduleEndDateString = resheduleEndDateString;
	}

	public Date getLastTripOn() {
		return lastTripOn;
	}

	public void setLastTripOn(Date lastTripOn) {
		this.lastTripOn = lastTripOn;
	}
	
	
	//SL-21465

	public Boolean getIsEstimationRequest() {
		return isEstimationRequest;
	}

	public void setIsEstimationRequest(Boolean isEstimationRequest) {
		this.isEstimationRequest = isEstimationRequest;
	}

	public Integer getEstimationId() {
		return estimationId;
	}

	public void setEstimationId(Integer estimationId) {
		this.estimationId = estimationId;
	}

	/*public String getEstimationStatus() {
		return estimationStatus;
	}

	public void setEstimationStatus(String estimationStatus) {
		this.estimationStatus = estimationStatus;
	}*/
	
}