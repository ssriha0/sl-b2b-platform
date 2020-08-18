/**
 * 
 */
package com.servicelive.spn.common.detached;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hoza
 *
 */
public class CredentialsCriteriaVO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4317433894853677125L;
	
	private Integer spnId;
	private Boolean vehicleLiabilitySelected;
	private Boolean vehicleLiabilityVerified;
	private String vehicleLiabilityAmt;
	private Boolean workersCompensationSelected;
	private Boolean workersCompensationVerified;
	private Boolean commercialGeneralLiabilitySelected;
	private Boolean commercialGeneralLiabilityVerified;
	private String commercialGeneralLiabilityAmt;
	private List<Integer> selectedVendorCredTypes = new ArrayList<Integer>();
	private List<Integer> selectedVendorCredCategories = new ArrayList<Integer>();
	private List<Integer> selectedResCredTypes = new ArrayList<Integer>();
	private List<Integer> selectedResCredCategories = new ArrayList<Integer>();
	
	
	
	public Integer getSpnId() {
		return spnId;
	}
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	public Boolean getVehicleLiabilitySelected() {
		return vehicleLiabilitySelected;
	}
	public void setVehicleLiabilitySelected(Boolean vehicleLiabilitySelected) {
		this.vehicleLiabilitySelected = vehicleLiabilitySelected;
	}
	public Boolean getVehicleLiabilityVerified() {
		return vehicleLiabilityVerified;
	}
	public void setVehicleLiabilityVerified(Boolean vehicleLiabilityVerified) {
		this.vehicleLiabilityVerified = vehicleLiabilityVerified;
	}
	public String getVehicleLiabilityAmt() {
		return vehicleLiabilityAmt;
	}
	public void setVehicleLiabilityAmt(String vehicleLiabilityAmt) {
		this.vehicleLiabilityAmt = vehicleLiabilityAmt;
	}
	public Boolean getWorkersCompensationSelected() {
		return workersCompensationSelected;
	}
	public void setWorkersCompensationSelected(Boolean workersCompensationSelected) {
		this.workersCompensationSelected = workersCompensationSelected;
	}
	public Boolean getWorkersCompensationVerified() {
		return workersCompensationVerified;
	}
	public void setWorkersCompensationVerified(Boolean workersCompensationVerified) {
		this.workersCompensationVerified = workersCompensationVerified;
	}
	public Boolean getCommercialGeneralLiabilitySelected() {
		return commercialGeneralLiabilitySelected;
	}
	public void setCommercialGeneralLiabilitySelected(
			Boolean commercialGeneralLiabilitySelected) {
		this.commercialGeneralLiabilitySelected = commercialGeneralLiabilitySelected;
	}
	public Boolean getCommercialGeneralLiabilityVerified() {
		return commercialGeneralLiabilityVerified;
	}
	public void setCommercialGeneralLiabilityVerified(
			Boolean commercialGeneralLiabilityVerified) {
		this.commercialGeneralLiabilityVerified = commercialGeneralLiabilityVerified;
	}
	public String getCommercialGeneralLiabilityAmt() {
		return commercialGeneralLiabilityAmt;
	}
	public void setCommercialGeneralLiabilityAmt(
			String commercialGeneralLiabilityAmt) {
		this.commercialGeneralLiabilityAmt = commercialGeneralLiabilityAmt;
	}
	public List<Integer> getSelectedVendorCredTypes() {
		return selectedVendorCredTypes;
	}
	public void setSelectedVendorCredTypes(List<Integer> selectedVendorCredTypes) {
		this.selectedVendorCredTypes = selectedVendorCredTypes;
	}
	public List<Integer> getSelectedVendorCredCategories() {
		return selectedVendorCredCategories;
	}
	public void setSelectedVendorCredCategories(
			List<Integer> selectedVendorCredCategories) {
		this.selectedVendorCredCategories = selectedVendorCredCategories;
	}
	public List<Integer> getSelectedResCredTypes() {
		return selectedResCredTypes;
	}
	public void setSelectedResCredTypes(List<Integer> selectedResCredTypes) {
		this.selectedResCredTypes = selectedResCredTypes;
	}
	public List<Integer> getSelectedResCredCategories() {
		return selectedResCredCategories;
	}
	public void setSelectedResCredCategories(List<Integer> selectedResCredCategories) {
		this.selectedResCredCategories = selectedResCredCategories;
	}
	
	
	
	
	
	
	

}
