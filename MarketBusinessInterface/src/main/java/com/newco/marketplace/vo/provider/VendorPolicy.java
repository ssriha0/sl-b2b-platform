package com.newco.marketplace.vo.provider;

public class VendorPolicy extends BaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3160566757392306792L;
	private int vendorId;
	private Integer warrOffered=-1;
	private Integer warPeriod=0;
	private Integer freeEstimate=null;
	private Integer conductDrugTest=null;
	private Integer considerDrugTest=null;
	private Integer hasEthicsPolicy=null;
	private Integer requireBadge=null;
	private Integer considerBadge=null;
	private Integer requireUsDoc=null;
	private Integer considerImplPolicy=null;
	private Integer considerEthicPolicy=null;
	private String  warrentyMessuare=null;
	/**
	 * @return the conductDrugTest
	 */
	public Integer getConductDrugTest() {
		return conductDrugTest;
	}
	/**
	 * @param conductDrugTest the conductDrugTest to set
	 */
	public void setConductDrugTest(Integer conductDrugTest) {
		this.conductDrugTest = conductDrugTest;
	}
	/**
	 * @return the considerBadge
	 */
	public Integer getConsiderBadge() {
		return considerBadge;
	}
	/**
	 * @param considerBadge the considerBadge to set
	 */
	public void setConsiderBadge(Integer considerBadge) {
		this.considerBadge = considerBadge;
	}
	/**
	 * @return the considerDrugTest
	 */
	public Integer getConsiderDrugTest() {
		return considerDrugTest;
	}
	/**
	 * @param considerDrugTest the considerDrugTest to set
	 */
	public void setConsiderDrugTest(Integer considerDrugTest) {
		this.considerDrugTest = considerDrugTest;
	}
	/**
	 * @return the considerEthicPolicy
	 */
	public Integer getConsiderEthicPolicy() {
		return considerEthicPolicy;
	}
	/**
	 * @param considerEthicPolicy the considerEthicPolicy to set
	 */
	public void setConsiderEthicPolicy(Integer considerEthicPolicy) {
		this.considerEthicPolicy = considerEthicPolicy;
	}
	/**
	 * @return the considerImplPolicy
	 */
	public Integer getConsiderImplPolicy() {
		return considerImplPolicy;
	}
	/**
	 * @param considerImplPolicy the considerImplPolicy to set
	 */
	public void setConsiderImplPolicy(Integer considerImplPolicy) {
		this.considerImplPolicy = considerImplPolicy;
	}
	/**
	 * @return the freeEstimate
	 */
	public Integer getFreeEstimate() {
		return freeEstimate;
	}
	/**
	 * @param freeEstimate the freeEstimate to set
	 */
	public void setFreeEstimate(Integer freeEstimate) {
		this.freeEstimate = freeEstimate;
	}
	/**
	 * @return the hasEthicsPolicy
	 */
	public Integer getHasEthicsPolicy() {
		return hasEthicsPolicy;
	}
	/**
	 * @param hasEthicsPolicy the hasEthicsPolicy to set
	 */
	public void setHasEthicsPolicy(Integer hasEthicsPolicy) {
		this.hasEthicsPolicy = hasEthicsPolicy;
	}
	/**
	 * @return the requireBadge
	 */
	public Integer getRequireBadge() {
		return requireBadge;
	}
	/**
	 * @param requireBadge the requireBadge to set
	 */
	public void setRequireBadge(Integer requireBadge) {
		this.requireBadge = requireBadge;
	}
	/**
	 * @return the requireUsDoc
	 */
	public Integer getRequireUsDoc() {
		return requireUsDoc;
	}
	/**
	 * @param requireUsDoc the requireUsDoc to set
	 */
	public void setRequireUsDoc(Integer requireUsDoc) {
		this.requireUsDoc = requireUsDoc;
	}
	/**
	 * @return the vendorId
	 */
	public int getVendorId() {
		return vendorId;
	}
	/**
	 * @param vendorId the vendorId to set
	 */
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	/**
	 * @return the warPeriod
	 */
	public Integer getWarPeriod() {
		return warPeriod;
	}
	/**
	 * @param warPeriod the warPeriod to set
	 */
	public void setWarPeriod(Integer warPeriod) {
		this.warPeriod = warPeriod;
	}
	/**
	 * @return the warrOffered
	 */
	public Integer getWarrOffered() {
		return warrOffered;
	}
	/**
	 * @param warrOffered the warrOffered to set
	 */
	public void setWarrOffered(Integer warrOffered) {
		this.warrOffered = warrOffered;
	}
	public String getWarrentyMessuare() {
		return warrentyMessuare;
	}
	public void setWarrentyMessuare(String warrentyMessuare) {
		this.warrentyMessuare = warrentyMessuare;
	}
	
	
}
