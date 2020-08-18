package com.newco.marketplace.vo.provider;

import java.util.HashMap;

import com.sears.os.vo.SerializableBaseVO;

public class WarrantyVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7919842760715874122L;
	private int maxVendorID;//maximum vendor_id in table (most recent)
	private String vendorID = null;//vendor_id
	private String modifiedDate = null;//modified_date
	private String modifiedBy = null;//modified_by
	private String createdDate = null;//created_date
	private String warrPeriodLabor = null;//warr_period_labor
	private String warrOfferedLabor = null;//warr_offered_labor
	private String warrOfferedParts = null;//warr_offered_parts
	private String warrPeriodParts = null;//warr_period_parts	
	private String warrantyMeasure = null;//warranty_measure
	private String freeEstimate = null;//free_estimate
	private String conductDrugTest = null;//conduct_drug_test
	private String considerDrugTest = null;//consider_drug_test
	private String hasEthicsPolicy = null;//has_ethics_policy
	private String requireBadge = null;//require_badge
	private String considerBadge = null;//consider_badge
	private String requireUsDoc = null;//require_us_doc
	private String considerImplPolicy = null;//consider_impl_policy
	private String considerEthicPolicy = null;//consider_ethic_policy
	private HashMap warrantyPeriods = null;//for lu warranty periods table resultset
	
	public String getVendorID() {
		return vendorID;
	}
	public void setVendorID(String vendorID) {
		this.vendorID = vendorID;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifieDate) {
		this.modifiedDate = modifieDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
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
}
