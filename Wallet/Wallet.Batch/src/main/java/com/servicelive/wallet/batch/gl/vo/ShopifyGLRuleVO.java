package com.servicelive.wallet.batch.gl.vo;

import java.io.Serializable;


// TODO: Auto-generated Javadoc
/**
 * The Class ShopifyGLRuleVO to hold the GL rules for buyer 9000.
 */
public class ShopifyGLRuleVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8554798475137419492L;

	private String reportingId;
	private String glDivision;
	private String glUnit;
	private String glAccountNo;
	private String glCategory;
	private String descr;
	private String ledgerRule;
	private String ledgerEntityId;
	private Double multiplier;
	private String transactionType;
	private String pricingExpression;
	
	public String getPricingExpression() {
		return pricingExpression;
	}
	public void setPricingExpression(String pricingExpression) {
		this.pricingExpression = pricingExpression;
	}
	
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getReportingId() {
		return reportingId;
	}
	public void setReportingId(String reportingId) {
		this.reportingId = reportingId;
	}
	public String getGlDivision() {
		return glDivision;
	}
	public void setGlDivision(String glDivision) {
		this.glDivision = glDivision;
	}
	public String getGlUnit() {
		return glUnit;
	}
	public void setGlUnit(String glUnit) {
		this.glUnit = glUnit;
	}
	public String getGlAccountNo() {
		return glAccountNo;
	}
	public void setGlAccountNo(String glAccountNo) {
		this.glAccountNo = glAccountNo;
	}
	public String getGlCategory() {
		return glCategory;
	}
	public void setGlCategory(String glCategory) {
		this.glCategory = glCategory;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getLedgerRule() {
		return ledgerRule;
	}
	public void setLedgerRule(String ledgerRule) {
		this.ledgerRule = ledgerRule;
	}
	public String getLedgerEntityId() {
		return ledgerEntityId;
	}
	public void setLedgerEntityId(String ledgerEntityId) {
		this.ledgerEntityId = ledgerEntityId;
	}
	public Double getMultiplier() {
		return multiplier;
	}
	public void setMultiplier(Double multiplier) {
		this.multiplier = multiplier;
	}
	
	
	
	
}
