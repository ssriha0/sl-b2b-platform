package com.newco.marketplace.dto.vo.so.order;

import java.sql.Timestamp;

import com.sears.os.vo.SerializableBaseVO;

public class SoCancelVO extends SerializableBaseVO{
	
	private static final long serialVersionUID = 6558490507956928210L;
	private String id;
	private Integer status;
	private String primaryStatus;
	private Double spendLimitLabor;
	private Double spendLimitParts;
	private Timestamp serviceDate1;
	private Timestamp serviceDate2;
	private String serviceTimeStart;
	private String serviceTimeEnd;
	private Integer serviceDateTypeId;
	private String serviceLocationTimeZone;
	private Double totalSpendLimit;
	private String soPricing;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPrimaryStatus() {
		return primaryStatus;
	}
	public void setPrimaryStatus(String primaryStatus) {
		this.primaryStatus = primaryStatus;
	}
	public Double getSpendLimitLabor() {
		return spendLimitLabor;
	}
	public void setSpendLimitLabor(Double spendLimitLabor) {
		this.spendLimitLabor = spendLimitLabor;
	}
	public Double getSpendLimitParts() {
		return spendLimitParts;
	}
	public void setSpendLimitParts(Double spendLimitParts) {
		this.spendLimitParts = spendLimitParts;
	}
	public Timestamp getServiceDate1() {
		return serviceDate1;
	}
	public void setServiceDate1(Timestamp serviceDate1) {
		this.serviceDate1 = serviceDate1;
	}
	public Timestamp getServiceDate2() {
		return serviceDate2;
	}
	public void setServiceDate2(Timestamp serviceDate2) {
		this.serviceDate2 = serviceDate2;
	}
	public String getServiceTimeStart() {
		return serviceTimeStart;
	}
	public void setServiceTimeStart(String serviceTimeStart) {
		this.serviceTimeStart = serviceTimeStart;
	}
	public String getServiceTimeEnd() {
		return serviceTimeEnd;
	}
	public void setServiceTimeEnd(String serviceTimeEnd) {
		this.serviceTimeEnd = serviceTimeEnd;
	}
	public Integer getServiceDateTypeId() {
		return serviceDateTypeId;
	}
	public void setServiceDateTypeId(Integer serviceDateTypeId) {
		this.serviceDateTypeId = serviceDateTypeId;
	}
	public String getServiceLocationTimeZone() {
		return serviceLocationTimeZone;
	}
	public void setServiceLocationTimeZone(String serviceLocationTimeZone) {
		this.serviceLocationTimeZone = serviceLocationTimeZone;
	}
	public Double getTotalSpendLimit() {
		Double total = 0.0;
		if (this.getSpendLimitLabor() != null)
			total += this.getSpendLimitLabor();
		if (this.getSpendLimitParts() != null)
			total += this.getSpendLimitParts();
		return total;
	}
	public void setTotalSpendLimit(Double totalSpendLimit) {
		this.totalSpendLimit = totalSpendLimit;
	}
	public String getSoPricing() {
		return soPricing;
	}
	public void setSoPricing(String soPricing) {
		this.soPricing = soPricing;
	}
}
	
	
