package com.newco.marketplace.webservices.dto.serviceorder;

import java.io.Serializable;

/**
 * Mapped fields from the create request object.
 * @author dpotlur
 *
 */
public class SpecialtyAddonRequest  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sku;
	private String description;
	private double retailPrice;
	private double margin;
	private boolean miscInd;
	private String scopeOfWork;
	private String coverage;
	private int quantity;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public double getMargin() {
		return margin;
	}

	public void setMargin(double margin) {
		this.margin = margin;
	}

	public boolean getMiscInd() {
		return miscInd;
	}

	public void setMiscInd(boolean miscInd) {
		this.miscInd = miscInd;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getScopeOfWork() {
		return scopeOfWork;
	}

	public void setScopeOfWork(String scopeOfWork) {
		this.scopeOfWork = scopeOfWork;
	}

	public String getCoverage() {
		return coverage;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
