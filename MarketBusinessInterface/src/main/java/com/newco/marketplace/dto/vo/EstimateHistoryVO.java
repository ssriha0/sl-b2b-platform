package com.newco.marketplace.dto.vo;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

public class EstimateHistoryVO extends SerializableBaseVO {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String soId;
	private Integer estimationId;
	private Integer estimationHistoryId;
	private String estimationRefNo;
	private String description;
	private Date estimationDate;
	
	private Double totalLaborPrice;
	private Double totalPartsPrice;
	private Double totalOtherServicePrice;
	private String discountType;
	private Double discountedPercentage;
	private Double discountedAmount;
	private Double taxRate;
    private String taxType;
	private Double taxPrice;
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
	private Double totalPrice;
	private String comments;
	private Integer logoDocumentId;
	private String status;
	private Integer vendorId;
	private Integer resourceId;
	private Date createdDate;
	private Date modifiedDate;
	private String createdBy;
	private String modifiedBy;
	
	//Added to hold firm and provider's Address
	private EstimateLocationVO firmLocation;
	private EstimateLocationVO providerLocation;
	
	private String acceptSource;
	private double tripCharge;
	private String respondedCustomerName;
	private Date responseDate;
	private String action;
	private Date estimationExpiryDate;

	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
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
	public Date getResponseDate() {
		return responseDate;
	}
	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Integer getEstimationHistoryId() {
		return estimationHistoryId;
	}
	public void setEstimationHistoryId(Integer estimationHistoryId) {
		this.estimationHistoryId = estimationHistoryId;
	}
	public Double getTotalOtherServicePrice() {
		return totalOtherServicePrice;
	}
	public void setTotalOtherServicePrice(Double totalOtherServicePrice) {
		this.totalOtherServicePrice = totalOtherServicePrice;
	}
	public Date getEstimationExpiryDate() {
		return estimationExpiryDate;
	}
	public void setEstimationExpiryDate(Date estimationExpiryDate) {
		this.estimationExpiryDate = estimationExpiryDate;
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
