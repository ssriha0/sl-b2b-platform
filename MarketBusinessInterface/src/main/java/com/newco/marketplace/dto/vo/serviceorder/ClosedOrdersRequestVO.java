package com.newco.marketplace.dto.vo.serviceorder;

import java.util.Date;

import com.newco.marketplace.utils.DateUtils;

/**
 * This class would act as a VO class for retrieving closed SO Details for provider.
 * 
 * @author Infosys
 * @version 1.0
 */

public class ClosedOrdersRequestVO {

	private String providerId;
	private String serviceLocZipcode;
	private Integer completedIn;
	private Integer noOfOrders;
	private Date startDate;
	
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public String getServiceLocZipcode() {
		return serviceLocZipcode;
	}
	public void setServiceLocZipcode(String serviceLocZipcode) {
		this.serviceLocZipcode = serviceLocZipcode;
	}
	public Integer getCompletedIn() {
		return completedIn;
	}
	public void setCompletedIn(Integer completedIn) {
		this.completedIn = completedIn;
	}
	public Integer getNoOfOrders() {
		return noOfOrders;
	}
	public void setNoOfOrders(Integer noOfOrders) {
		this.noOfOrders = noOfOrders;
	}

}
