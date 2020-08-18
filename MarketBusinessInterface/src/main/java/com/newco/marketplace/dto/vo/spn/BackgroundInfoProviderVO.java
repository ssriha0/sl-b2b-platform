/**
 * 
 */
package com.newco.marketplace.dto.vo.spn;

import java.io.Serializable;

import java.util.Date;


//SL-19387
//Value Object class for fetching Background Check details
public class BackgroundInfoProviderVO implements Serializable {

	private static final long serialVersionUID = 20090317;
	
	
	private Integer resourceId;
	private String vendorBusinessName;
	private Integer vendorId;
	private Integer criteriaBg;
	private String providerFirstName;
	private String providerLastName;
	private String backgroundState;
	private String overAll;
	private String driving;
	private String criminal;
	private String civil;
	private Date verificationDate;
	private Date reverificationDate;
	private Date notificationDateThirty;
	private Date notificationDateSeven;
	private Date notificationDateZero;
	private String notificationType;
	private String expiresIn;
	private String recertificationStatus;
	
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getVendorBusinessName() {
		return vendorBusinessName;
	}
	public void setVendorBusinessName(String vendorBusinessName) {
		this.vendorBusinessName = vendorBusinessName;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getProviderFirstName() {
		return providerFirstName;
	}
	public void setProviderFirstName(String providerFirstName) {
		this.providerFirstName = providerFirstName;
	}
	public String getProviderLastName() {
		return providerLastName;
	}
	public void setProviderLastName(String providerLastName) {
		this.providerLastName = providerLastName;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getBackgroundState() {
		return backgroundState;
	}
	public void setBackgroundState(String backgroundState) {
		this.backgroundState = backgroundState;
	}
	public String getOverAll() {
		return overAll;
	}
	public void setOverAll(String overAll) {
		this.overAll = overAll;
	}
	public String getDriving() {
		return driving;
	}
	public void setDriving(String driving) {
		this.driving = driving;
	}
	public String getCriminal() {
		return criminal;
	}
	public void setCriminal(String criminal) {
		this.criminal = criminal;
	}
	public String getCivil() {
		return civil;
	}
	public void setCivil(String civil) {
		this.civil = civil;
	}
	public Date getVerificationDate() {
		return verificationDate;
	}
	public void setVerificationDate(Date verificationDate) {
		this.verificationDate = verificationDate;
	}
	public Date getReverificationDate() {
		return reverificationDate;
	}
	public void setReverificationDate(Date reverificationDate) {
		this.reverificationDate = reverificationDate;
	}

	public Date getNotificationDateThirty() {
		return notificationDateThirty;
	}
	public void setNotificationDateThirty(Date notificationDateThirty) {
		this.notificationDateThirty = notificationDateThirty;
	}
	public Date getNotificationDateSeven() {
		return notificationDateSeven;
	}
	public void setNotificationDateSeven(Date notificationDateSeven) {
		this.notificationDateSeven = notificationDateSeven;
	}
	public Date getNotificationDateZero() {
		return notificationDateZero;
	}
	public void setNotificationDateZero(Date notificationDateZero) {
		this.notificationDateZero = notificationDateZero;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public String getRecertificationStatus() {
		return recertificationStatus;
	}
	public void setRecertificationStatus(String recertificationStatus) {
		this.recertificationStatus = recertificationStatus;
	}
	public Integer getCriteriaBg() {
		return criteriaBg;
	}
	public void setCriteriaBg(Integer criteriaBg) {
		this.criteriaBg = criteriaBg;
	}
	
	
}
