package com.newco.marketplace.api.mobile.beans.sodetails;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing part information.
 * @author Infosys
 *
 */
@XStreamAlias("partInvoice")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartInvoice {
	
	@XStreamAlias("invoiceId")
	private String invoiceId;
	
	@XStreamAlias("partCoverage")
	private String partCoverage;
	
	@XStreamAlias("partSource")
	private String partSource;	
	
	@XStreamAlias("nonSearsSource")
	private String nonSearsSource;

	@XStreamAlias("partNumber")
	private String partNumber;
		
	@XStreamAlias("partDescription")
	private String partDescription;		
	
	@XStreamAlias("invoiceNumber")
	private String invoiceNumber;
	
	@XStreamAlias("divisionNumber")
	private String divisionNumber;	
	
	@XStreamAlias("sourceNumber")
	private String sourceNumber;
	
	@XStreamAlias("unitCost")
	private String unitCost;

	@XStreamAlias("retailPrice")
	private String retailPrice;
	
	@XStreamAlias("qty")
	private Integer qty;

	public String getPartCoverage() {
		return partCoverage;
	}

	public void setPartCoverage(String partCoverage) {
		this.partCoverage = partCoverage;
	}

	public String getPartSource() {
		return partSource;
	}

	public void setPartSource(String partSource) {
		this.partSource = partSource;
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

	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getNonSearsSource() {
		return nonSearsSource;
	}

	public void setNonSearsSource(String nonSearsSource) {
		this.nonSearsSource = nonSearsSource;
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
	
	
	
}
