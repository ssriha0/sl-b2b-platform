package com.servicelive.spn.buyer.campaign;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * 
 *
 */
public class SPNCreateCampaignApprovalModel
{
	private Boolean vehicleLiabilitySelected;
	private Boolean vehicleLiabilityVerified;
	private String vehicleLiabilityAmt;
	private Boolean workersCompensationSelected;
	private Boolean workersCompensationVerified;
	private Boolean commercialGeneralLiabilitySelected;
	private Boolean commercialGeneralLiabilityVerified;
	private String commercialGeneralLiabilityAmt;
	private String minimumCompletedServiceOrders;
	private List<String> viewServicesAndSkills;
	private List<Integer> selectedMainServices = new ArrayList<Integer>();	
	private List<Integer> selectedSkills = new ArrayList<Integer>();
	private List<Integer> selectedLanguages = new ArrayList<Integer>();
	
	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getVehicleLiabilitySelected() {
		return vehicleLiabilitySelected;
	}
	/**
	 * 
	 * @param vehicleLiabilitySelected
	 */
	public void setVehicleLiabilitySelected(Boolean vehicleLiabilitySelected) {
		this.vehicleLiabilitySelected = vehicleLiabilitySelected;
	}
	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getVehicleLiabilityVerified() {
		return vehicleLiabilityVerified;
	}
	/**
	 * 
	 * @param vehicleLiabilityVerified
	 */
	public void setVehicleLiabilityVerified(Boolean vehicleLiabilityVerified) {
		this.vehicleLiabilityVerified = vehicleLiabilityVerified;
	}
	/**
	 * 
	 * @return String
	 */
	public String getVehicleLiabilityAmt() {
		return vehicleLiabilityAmt;
	}
	/**
	 * 
	 * @param vehicleLiabilityAmt
	 */
	public void setVehicleLiabilityAmt(String vehicleLiabilityAmt) {
		this.vehicleLiabilityAmt = vehicleLiabilityAmt;
	}
	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getWorkersCompensationSelected() {
		return workersCompensationSelected;
	}
	/**
	 * 
	 * @param workersCompensationSelected
	 */
	public void setWorkersCompensationSelected(Boolean workersCompensationSelected) {
		this.workersCompensationSelected = workersCompensationSelected;
	}
	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getWorkersCompensationVerified() {
		return workersCompensationVerified;
	}
	/**
	 * 
	 * @param workersCompensationVerified
	 */
	public void setWorkersCompensationVerified(Boolean workersCompensationVerified) {
		this.workersCompensationVerified = workersCompensationVerified;
	}
	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getCommercialGeneralLiabilitySelected() {
		return commercialGeneralLiabilitySelected;
	}
	/**
	 * 
	 * @param commercialGeneralLiabilitySelected
	 */
	public void setCommercialGeneralLiabilitySelected(
			Boolean commercialGeneralLiabilitySelected) {
		this.commercialGeneralLiabilitySelected = commercialGeneralLiabilitySelected;
	}
	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getCommercialGeneralLiabilityVerified() {
		return commercialGeneralLiabilityVerified;
	}
	/**
	 * 
	 * @param commercialGeneralLiabilityVerified
	 */
	public void setCommercialGeneralLiabilityVerified(
			Boolean commercialGeneralLiabilityVerified) {
		this.commercialGeneralLiabilityVerified = commercialGeneralLiabilityVerified;
	}
	/**
	 * 
	 * @return String
	 */
	public String getCommercialGeneralLiabilityAmt() {
		return commercialGeneralLiabilityAmt;
	}
	/**
	 * 
	 * @param commercialGeneralLiabilityAmt
	 */
	public void setCommercialGeneralLiabilityAmt(
			String commercialGeneralLiabilityAmt) {
		this.commercialGeneralLiabilityAmt = commercialGeneralLiabilityAmt;
	}
	/**
	 * 
	 * @return String
	 */
	public String getMinimumCompletedServiceOrders() {
		return minimumCompletedServiceOrders;
	}
	/**
	 * 
	 * @param minimumCompletedServiceOrders
	 */
	public void setMinimumCompletedServiceOrders(
			String minimumCompletedServiceOrders) {
		this.minimumCompletedServiceOrders = minimumCompletedServiceOrders;
	}
	/**
	 * 
	 * @return List 
	 */
	public List<String> getViewServicesAndSkills() {
		return viewServicesAndSkills;
	}
	/**
	 * 
	 * @param viewServicesAndSkills
	 */
	public void setViewServicesAndSkills(List<String> viewServicesAndSkills) {
		this.viewServicesAndSkills = viewServicesAndSkills;
	}
	/**
	 * 
	 * @return List
	 */
	public List<Integer> getSelectedMainServices() {
		return selectedMainServices;
	}
	/**
	 * 
	 * @param selectedMainServices
	 */
	public void setSelectedMainServices(List<Integer> selectedMainServices) {
		this.selectedMainServices = selectedMainServices;
	}
	/**
	 * 
	 * @return List
	 */
	public List<Integer> getSelectedSkills() {
		return selectedSkills;
	}
	/**
	 * 
	 * @param selectedSkills
	 */
	public void setSelectedSkills(List<Integer> selectedSkills) {
		this.selectedSkills = selectedSkills;
	}
	/**
	 * 
	 * @return List
	 */
	public List<Integer> getSelectedLanguages() {
		return selectedLanguages;
	}
	/**
	 * 
	 * @param selectedLanguages
	 */
	public void setSelectedLanguages(List<Integer> selectedLanguages) {
		this.selectedLanguages = selectedLanguages;
	}
}