package com.newco.marketplace.api.beans.so;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing pricing information.
 * @author Infosys
 *
 */
@XStreamAlias("pricing")
public class Pricing {
	
	@XStreamAlias("priceModel")
	private String priceModel;
	
	@XStreamAlias("laborSpendLimit")
	private String laborSpendLimit;
	
	@XStreamAlias("partsSpendLimit")
	private String partsSpendLimit;
	
	@XStreamAlias("finalPriceForLabor")
	private String finalPriceForLabor;
	
	@XStreamAlias("finalPriceForParts")
	private String finalPriceForParts;
	
	@XStreamAlias("spendLimitComments")
	private String spendLimitComments;
	
	@XStreamAlias("fundingSourceId")
	private String fundingSourceId;
	
	@XStreamAlias("laborPriceChangeReasonCode")
	private String laborPriceChangeReasonCode;
	
	@XStreamAlias("laborPriceChangeComments")
	private String laborPriceChangeComments;
	
	@XStreamAlias("laborPriceChangeBy")
	private String laborPriceChangeBy;
	
	@XStreamAlias("partsPriceChangeReasonCode")
	private String partsPriceChangeReasonCode;
	
	@XStreamAlias("partsPriceChangeComments")
	private String partsPriceChangeComments;
	
	@XStreamAlias("partsPriceChangeBy")
	private String partsPriceChangeBy;	
	
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

	public String getLaborSpendLimit() {
		return laborSpendLimit;
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

	public String getSpendLimitComments() {
		return spendLimitComments;
	}

	public void setSpendLimitComments(String spendLimitComments) {
		this.spendLimitComments = spendLimitComments;
	}

	public String getPriceModel() {
		return priceModel;
	}

	public void setPriceModel(String priceModel) {
		this.priceModel = priceModel;
	}
	
	public String getFinalPriceForLabor() {
		return finalPriceForLabor;
	}

	public void setFinalPriceForLabor(String finalPriceForLabor) {
		this.finalPriceForLabor = finalPriceForLabor;
	}

	public String getFinalPriceForParts() {
		return finalPriceForParts;
	}

	public void setFinalPriceForParts(String finalPriceForParts) {
		this.finalPriceForParts = finalPriceForParts;
	}

	public String getFundingSourceId() {
		return fundingSourceId;
	}

	public void setFundingSourceId(String fundingSourceId) {
		this.fundingSourceId = fundingSourceId;
	}

	public String getLaborPriceChangeReasonCode() {
		return laborPriceChangeReasonCode;
	}

	public void setLaborPriceChangeReasonCode(String laborPriceChangeReasonCode) {
		this.laborPriceChangeReasonCode = laborPriceChangeReasonCode;
	}

	public String getLaborPriceChangeComments() {
		return laborPriceChangeComments;
	}

	public void setLaborPriceChangeComments(String laborPriceChangeComments) {
		this.laborPriceChangeComments = laborPriceChangeComments;
	}

	public String getLaborPriceChangeBy() {
		return laborPriceChangeBy;
	}

	public void setLaborPriceChangeBy(String laborPriceChangeBy) {
		this.laborPriceChangeBy = laborPriceChangeBy;
	}

	public String getPartsPriceChangeReasonCode() {
		return partsPriceChangeReasonCode;
	}

	public void setPartsPriceChangeReasonCode(String partsPriceChangeReasonCode) {
		this.partsPriceChangeReasonCode = partsPriceChangeReasonCode;
	}

	public String getPartsPriceChangeComments() {
		return partsPriceChangeComments;
	}

	public void setPartsPriceChangeComments(String partsPriceChangeComments) {
		this.partsPriceChangeComments = partsPriceChangeComments;
	}

	public String getPartsPriceChangeBy() {
		return partsPriceChangeBy;
	}

	public void setPartsPriceChangeBy(String partsPriceChangeBy) {
		this.partsPriceChangeBy = partsPriceChangeBy;
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
