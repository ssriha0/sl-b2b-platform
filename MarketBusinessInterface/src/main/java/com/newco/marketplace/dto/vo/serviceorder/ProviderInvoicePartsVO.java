package com.newco.marketplace.dto.vo.serviceorder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.api.mobile.beans.sodetails.Document;
import com.newco.marketplace.api.mobile.beans.sodetails.InvoiceDocumentVO;
import com.newco.marketplace.webservices.base.CommonVO;

public class ProviderInvoicePartsVO extends CommonVO {
	
	private static final long serialVersionUID = 20090428L;

	//This id is required to retain invoice child Objects(invoiceDocument and Location)
	private Integer invoicePartId;
	//newly added columns
	private String partStatus;
	private String category;
	private String soId;
	private String partCoverage;
	private String source;
	private String partNo;
	private String description;
	private String invoiceNo;
	private BigDecimal unitCost;
	private BigDecimal retailPrice;
	private int qty;
	private BigDecimal estProviderPartsPayment;
	private BigDecimal finalPrice;
	private String nonSearsSource;
	private String slGrossUpValue;
	private String reimbursementRate;
	//for finance
	private String retailCostToInventory;
	private String retailReimbursement;
	private String retailPriceSLGrossUp;
	private String retailSLGrossUp;
	private String divisionNumber;
	private String sourceNumber;
	private String partsUrl;
	//added for parts management
	private  List<InvoiceDocumentVO> invoiceDocuments;	
	//R12_0_2 Added for part Source
	private String partSource;
	private BigDecimal totalEstProviderPayment;
	private boolean invoiceDocExists = false;
	
	
	//R12_1: Newly added as part of the release
	private String claimStatus;
private BigDecimal providerMargin;
private BigDecimal commercialPrice;
	

	
//	public ProviderInvoicePartsVO(String partCoverage, String source,
//			String partNo, String invoiceNo, BigDecimal unitCost,
//			BigDecimal retailPrice, Integer qty, String description,
//			BigDecimal estProviderPartsPayment, BigDecimal finalPrice, String nonSearsSource) {
//		super();
//		this.partCoverage = partCoverage;
//		this.source = source;
//		this.partNo = partNo;
//		this.invoiceNo = invoiceNo;
//		this.unitCost = unitCost;
//		this.retailPrice = retailPrice;
//		this.qty = qty;
//		this.description = description;
//		this.estProviderPartsPayment = estProviderPartsPayment;
//		this.finalPrice = finalPrice;
//		this.nonSearsSource = nonSearsSource;
//	}	
	
	public List<InvoiceDocumentVO> getInvoiceDocuments() {
		return invoiceDocuments;
	}
	public void setInvoiceDocuments(List<InvoiceDocumentVO> invoiceDocuments) {
		this.invoiceDocuments = invoiceDocuments;
	}
	public String getPartsUrl() {
		return partsUrl;
	}
	public void setPartsUrl(String partsUrl) {
		this.partsUrl = partsUrl;
	}
	public String getSlGrossUpValue() {
		return slGrossUpValue;
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
	public void setSlGrossUpVaue(String slGrossUpValue) {
		this.slGrossUpValue = slGrossUpValue;
	}
	public String getReimbursementRate() {
		return reimbursementRate;
	}
	public void setReimbursementRate(String reimbursementRate) {
		this.reimbursementRate = reimbursementRate;
	}
	public String getNonSearsSource() {
		return nonSearsSource;
	}
	public void setNonSearsSource(String nonSearsSource) {
		this.nonSearsSource = nonSearsSource;
	}
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
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public BigDecimal getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}
	public BigDecimal getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public BigDecimal getEstProviderPartsPayment() {
		return estProviderPartsPayment;
	}
	public void setEstProviderPartsPayment(BigDecimal estProviderPartsPayment) {
		this.estProviderPartsPayment = estProviderPartsPayment;
	}
	public BigDecimal getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
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
	public Integer getInvoicePartId() {
		return invoicePartId;
	}
	public void setInvoicePartId(Integer invoicePartId) {
		this.invoicePartId = invoicePartId;
	}
	public String getPartStatus() {
		return partStatus;
	}
	public void setPartStatus(String partStatus) {
		this.partStatus = partStatus;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPartSource() {
		return partSource;
	}
	public void setPartSource(String partSource) {
		this.partSource = partSource;
	}
	public BigDecimal getTotalEstProviderPayment() {
		return totalEstProviderPayment;
	}
	public void setTotalEstProviderPayment(BigDecimal totalEstProviderPayment) {
		this.totalEstProviderPayment = totalEstProviderPayment;
	}
	public boolean isInvoiceDocExists() {
		return invoiceDocExists;
	}
	public void setInvoiceDocExists(boolean invoiceDocExists) {
		this.invoiceDocExists = invoiceDocExists;
	}
	public String getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
	public BigDecimal getProviderMargin() {
		return providerMargin;
	}
	public void setProviderMargin(BigDecimal providerMargin) {
		this.providerMargin = providerMargin;
	}
	public BigDecimal getCommercialPrice() {
		return commercialPrice;
	}
	public void setCommercialPrice(BigDecimal commercialPrice) {
		this.commercialPrice = commercialPrice;
	}
	
	
}
