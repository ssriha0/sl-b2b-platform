package com.newco.marketplace.dto.vo;




import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.CompareToBuilder;

import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.sears.os.vo.SerializableBaseVO;

public class EstimateVO extends SerializableBaseVO {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String soId;
	private Integer estimationId;
	private String estimationRefNo;
	private String description;
	private Date estimationDate;
	private List<EstimateTaskVO> estimateTasks;
	private List<EstimateTaskVO> estimateParts;
	private List<EstimateTaskVO> estimateItems;
	//Adding Other Services as part of R16_2_1
	private List<EstimateTaskVO> estimateOtherEstimateServices;
	private Double totalLaborPrice;
	private Double totalPartsPrice;
	private Double totalOtherServicePrice;
	private String discountType;
	private Double discountedPercentage;
	private Double discountedAmount;
	private Double taxRate;
    private String taxType;
	private Double taxPrice;
	private Double totalPrice;
	//SL-21934
	private String laborDiscountType;
	private Double laborDiscountedPercentage;
	private Double laborDiscountedAmount;
	private Double laborTaxRate;
	private Double laborTaxPrice;
	private String partsDiscountType;
	private Double partsDiscountedPercentage;
	private Double partsDiscountedAmount;
	private Double partsTaxRate;
	private Double partsTaxPrice;
	
	private String comments;
	private Integer logoDocumentId;
	private String status;
	private String isDraftEstimate;
	private Boolean isEstimateIdExist;
	//Added to check save/edit is success or not
	private Integer addEditestimationId;
	private Integer vendorId;
	private Integer resourceId;
	private Date createdDate;
	private Date modifiedDate;
	private String createdBy;
	private String modifiedBy;
	//Added for Holding Error Response
	private ResultsCode resultCode;
	
	//Added to hold firm and provider's Address
	private EstimateLocationVO firmLocation;
	private EstimateLocationVO providerLocation;
	
	// B2C changes: Estimation History details
	private List<EstimateHistoryVO> estimateHistoryList;
	private List<EstimateHistoryTaskVO> estimateTasksHistory;
	private List<EstimateHistoryTaskVO> estimatePartsHistory;
	private List<EstimateHistoryTaskVO> estimateOtherServicesHistory;
	//added for Service Location time zone
	private String timeZone;
	private String action;
	
	private boolean estimateAdded;
	boolean estimateUpdate;
	boolean estimatePriceChange;

	private Date estimationExpiryDate;


	//SL-21645
	private SoLocation serviceLocation;
	private Contact serviceContact;
	private String buyerRefValue;
	//SL-21902
	private boolean isEstimateUpdatedFromDraftTONew;
	
	private com.newco.marketplace.api.common.ResultsCode apiResultCode;

	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	private String acceptSource;
	private double tripCharge;
	private String respondedCustomerName;
	private Date responseDate;
	
