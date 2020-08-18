package com.servicelive.orderfulfillment.integration.domain;

import java.util.Calendar;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * User: Mustafa Motiwala
 * Date: Apr 9, 2010
 * Time: 4:14:23 PM
 */
public class Transaction {
    private Long id;
    private Long batchId;
    private String externalOrderNumber;
    private Long platformBuyerId;
    private Integer platformBuyerRoleId;
    private String buyerState;
    private String buyerName;
    private TransactionType type;
    private Long serviceOrderId;
    private String serviceLiveOrderId;
    private Calendar processedOn;
    private ServiceOrder serviceOrder;
	private Long buyerResourceId;
	private Calendar createdOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getExternalOrderNumber() {
        return externalOrderNumber;
    }

    public void setExternalOrderNumber(String externalOrderNumber) {
        this.externalOrderNumber = externalOrderNumber;
    }

    public Long getPlatformBuyerId() {
        return platformBuyerId;
    }

    public void setPlatformBuyerId(Long platformBuyerId) {
        this.platformBuyerId = platformBuyerId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Integer getPlatformBuyerRoleId() {
        return platformBuyerRoleId;
    }

    public void setPlatformBuyerRoleId(Integer platformBuyerRoleId) {
        this.platformBuyerRoleId = platformBuyerRoleId;
    }

    public String getBuyerState() {
        return buyerState;
    }

    public void setBuyerState(String buyerState) {
        this.buyerState = buyerState;
    }

    public Long getServiceOrderId() {
        return serviceOrderId;
    }

    public void setServiceOrderId(Long serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public String getServiceLiveOrderId() {
        return serviceLiveOrderId;
    }

    public void setServiceLiveOrderId(String serviceLiveOrderId) {
        this.serviceLiveOrderId = serviceLiveOrderId;
    }

    public Calendar getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(Calendar processedOn) {
        this.processedOn = processedOn;
    }

    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

	public void setBuyerResourceId(Long buyerResourceId) {
		this.buyerResourceId = buyerResourceId;
	}
	
	public Long getBuyerResourceId(){
		return this.buyerResourceId;
	}

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

	public void setCreatedOn(Calendar createdOn) {
		this.createdOn = createdOn;
	}

	public Calendar getCreatedOn() {
		return createdOn;
	}
}
