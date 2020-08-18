package com.newco.marketplace.api.beans.so.update;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing service spend limit information for 
 * the SOUpdateService
 * @author Infosys
 *
 */
@XStreamAlias("spendLimitDecrease")
public class SpendLimitDecrease {
	
	
	
	@OptionalParam
	@XStreamAlias("laborSpendLimit")
	private String laborSpendLimit;
	
	@OptionalParam
	@XStreamAlias("partsSpendLimit")
	private String partsSpendLimit;
	
	@OptionalParam
	@XStreamAlias("reason")
	private String reason;
	
	@OptionalParam
	@XStreamAlias("partsTax")
	private String partsTax;
	
	@OptionalParam
	@XStreamAlias("partsDiscount")
	private String partsDiscount;
	
	@OptionalParam
	@XStreamAlias("laborTax")
	private String laborTax;
	
	@OptionalParam
	@XStreamAlias("laborDiscount")
	private String laborDiscount;
	
	@OptionalParam
	@XStreamAlias("notes")
	String notes;
	
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getLaborSpendLimit() {
		return laborSpendLimit;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setLaborSpendLimit(String laborSpendLimit) {
		this.laborSpendLimit = laborSpendLimit;
	}

	public String getPartsSpendLimit() {
		return partsSpendLimit;
	}

	public void setPartsSpendLimit(String partsSpendLimit) {
		this.partsSpendLimit = partsSpendLimit;
	}

	public String getPartsTax() {
		return partsTax;
	}

	public void setPartsTax(String partsTax) {
		this.partsTax = partsTax;
	}

	public String getPartsDiscount() {
		return partsDiscount;
	}

	public void setPartsDiscount(String partsDiscount) {
		this.partsDiscount = partsDiscount;
	}

	public String getLaborTax() {
		return laborTax;
	}

	public void setLaborTax(String laborTax) {
		this.laborTax = laborTax;
	}

	public String getLaborDiscount() {
		return laborDiscount;
	}

	public void setLaborDiscount(String laborDiscount) {
		this.laborDiscount = laborDiscount;
	}
}
