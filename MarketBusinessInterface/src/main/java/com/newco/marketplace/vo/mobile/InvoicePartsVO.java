package com.newco.marketplace.vo.mobile;

import java.util.Date;
import java.util.List;
import com.newco.marketplace.api.mobile.beans.sodetails.Document;
import com.newco.marketplace.api.mobile.beans.sodetails.PartCoverage;
import com.newco.marketplace.api.mobile.beans.sodetails.PartSource;
import com.newco.marketplace.dto.vo.LookupVO;

public class InvoicePartsVO {
	
	private String soId;
	private String partCoverage;
	private String partSource;
	private String nonSearsSource;
	private String partDescription;
	private String partNumber;
	private String invoiceNumber;
	private String unitCost;
	private String retailPrice;
	private Integer qty;
	private String retailCostToInventory;
	private String retailReimbursement;
	private String retailPriceSLGrossUp;
	private String retailSLGrossUp;
	private String netPayment;
	private String divisionNumber;
	private String sourceNumber;
	private String partStatus;
	private String finalPayment;
	private Integer documentCount;
	
	//adding part url as apert of add part
	private String partUrl;
	List<Integer> documentIdList;	

  //added for add provider invoice part info
	private String invoiceId;
	private double estProviderPayement;
	private double finalPrice;
	private String category;
	// added for parts management
	private Integer partInvoiceId;
	List<Document> invoiceDocumentList;	

	List<PartSource> sourceTypes;	
	List<LookupVO> partStatusTypes;	
	// added for LIS identification
	private String partNoSource;

	public List<Document> getInvoiceDocumentList() {
		return invoiceDocumentList;
	}
	public void setInvoiceDocumentList(List<Document> invoiceDocumentList) {
		this.invoiceDocumentList = invoiceDocumentList;
	}
	private String reimbursementRate;
	private String slGrossUpValue;
    private String  adjTolerance;
    
    //claim Status
    private String  claimStatus;
	// partInvoiveSource
    private String partInvoiceSource;
    
    // indicator for autoAdjudication
    private Boolean autoAdjudicationInd=false;
    
    private String providerMargin;
    
    private String reimbursementMax;
    
    private String commercialPrice;
    private Integer partIdentifier;
    private String errorMessage;
    
    
	public Integer getPartIdentifier() {
		return partIdentifier;
	}
	public void setPartIdentifier(Integer partIdentifier) {
		this.partIdentifier = partIdentifier;
	}
	public String getProviderMargin() {
		return providerMargin;
	}
	public void setProviderMargin(String providerMargin) {
		this.providerMargin = providerMargin;
	}
	public String getReimbursementMax() {
		return reimbursementMax;
	}
	public void setReimbursementMax(String reimbursementMax) {
		this.reimbursementMax = reimbursementMax;
	}
	public Boolean getAutoAdjudicationInd() {
		return autoAdjudicationInd;
	}
	public void setAutoAdjudicationInd(Boolean autoAdjudicationInd) {
		this.autoAdjudicationInd = autoAdjudicationInd;
	}
	
	public List<PartSource> getSourceTypes() {
		return sourceTypes;
	}
	public void setSourceTypes(List<PartSource> sourceTypes) {
		this.sourceTypes = sourceTypes;
	}
	public List<LookupVO> getPartStatusTypes() {
		return partStatusTypes;
	}
	public void setPartStatusTypes(List<LookupVO> partStatusTypes) {
		this.partStatusTypes = partStatusTypes;
	}
	
