package com.newco.marketplace.web.dto.simple;

import java.util.List;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.dto.SOWBaseTabDTO;

public class CreateServiceOrderReviewDTO extends SOWBaseTabDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1027993732167944784L;
	private String orderNumber;
	private String taskName;
	private String serviceDates;
	private String serviceTime;
	private String totalCost;
	private String total;
	private String postingFee;
	private String primaryPhone;
	private Integer numProvidersSelected;
	private String location;
	private double availableBalance;
	private double currentBalance;
	// Table/Grid of Provider Results
	List providers;
	private Integer soStatusId;
	/**
	 * @return the providers
	 */
	public List getProviders() {
		return providers;
	}

	/**
	 * @param providers the providers to set
	 */
	public void setProviders(List providers) {
		this.providers = providers;
	}

	/**
	 * @return the primaryPhone
	 */
	public String getPrimaryPhone() {
		return primaryPhone;
	}

	/**
	 * @param primaryPhone the primaryPhone to set
	 */
	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	/**
	 * @return the serviceDates
	 */
	public String getServiceDates() {
		return serviceDates;
	}

	/**
	 * @param serviceDates the serviceDates to set
	 */
	public void setServiceDates(String serviceDates) {
		this.serviceDates = serviceDates;
	}

	/**
	 * @return the serviceTime
	 */
	public String getServiceTime() {
		return serviceTime;
	}

	/**
	 * @param serviceTime the serviceTime to set
	 */
	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	/**
	 * @return the totalCost
	 */
	public String getTotalCost() {
		return totalCost;
	}

	/**
	 * @param totalCost the totalCost to set
	 */
	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}

	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * @return the orderNumber
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Override
	public String getTabIdentifier() {
		return OrderConstants.SSO_ORDER_REVIEW_DTO;
	}

	@Override
	public void validate() {

		if (numProvidersSelected.intValue() == 0) {
			clearAllWarnings();
			addWarning(getTheResourceBundle().getString("Provider"), 
					getTheResourceBundle().getString("Selected_providers_validation"), OrderConstants.SOW_TAB_WARNING);
		}
	}

	/**
	 * @return the postingFee
	 */
	public String getPostingFee() {
		return postingFee;
	}

	/**
	 * @param postingFee the postingFee to set
	 */
	public void setPostingFee(String postingFee) {
		this.postingFee = postingFee;
	}

	/**
	 * @return the numProvidersSelected
	 */
	public Integer getNumProvidersSelected() {
		return numProvidersSelected;
	}

	/**
	 * @param numProvidersSelected the numProvidersSelected to set
	 */
	public void setNumProvidersSelected(Integer numProvidersSelected) {
		this.numProvidersSelected = numProvidersSelected;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the availableBalance
	 */
	public double getAvailableBalance() {
		return availableBalance;
	}

	/**
	 * @param availableBalance the availableBalance to set
	 */
	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}

	/**
	 * @return the currentBalance
	 */
	public double getCurrentBalance() {
		return currentBalance;
	}

	/**
	 * @param currentBalance the currentBalance to set
	 */
	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public Integer getSoStatusId() {
		return soStatusId;
	}

	public void setSoStatusId(Integer soStatusId) {
		this.soStatusId = soStatusId;
	}


}
