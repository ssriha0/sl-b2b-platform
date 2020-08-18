package com.newco.marketplace.dto.vo.buyersearch;

import java.sql.Timestamp;

import com.newco.marketplace.vo.MPBaseVO;

public class AgentVisibilityWidgetVO extends MPBaseVO {

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getStrSoId();
	}
	
	private String strProviderCount;
	private String strFirmProviderCount;
	private Timestamp serviceStartDate;
	private Timestamp serviceEndDate;
	private String serviceTimeStart;
	private String serviceTimeEnd;
	private String strSoId;
	private String serviceLocationTimezone;
	private String color;
	private String percentage;
	private Integer buyerId;
	private boolean buyerInd;
	
	public boolean isBuyerInd() {
		return buyerInd;
	}
	public void setBuyerInd(boolean buyerInd) {
		this.buyerInd = buyerInd;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public String getStrProviderCount() {
		return strProviderCount;
	}
	public void setStrProviderCount(String strProviderCount) {
		this.strProviderCount = strProviderCount;
	}
	public String getStrFirmProviderCount() {
		return strFirmProviderCount;
	}
	public void setStrFirmProviderCount(String strFirmProviderCount) {
		this.strFirmProviderCount = strFirmProviderCount;
	}
	public Timestamp getServiceStartDate() {
		return serviceStartDate;
	}
	public void setServiceStartDate(Timestamp serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}
	public Timestamp getServiceEndDate() {
		return serviceEndDate;
	}
	public void setServiceEndDate(Timestamp serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
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
	public String getStrSoId() {
		return strSoId;
	}
	public void setStrSoId(String strSoId) {
		this.strSoId = strSoId;
	}
	public String getServiceLocationTimezone() {
		return serviceLocationTimezone;
	}
	public void setServiceLocationTimezone(String serviceLocationTimezone) {
		this.serviceLocationTimezone = serviceLocationTimezone;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	
}
