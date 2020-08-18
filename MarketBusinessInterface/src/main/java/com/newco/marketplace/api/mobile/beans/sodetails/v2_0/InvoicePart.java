package com.newco.marketplace.api.mobile.beans.sodetails.v2_0;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.newco.marketplace.api.mobile.beans.sodetails.InvoiceDocuments;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing InvoicePart information.
 * @author Infosys
 *
 */
@XStreamAlias("invoicePart")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoicePart {

	@XStreamAlias("invoicePartId")
	private String invoicePartId;
	
	@XStreamAlias("partCoverage")
	private String partCoverage;
	
	@XStreamAlias("partSource")
	private String partSource;	
	
	@XStreamAlias("partNumber")
	private String partNumber;
	
	@XStreamAlias("partDescription")
	private String partDescription;
	
	@XStreamAlias("invoiceNumber")
	private String invoiceNumber;
	
	@XStreamAlias("unitCost")
	private String unitCost;
	
	@XStreamAlias("retailPrice")
	private String retailPrice;
	
	@XStreamAlias("qty")
	private Integer qty;
	
	@XStreamAlias("nonSearsSource")
	private String nonSearsSource;
	
	@XStreamAlias("divisionNumber")
	private String divisionNumber;

	@XStreamAlias("sourceNumber")
	private String sourceNumber;
	
	@XStreamAlias("category")
	private String category;

	@XStreamAlias("partStatus")
	private String partStatus;
	
	@XStreamAlias("isManual")
	private String isManual;

	@XStreamAlias("invoiceSuppliers")
	private InvoiceSuppliers invoiceSuppliers;
	
	@XStreamAlias("invoiceDocuments")
	private InvoiceDocuments invoiceDocuments;

	/**
	 * @return the invoicePartId
	 */
	public String getInvoicePartId() {
		return invoicePartId;
	}

	/**
	 * @param invoicePartId the invoicePartId to set
	 */
	public void setInvoicePartId(String invoicePartId) {
		this.invoicePartId = invoicePartId;
	}

	/**
	 * @return the partCoverage
	 */
	public String getPartCoverage() {
		return partCoverage;
	}

	/**
	 * @param partCoverage the partCoverage to set
	 */
	public void setPartCoverage(String partCoverage) {
		this.partCoverage = partCoverage;
	}

	/**
	 * @return the partSource
	 */
	public String getPartSource() {
		return partSource;
	}

	/**
	 * @param partSource the partSource to set
	 */
	public void setPartSource(String partSource) {
		this.partSource = partSource;
	}

	/**
	 * @return the partNumber
	 */
	public String getPartNumber() {
		return partNumber;
	}

	/**
	 * @param partNumber the partNumber to set
	 */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	/**
	 * @return the partDescription
	 */
	public String getPartDescription() {
		return partDescription;
	}

	/**
	 * @param partDescription the partDescription to set
	 */
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return the unitCost
	 */
	public String getUnitCost() {
		return unitCost;
	}

	/**
	 * @param unitCost the unitCost to set
	 */
	public void setUnitCost(String unitCost) {
		this.unitCost = unitCost;
	}

	/**
	 * @return the retailPrice
	 */
	public String getRetailPrice() {
		return retailPrice;
	}

	/**
	 * @param retailPrice the retailPrice to set
	 */
	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}

	/**
	 * @return the qty
	 */
	public Integer getQty() {
		return qty;
	}

	/**
	 * @param qty the qty to set
	 */
	public void setQty(Integer qty) {
		this.qty = qty;
	}

	/**
	 * @return the nonSearsSource
	 */
	public String getNonSearsSource() {
		return nonSearsSource;
	}

	/**
	 * @param nonSearsSource the nonSearsSource to set
	 */
	public void setNonSearsSource(String nonSearsSource) {
		this.nonSearsSource = nonSearsSource;
	}

	/**
	 * @return the divisionNumber
	 */
	public String getDivisionNumber() {
		return divisionNumber;
	}

	/**
	 * @param divisionNumber the divisionNumber to set
	 */
	public void setDivisionNumber(String divisionNumber) {
		this.divisionNumber = divisionNumber;
	}

	/**
	 * @return the sourceNumber
	 */
	public String getSourceNumber() {
		return sourceNumber;
	}

	/**
	 * @param sourceNumber the sourceNumber to set
	 */
	public void setSourceNumber(String sourceNumber) {
		this.sourceNumber = sourceNumber;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the partStatus
	 */
	public String getPartStatus() {
		return partStatus;
	}

	/**
	 * @param partStatus the partStatus to set
	 */
	public void setPartStatus(String partStatus) {
		this.partStatus = partStatus;
	}

	/**
	 * @return the invoiceSuppliers
	 */
	public InvoiceSuppliers getInvoiceSuppliers() {
		return invoiceSuppliers;
	}

	/**
	 * @param invoiceSuppliers the invoiceSuppliers to set
	 */
	public void setInvoiceSuppliers(InvoiceSuppliers invoiceSuppliers) {
		this.invoiceSuppliers = invoiceSuppliers;
	}

	public InvoiceDocuments getInvoiceDocuments() {
		return invoiceDocuments;
	}

	public void setInvoiceDocuments(InvoiceDocuments invoiceDocuments) {
		this.invoiceDocuments = invoiceDocuments;
	}

	public String getIsManual() {
		return isManual;
	}

	public void setIsManual(String isManual) {
		this.isManual = isManual;
	}
}
