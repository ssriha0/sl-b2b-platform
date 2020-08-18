package com.newco.marketplace.web.dto.provider;

import java.util.HashMap;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("Warranty")
public class WarrantyDto extends BaseDto{
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = -1027627143131293971L;
	@XStreamAlias("VendorID")
	private String vendorID;//vendor_id
	@XStreamAlias("MaxVendorID")
	private int maxVendorID;//maximum vendor_id in table (most recent)
	@XStreamAlias("WarrPeriodLabor")
	private String warrPeriodLabor;//warr_period_labor
	@XStreamAlias("WarrOfferedLabor")
	private String warrOfferedLabor;//warr_offered_labor
	@XStreamAlias("WarrOfferedParts")
	private String warrOfferedParts;//warr_offered_parts
	@XStreamAlias("WarrPeriodParts")
	private String warrPeriodParts;//warr_period_parts
	@XStreamAlias("WarrantyMeasure")
	private String warrantyMeasure;//warranty_measure
	@XStreamAlias("FreeEstimate")
	private String freeEstimate;//free_estimate
	@XStreamAlias("ConductDrugTest")
	private String conductDrugTest;//conduct_drug_test
	@XStreamAlias("ConsiderDrugTest")
	private String considerDrugTest;//consider_drug_test
	@XStreamAlias("HasEthicsPolicy")
	private String hasEthicsPolicy;//has_ethics_policy
	@XStreamAlias("RequireBadge")
	private String requireBadge;//require_badge
	@XStreamAlias("ConsiderBadge")
	private String considerBadge;//consider_badge
	@XStreamAlias("RequireUsDoc")
	private String requireUsDoc;//require_us_doc
	@XStreamAlias("ConsiderImplPolicy")
	private String considerImplPolicy;//consider_impl_policy
	@XStreamAlias("ConsiderEthicPolicy")
	private String considerEthicPolicy;//consider_ethic_policy
	@XStreamAlias("WarrantyPeriods")
	private HashMap warrantyPeriods;
	
	private String action;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getWarrantyMeasure() {
		return warrantyMeasure;
	}
	public void setWarrantyMeasure(String warrantyMeasure) {
		this.warrantyMeasure = warrantyMeasure;
	}
	public String getFreeEstimate() {
		return freeEstimate;
	}
	public void setFreeEstimate(String freeEstimate) {
		this.freeEstimate = freeEstimate;
	}
	public String getConductDrugTest() {
		return conductDrugTest;
	}
	public void setConductDrugTest(String conductDrugTest) {
		this.conductDrugTest = conductDrugTest;
	}
	public String getConsiderDrugTest() {
		return considerDrugTest;
	}
	public void setConsiderDrugTest(String considerDrugTest) {
		this.considerDrugTest = considerDrugTest;
	}
	public String getHasEthicsPolicy() {
		return hasEthicsPolicy;
	}
	public void setHasEthicsPolicy(String hasEthicsPolicy) {
		this.hasEthicsPolicy = hasEthicsPolicy;
	}
	public String getRequireBadge() {
		return requireBadge;
	}
	public void setRequireBadge(String requireBadge) {
		this.requireBadge = requireBadge;
	}
	public String getConsiderBadge() {
		return considerBadge;
	}
	public void setConsiderBadge(String considerBadge) {
		this.considerBadge = considerBadge;
	}
	public String getRequireUsDoc() {
		return requireUsDoc;
	}
	public void setRequireUsDoc(String requireUsDoc) {
		this.requireUsDoc = requireUsDoc;
	}
	public String getConsiderImplPolicy() {
		return considerImplPolicy;
	}
	public void setConsiderImplPolicy(String considerImplPolicy) {
		this.considerImplPolicy = considerImplPolicy;
	}
	public String getConsiderEthicPolicy() {
		return considerEthicPolicy;
	}
	public void setConsiderEthicPolicy(String considerEthicPolicy) {
		this.considerEthicPolicy = considerEthicPolicy;
	}
	public String getWarrPeriodLabor() {
		return warrPeriodLabor;
	}
	public void setWarrPeriodLabor(String warrPeriodLabor) {
		this.warrPeriodLabor = warrPeriodLabor;
	}
	public String getWarrOfferedLabor() {
		return warrOfferedLabor;
	}
	public void setWarrOfferedLabor(String warrOfferedLabor) {
		this.warrOfferedLabor = warrOfferedLabor;
	}
	public String getWarrOfferedParts() {
		return warrOfferedParts;
	}
	public void setWarrOfferedParts(String warrOfferedParts) {
		this.warrOfferedParts = warrOfferedParts;
	}
	public String getWarrPeriodParts() {
		return warrPeriodParts;
	}
	public void setWarrPeriodParts(String warrPeriodParts) {
		this.warrPeriodParts = warrPeriodParts;
	}
	/**
	 * @return the maxVendorID
	 */
	public int getMaxVendorID() {
		return maxVendorID;
	}
	/**
	 * @param maxVendorID the maxVendorID to set
	 */
	public void setMaxVendorID(int maxVendorID) {
		this.maxVendorID = maxVendorID;
	}
	/**
	 * @return the warrantyPeriods
	 */
	public HashMap getWarrantyPeriods() {
		return warrantyPeriods;
	}
	/**
	 * @param warrantyPeriods the warrantyPeriods to set
	 */
	public void setWarrantyPeriods(HashMap warrantyPeriods) {
		this.warrantyPeriods = warrantyPeriods;
	}
	/**
	 * @return the vendorID
	 */
	public String getVendorID() {
		return vendorID;
	}
	/**
	 * @param vendorID the vendorID to set
	 */
	public void setVendorID(String vendorID) {
		this.vendorID = vendorID;
	}
}
