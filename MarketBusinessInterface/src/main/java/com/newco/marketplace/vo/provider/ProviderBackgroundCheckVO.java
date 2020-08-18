/*
** TeamMemberInformationForm.java  1.0     2007/06/06
*/
package com.newco.marketplace.vo.provider;

import java.sql.Date;

import com.sears.os.vo.SerializableBaseVO;

/**
 * Struts Form bean for Team Member actions
 * 
 * @version
 * @author blars04
 *
 */
public class ProviderBackgroundCheckVO extends SerializableBaseVO{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3637996615801301553L;
	
	private Integer resourceId;
	private Integer vendorId;
	private Date expirationDate;
	private String vendorEmail;
	private String resourceFirstName;
	private String resourceLastName;
	private String vendorFirstName;
	private String vendorLastName;
	private Integer inputMonth;
	private Integer inputYear;
	//R11.0
	private Integer spnId;
	private Date reVerficationDate;
	private Integer notificationType;
	private Integer backgroundCheckId;
	private Integer notificationId;
	private String spnName;
	private Integer spnIdCount;
	private String resourcePlusOneKey;
	
	//R11_1
	//Jira SL-20434
	private String ssnLastFour;
	
	public String getSsnLastFour() {
		return ssnLastFour;
	}
	public void setSsnLastFour(String ssnLastFour) {
		this.ssnLastFour = ssnLastFour;
	}
	public String getResourcePlusOneKey() {
		return resourcePlusOneKey;
	}
	public void setResourcePlusOneKey(String resourcePlusOneKey) {
		this.resourcePlusOneKey = resourcePlusOneKey;
	}
	public Integer getSpnIdCount() {
		return spnIdCount;
	}
	public void setSpnIdCount(Integer spnIdCount) {
		this.spnIdCount = spnIdCount;
	}
	public String getSpnName() {
		return spnName;
	}
	public void setSpnName(String spnName) {
		this.spnName = spnName;
	}
	public Integer getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}
	public Integer getSpnId() {
		return spnId;
	}
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	public Date getReVerficationDate() {
		return reVerficationDate;
	}
	public void setReVerficationDate(Date reVerficationDate) {
		this.reVerficationDate = reVerficationDate;
	}
	public Integer getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(Integer notificationType) {
		this.notificationType = notificationType;
	}
	public Integer getBackgroundCheckId() {
		return backgroundCheckId;
	}
	public void setBackgroundCheckId(Integer backgroundCheckId) {
		this.backgroundCheckId = backgroundCheckId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getVendorEmail() {
		return vendorEmail;
	}
	public void setVendorEmail(String vendorEmail) {
		this.vendorEmail = vendorEmail;
	}
	public String getResourceFirstName() {
		return resourceFirstName;
	}
	public void setResourceFirstName(String resourceFirstName) {
		this.resourceFirstName = resourceFirstName;
	}
	public String getResourceLastName() {
		return resourceLastName;
	}
	public void setResourceLastName(String resourceLastName) {
		this.resourceLastName = resourceLastName;
	}
	public String getVendorFirstName() {
		return vendorFirstName;
	}
	public void setVendorFirstName(String vendorFirstName) {
		this.vendorFirstName = vendorFirstName;
	}
	public String getVendorLastName() {
		return vendorLastName;
	}
	public void setVendorLastName(String vendorLastName) {
		this.vendorLastName = vendorLastName;
	}
	public Integer getInputMonth() {
		return inputMonth;
	}
	public void setInputMonth(Integer inputMonth) {
		this.inputMonth = inputMonth;
	}
	public Integer getInputYear() {
		return inputYear;
	}
	public void setInputYear(Integer inputYear) {
		this.inputYear = inputYear;
	}
	
}//end class