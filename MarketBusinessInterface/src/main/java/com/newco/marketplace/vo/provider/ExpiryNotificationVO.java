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
public class ExpiryNotificationVO extends SerializableBaseVO{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3637996615801301553L;
	
	private Integer documentId;
	private Integer vendorId;
	private Integer resourceId;
	private Integer notificationType;
	private Date expirationDate;
	private Date notificationDate;
	private Integer credentialType;
	private String credentialName;
	private Integer credentialIndicator;
	
	public Integer getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	
	public Integer getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(Integer notificationType) {
		this.notificationType = notificationType;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public Date getNotificationDate() {
		return notificationDate;
	}
	public void setNotificationDate(Date notificationDate) {
		this.notificationDate = notificationDate;
	}
	
	public Integer getCredentialType() {
		return credentialType;
	}
	public void setCredentialType(Integer credentialType) {
		this.credentialType = credentialType;
	}
	public String getCredentialName() {
		return credentialName;
	}
	public void setCredentialName(String credentialName) {
		this.credentialName = credentialName;
	}
	public Integer getCredentialIndicator() {
		return credentialIndicator;
	}
	public void setCredentialIndicator(Integer credentialIndicator) {
		this.credentialIndicator = credentialIndicator;
	}
	
	
}//end class