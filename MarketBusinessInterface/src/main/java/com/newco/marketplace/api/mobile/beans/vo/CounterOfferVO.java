/**
 * 
 */
package com.newco.marketplace.api.mobile.beans.vo;

import java.sql.Timestamp;

import com.newco.marketplace.api.beans.so.CounterOfferResources;
import com.newco.marketplace.api.beans.so.OfferReasonCodes;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
/**
 * $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/16
 * This is a VO class for storing vo information for the CounterOffer/WithdrawCounterOffer
 */
public class CounterOfferVO {

	private Integer roleId;
	private String soId;
	//private String groupId;
	private ServiceOrder so;
	//private String groupInd;
	private String serviceDateTime1;
	private String serviceDateTime2;
	private String spendLimit;
	private String offerExpirationDate;
	private CounterOfferResources resourceIds;
	private Integer providerResourceId;
	private String firmId;
	private OfferReasonCodes reasonCodes;
	
	private Timestamp serviceDate1;
	private String serviceTimeStart;
	private Timestamp serviceDate2;
	private String serviceTimeEnd;
	private Timestamp conditionalExpirationDate;
	private String conditionalExpirationTime;
	

	/**
	 * @return the soId
	 */
	public String getSoId() {
		return soId;
	}

	/**
	 * @param soId the soId to set
	 */
	public void setSoId(final String soId) {
		this.soId = soId;
	}

	/**
	 * @return the groupId
	 */
/*	public String getGroupId() {
		return groupId;
	}

	*//**
	 * @param groupId the groupId to set
	 *//*
	public void setGroupId(final String groupId) {
		this.groupId = groupId;
	}*/

	/**
	 * @return the so
	 */
	public ServiceOrder getSo() {
		return so;
	}

	/**
	 * @param so the so to set
	 */
	public void setSo(final ServiceOrder so) {
		this.so = so;
	}

	/**
	 * @return the groupInd
	 *//*
	public String getGroupInd() {
		return groupInd;
	}

	*//**
	 * @param groupInd the groupInd to set
	 *//*
	public void setGroupInd(final String groupInd) {
		this.groupInd = groupInd;
	}*/

	/**
	 * @return the serviceDateTime1
	 */
	public String getServiceDateTime1() {
		return serviceDateTime1;
	}

	/**
	 * @param serviceDateTime1 the serviceDateTime1 to set
	 */
	public void setServiceDateTime1(final String serviceDateTime1) {
		this.serviceDateTime1 = serviceDateTime1;
	}

	/**
	 * @return the serviceDateTime2
	 */
	public String getServiceDateTime2() {
		return serviceDateTime2;
	}

	/**
	 * @param serviceDateTime2 the serviceDateTime2 to set
	 */
	public void setServiceDateTime2(final String serviceDateTime2) {
		this.serviceDateTime2 = serviceDateTime2;
	}

	/**
	 * @return the spendLimit
	 */
	public String getSpendLimit() {
		return spendLimit;
	}

	/**
	 * @param spendLimit the spendLimit to set
	 */
	public void setSpendLimit(final String spendLimit) {
		this.spendLimit = spendLimit;
	}

	/**
	 * @return the offerExpirationDate
	 */
	public String getOfferExpirationDate() {
		return offerExpirationDate;
	}

	/**
	 * @param offerExpirationDate the offerExpirationDate to set
	 */
	public void setOfferExpirationDate(final String offerExpirationDate) {
		this.offerExpirationDate = offerExpirationDate;
	}

	/**
	 * @return the resourceIds
	 */
	public CounterOfferResources getResourceIds() {
		return resourceIds;
	}

	/**
	 * @param resourceIds the resourceIds to set
	 */
	public void setResourceIds(final CounterOfferResources resourceIds) {
		this.resourceIds = resourceIds;
	}

	/**
	 * @return the providerResourceId
	 */
	public Integer getProviderResourceId() {
		return providerResourceId;
	}

	/**
	 * @param providerResourceId the providerResourceId to set
	 */
	public void setProviderResourceId(final Integer providerResourceId) {
		this.providerResourceId = providerResourceId;
	}

	/**
	 * @return the serviceDate1
	 */
	public Timestamp getServiceDate1() {
		return serviceDate1;
	}

	/**
	 * @param serviceDate1 the serviceDate1 to set
	 */
	public void setServiceDate1(final Timestamp serviceDate1) {
		this.serviceDate1 = serviceDate1;
	}

	/**
	 * @return the serviceTimeStart
	 */
	public String getServiceTimeStart() {
		return serviceTimeStart;
	}

	/**
	 * @param serviceTimeStart the serviceTimeStart to set
	 */
	public void setServiceTimeStart(final String serviceTimeStart) {
		this.serviceTimeStart = serviceTimeStart;
	}

	/**
	 * @return the serviceDate2
	 */
	public Timestamp getServiceDate2() {
		return serviceDate2;
	}

	/**
	 * @param serviceDate2 the serviceDate2 to set
	 */
	public void setServiceDate2(final Timestamp serviceDate2) {
		this.serviceDate2 = serviceDate2;
	}

	/**
	 * @return the serviceTimeEnd
	 */
	public String getServiceTimeEnd() {
		return serviceTimeEnd;
	}

	/**
	 * @param serviceTimeEnd the serviceTimeEnd to set
	 */
	public void setServiceTimeEnd(final String serviceTimeEnd) {
		this.serviceTimeEnd = serviceTimeEnd;
	}

	/**
	 * @return the conditionalExpirationDate
	 */
	public Timestamp getConditionalExpirationDate() {
		return conditionalExpirationDate;
	}

	/**
	 * @param conditionalExpirationDate the conditionalExpirationDate to set
	 */
	public void setConditionalExpirationDate(final Timestamp conditionalExpirationDate) {
		this.conditionalExpirationDate = conditionalExpirationDate;
	}

	/**
	 * @return the conditionalExpirationTime
	 */
	public String getConditionalExpirationTime() {
		return conditionalExpirationTime;
	}

	/**
	 * @param conditionalExpirationTime the conditionalExpirationTime to set
	 */
	public void setConditionalExpirationTime(final String conditionalExpirationTime) {
		this.conditionalExpirationTime = conditionalExpirationTime;
	}

	/**
	 * @return the firmId
	 */
	public String getFirmId() {
		return firmId;
	}

	/**
	 * @param firmId the firmId to set
	 */
	public void setFirmId(final String firmId) {
		this.firmId = firmId;
	}

	/**
	 * @return the reasonCodes
	 */
	public OfferReasonCodes getReasonCodes() {
		return reasonCodes;
	}

	/**
	 * @param reasonCodes the reasonCodes to set
	 */
	public void setReasonCodes(final OfferReasonCodes reasonCodes) {
		this.reasonCodes = reasonCodes;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}


}