	public String getPartInvoiceSource() {
		return partInvoiceSource;
	}
	public void setPartInvoiceSource(String partInvoiceSource) {
		this.partInvoiceSource = partInvoiceSource;
	}
	public String getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
	public String getReimbursementRate() {
		return reimbursementRate;
	}
	public void setReimbursementRate(String reimbursementRate) {
		this.reimbursementRate = reimbursementRate;
	}
	public String getSlGrossUpValue() {
		return slGrossUpValue;
	}
	public void setSlGrossUpValue(String slGrossUpValue) {
		this.slGrossUpValue = slGrossUpValue;
	}
	public String getAdjTolerance() {
		return adjTolerance;
	}
	public void setAdjTolerance(String adjTolerance) {
		this.adjTolerance = adjTolerance;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getFinalPayment() {
		return finalPayment;
	}
	public void setFinalPayment(String finalPayment) {
		this.finalPayment = finalPayment;
	}
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;

	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getPartCoverage() {
		return partCoverage;
	}
	public void setPartCoverage(String partCoverage) {
		this.partCoverage = partCoverage;
	}
	public String getRetailCostToInventory() {
		return retailCostToInventory;
	}
	public void setRetailCostToInventory(String retailCostToInventory) {
		this.retailCostToInventory = retailCostToInventory;
	}
	public String getRetailReimbursement() {
		return retailReimbursement;
	}
	public void setRetailReimbursement(String retailReimbursement) {
		this.retailReimbursement = retailReimbursement;
	}
	public String getRetailPriceSLGrossUp() {
		return retailPriceSLGrossUp;
	}
	public void setRetailPriceSLGrossUp(String retailPriceSLGrossUp) {
		this.retailPriceSLGrossUp = retailPriceSLGrossUp;
	}
	public String getRetailSLGrossUp() {
		return retailSLGrossUp;
	}
	public void setRetailSLGrossUp(String retailSLGrossUp) {
		this.retailSLGrossUp = retailSLGrossUp;
	}
	public String getPartSource() {
		return partSource;
	}
	public void setPartSource(String partSource) {
		this.partSource = partSource;
	}
	public String getNonSearsSource() {
		return nonSearsSource;
	}
	public void setNonSearsSource(String nonSearsSource) {
		this.nonSearsSource = nonSearsSource;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}	
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(String unitCost) {
		this.unitCost = unitCost;
	}
	public String getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
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
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getNetPayment() {
		return netPayment;
	}
	public void setNetPayment(String netPayment) {
		this.netPayment = netPayment;
	}
	public String getDivisionNumber() {
		return divisionNumber;
	}
	public void setDivisionNumber(String divisionNumber) {
		this.divisionNumber = divisionNumber;
	}
	public String getSourceNumber() {
		return sourceNumber;
	}
	public void setSourceNumber(String sourceNumber) {
		this.sourceNumber = sourceNumber;
	}
	public double getEstProviderPayement() {
		return estProviderPayement;
	}
	public void setEstProviderPayement(double estProviderPayement) {
		this.estProviderPayement = estProviderPayement;
	}
	public double getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}
	public String getPartStatus() {
		return partStatus;
	}
	public void setPartStatus(String partStatus) {
		this.partStatus = partStatus;
	}
	public List<Integer> getDocumentIdList() {
		return documentIdList;
	}
	public void setDocumentIdList(List<Integer> documentIdList) {
		this.documentIdList = documentIdList;
	}
	public String getPartUrl() {
		return partUrl;
	}
	public void setPartUrl(String partUrl) {
		this.partUrl = partUrl;
	}
	public Integer getPartInvoiceId() {
		return partInvoiceId;
	}
	public void setPartInvoiceId(Integer partInvoiceId) {
		this.partInvoiceId = partInvoiceId;
	}
	public Integer getDocumentCount() {
		return documentCount;
	}
	public void setDocumentCount(Integer documentCount) {
		this.documentCount = documentCount;
	}
	public String getCommercialPrice() {
		return commercialPrice;
	}
	public void setCommercialPrice(String commercialPrice) {
		this.commercialPrice = commercialPrice;
	}

	public String getPartNoSource() {
		return partNoSource;
	}
	public void setPartNoSource(String partNoSource) {
		this.partNoSource = partNoSource;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
