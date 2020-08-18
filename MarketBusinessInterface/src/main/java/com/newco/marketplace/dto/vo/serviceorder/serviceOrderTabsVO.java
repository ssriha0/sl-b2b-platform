package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Date;
import java.sql.Timestamp;

import com.sears.os.vo.SerializableBaseVO;

public class serviceOrderTabsVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8265572424690197394L;
	private String soStatus;
	private String buyerId;
	private Date today;
	private Integer soSubStatus;
	private String entityId;
	private String providerId;
	private Timestamp currentDate;
	private String currentTime;
	
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public String getSoStatus() {
		return soStatus;
	}
	public void setSoStatus(String soStatus) {
		this.soStatus = soStatus;
	}
	public Integer getSoSubStatus() {
		return soSubStatus;
	}
	public void setSoSubStatus(Integer soSubStatus) {
		this.soSubStatus = soSubStatus;
	}
	public Date getToday() {
		return today;
	}
	public void setToday(Date today) {
		this.today = today;
	}
	
	/**
	 * @return the currentDate
	 */
	public Timestamp getCurrentDate() {
		return currentDate;
	}
	/**
	 * @param currentDate the currentDate to set
	 */
	public void setCurrentDate(Timestamp currentDate) {
		this.currentDate = currentDate;
	}
	/**
	 * @return the currentTime
	 */
	public String getCurrentTime() {
		return currentTime;
	}
	/**
	 * @param currentTime the currentTime to set
	 */
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	

	
}