	public Date getResponseDate() {
		return responseDate;
	}
	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}
	public Integer getEstimationId() {
		return estimationId;
	}
	public void setEstimationId(Integer estimationId) {
		this.estimationId = estimationId;
	}
	public String getEstimationRefNo() {
		return estimationRefNo;
	}
	public void setEstimationRefNo(String estimationRefNo) {
		this.estimationRefNo = estimationRefNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getEstimationDate() {
		return estimationDate;
	}
	public void setEstimationDate(Date estimationDate) {
		this.estimationDate = estimationDate;
	}
	public List<EstimateTaskVO> getEstimateTasks() {
		return estimateTasks;
	}
	public void setEstimateTasks(List<EstimateTaskVO> estimateTasks) {
		this.estimateTasks = estimateTasks;
	}
	public List<EstimateTaskVO> getEstimateParts() {
		return estimateParts;
	}
	public void setEstimateParts(List<EstimateTaskVO> estimateParts) {
		this.estimateParts = estimateParts;
	}
	public List<EstimateTaskVO> getEstimateItems() {
		return estimateItems;
	}
	public void setEstimateItems(List<EstimateTaskVO> estimateItems) {
		this.estimateItems = estimateItems;
	}
	public Double getTotalLaborPrice() {
		return totalLaborPrice;
	}
	public void setTotalLaborPrice(Double totalLaborPrice) {
		this.totalLaborPrice = totalLaborPrice;
	}
	public Double getTotalPartsPrice() {
		return totalPartsPrice;
	}
	public void setTotalPartsPrice(Double totalPartsPrice) {
		this.totalPartsPrice = totalPartsPrice;
	}
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	public Double getDiscountedPercentage() {
		return discountedPercentage;
	}
	public List<EstimateHistoryTaskVO> getEstimateOtherServicesHistory() {
		return estimateOtherServicesHistory;
	}
	public void setEstimateOtherServicesHistory(
			List<EstimateHistoryTaskVO> estimateOtherServicesHistory) {
		this.estimateOtherServicesHistory = estimateOtherServicesHistory;
	}
	public void setDiscountedPercentage(Double discountedPercentage) {
		this.discountedPercentage = discountedPercentage;
	}
	public Double getDiscountedAmount() {
		return discountedAmount;
	}
	public void setDiscountedAmount(Double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}
	public Double getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	public Double getTaxPrice() {
		return taxPrice;
	}
	public void setTaxPrice(Double taxPrice) {
		this.taxPrice = taxPrice;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Integer getLogoDocumentId() {
		return logoDocumentId;
	}
	public void setLogoDocumentId(Integer logoDocumentId) {
		this.logoDocumentId = logoDocumentId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getAddEditestimationId() {
		return addEditestimationId;
	}
	public void setAddEditestimationId(Integer addEditestimationId) {
		this.addEditestimationId = addEditestimationId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public String getAcceptSource() {
		return acceptSource;
	}
	public void setAcceptSource(String acceptSource) {
		this.acceptSource = acceptSource;
	}
	public double getTripCharge() {
		return tripCharge;
	}
	public void setTripCharge(double tripCharge) {
		this.tripCharge = tripCharge;
	}
	public String getRespondedCustomerName() {
		return respondedCustomerName;
	}
	public void setRespondedCustomerName(String respondedCustomerName) {
		this.respondedCustomerName = respondedCustomerName;
	}
	public ResultsCode getResultCode() {
		return resultCode;
	}
	public void setResultCode(ResultsCode resultCode) {
		this.resultCode = resultCode;
	}
	public String getIsDraftEstimate() {
		return isDraftEstimate;
	}
	public void setIsDraftEstimate(String isDraftEstimate) {
		this.isDraftEstimate = isDraftEstimate;
	}
	public Boolean getIsEstimateIdExist() {
		return isEstimateIdExist;
	}
	public void setIsEstimateIdExist(Boolean isEstimateIdExist) {
		this.isEstimateIdExist = isEstimateIdExist;
	}
	public EstimateLocationVO getFirmLocation() {
		return firmLocation;
	}
	public void setFirmLocation(EstimateLocationVO firmLocation) {
		this.firmLocation = firmLocation;
	}
	public EstimateLocationVO getProviderLocation() {
		return providerLocation;
	}
	public void setProviderLocation(EstimateLocationVO providerLocation) {
		this.providerLocation = providerLocation;
	}
	public List<EstimateHistoryVO> getEstimateHistoryList() {
		return estimateHistoryList;
	}
	public void setEstimateHistoryList(List<EstimateHistoryVO> estimateHistoryList) {
		this.estimateHistoryList = estimateHistoryList;
	}
	public List<EstimateHistoryTaskVO> getEstimateTasksHistory() {
		return estimateTasksHistory;
	}
	public void setEstimateTasksHistory(
			List<EstimateHistoryTaskVO> estimateTasksHistory) {
		this.estimateTasksHistory = estimateTasksHistory;
	}
	public List<EstimateHistoryTaskVO> getEstimatePartsHistory() {
		return estimatePartsHistory;
	}
	public void setEstimatePartsHistory(
			List<EstimateHistoryTaskVO> estimatePartsHistory) {
		this.estimatePartsHistory = estimatePartsHistory;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	
	public Double getTotalOtherServicePrice() {
		return totalOtherServicePrice;
	}
	public void setTotalOtherServicePrice(Double totalOtherServicePrice) {
		this.totalOtherServicePrice = totalOtherServicePrice;
	}
	
	public List<EstimateTaskVO> getEstimateOtherEstimateServices() {
		return estimateOtherEstimateServices;
	}
	public void setEstimateOtherEstimateServices(
			List<EstimateTaskVO> estimateOtherEstimateServices) {
		this.estimateOtherEstimateServices = estimateOtherEstimateServices;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public boolean isEstimateAdded() {
		return estimateAdded;
	}
	public void setEstimateAdded(boolean estimateAdded) {
		this.estimateAdded = estimateAdded;
	}
	public Date getEstimationExpiryDate() {
		return estimationExpiryDate;
	}
	public void setEstimationExpiryDate(Date estimationExpiryDate) {
		this.estimationExpiryDate = estimationExpiryDate;
	}
	
	
	
	public boolean isEstimateUpdate() {
		return estimateUpdate;
	}
	public void setEstimateUpdate(boolean estimateUpdate) {
		this.estimateUpdate = estimateUpdate;
	}
	public boolean isEstimatePriceChange() {
		return estimatePriceChange;
	}
	public void setEstimatePriceChange(boolean estimatePriceChange) {
		this.estimatePriceChange = estimatePriceChange;
	}
	public int compareEstimation(EstimateVO o) {
		return new CompareToBuilder()
				.append(this.estimationRefNo, o.estimationRefNo)
				.append(this.description, o.description)
			 	.append(this.estimationDate, o.estimationDate)
			 	.append(this.comments, o.comments)
			 	.append(this.logoDocumentId, o.logoDocumentId)
			 	.append(this.status, o.status)
			 	.append(this.estimationExpiryDate, o.estimationExpiryDate)			 	
			 	.toComparison();
	} 
	
	// SL-21654 - Removed date field as it will be different for diff request even when the estimate is same
	public int compareEstimationWODate(EstimateVO o) {
		return new CompareToBuilder()
				.append(this.estimationRefNo, o.estimationRefNo)
				.append(this.description, o.description)
			 	.append(this.comments, o.comments)
			 	.append(this.logoDocumentId, o.logoDocumentId)
			 	.append(this.status, o.status)
			 	.append(this.estimationExpiryDate, o.estimationExpiryDate)			 	
			 	.toComparison();
	} 
	
	public int comparePrice(EstimateVO o) {
		return new CompareToBuilder()
			 	.append(this.totalLaborPrice, o.totalLaborPrice)
			 	.append(this.totalPartsPrice, o.totalPartsPrice)
			 	.append(this.totalOtherServicePrice, o.totalOtherServicePrice)
			 	.append(this.discountType, o.discountType)
			 	.append(this.discountedPercentage, o.discountedPercentage)
			 	.append(this.discountedAmount, o.discountedAmount)
			 	.append(this.taxRate, o.taxRate)
			 	.append(this.taxType, o.taxType)
			 	.append(this.taxPrice, o.taxPrice)
			 	.append(this.totalPrice, o.totalPrice)
			 	.append(this.laborDiscountType, o.laborDiscountType)
			 	.append(this.laborDiscountedPercentage, o.laborDiscountedPercentage)
			 	.append(this.laborDiscountedAmount, o.laborDiscountedAmount)
			 	.append(this.laborTaxRate, o.laborTaxRate)
			 	.append(this.laborTaxPrice, o.laborTaxPrice)
			 	.append(this.partsDiscountType, o.partsDiscountType)
			 	.append(this.partsDiscountedPercentage, o.partsDiscountedPercentage)
			 	.append(this.partsDiscountedAmount, o.partsDiscountedAmount)
			 	.append(this.partsTaxRate, o.partsTaxRate)
			 	.append(this.partsTaxPrice, o.partsTaxPrice)
			 	.toComparison();
	}
	
	
	public SoLocation getServiceLocation() {
		return serviceLocation;
	}
	public void setServiceLocation(SoLocation serviceLocation) {
		this.serviceLocation = serviceLocation;
	}
	public Contact getServiceContact() {
		return serviceContact;
	}
	public void setServiceContact(Contact serviceContact) {
		this.serviceContact = serviceContact;
	}
	public com.newco.marketplace.api.common.ResultsCode getApiResultCode() {
		return apiResultCode;
	}
	public void setApiResultCode(
			com.newco.marketplace.api.common.ResultsCode apiResultCode) {
		this.apiResultCode = apiResultCode;
	} 
	
	public String getBuyerRefValue() {
		return buyerRefValue;
	}
	public void setBuyerRefValue(String buyerRefValue) {
		this.buyerRefValue = buyerRefValue;
	}
	
	public boolean isEstimateUpdatedFromDraftTONew() {
		return isEstimateUpdatedFromDraftTONew;
	}
	public void setEstimateUpdatedFromDraftTONew(
			boolean isEstimateUpdatedFromDraftTONew) {
		this.isEstimateUpdatedFromDraftTONew = isEstimateUpdatedFromDraftTONew;
	}
	public String getLaborDiscountType() {
		return laborDiscountType;
	}
	public void setLaborDiscountType(String laborDiscountType) {
		this.laborDiscountType = laborDiscountType;
	}
	public Double getLaborDiscountedPercentage() {
		return laborDiscountedPercentage;
	}
	public void setLaborDiscountedPercentage(Double laborDiscountedPercentage) {
		this.laborDiscountedPercentage = laborDiscountedPercentage;
	}
	public Double getLaborDiscountedAmount() {
		return laborDiscountedAmount;
	}
	public void setLaborDiscountedAmount(Double laborDiscountedAmount) {
		this.laborDiscountedAmount = laborDiscountedAmount;
	}
	public Double getLaborTaxRate() {
		return laborTaxRate;
	}
	public void setLaborTaxRate(Double laborTaxRate) {
		this.laborTaxRate = laborTaxRate;
	}
	public Double getLaborTaxPrice() {
		return laborTaxPrice;
	}
	public void setLaborTaxPrice(Double laborTaxPrice) {
		this.laborTaxPrice = laborTaxPrice;
	}
	public String getPartsDiscountType() {
		return partsDiscountType;
	}
	public void setPartsDiscountType(String partsDiscountType) {
		this.partsDiscountType = partsDiscountType;
	}
	public Double getPartsDiscountedPercentage() {
		return partsDiscountedPercentage;
	}
	public void setPartsDiscountedPercentage(Double partsDiscountedPercentage) {
		this.partsDiscountedPercentage = partsDiscountedPercentage;
	}
	public Double getPartsDiscountedAmount() {
		return partsDiscountedAmount;
	}
	public void setPartsDiscountedAmount(Double partsDiscountedAmount) {
		this.partsDiscountedAmount = partsDiscountedAmount;
	}
	public Double getPartsTaxRate() {
		return partsTaxRate;
	}
	public void setPartsTaxRate(Double partsTaxRate) {
		this.partsTaxRate = partsTaxRate;
	}
	public Double getPartsTaxPrice() {
		return partsTaxPrice;
	}
	public void setPartsTaxPrice(Double partsTaxPrice) {
		this.partsTaxPrice = partsTaxPrice;
	} 
	
	
	
}
