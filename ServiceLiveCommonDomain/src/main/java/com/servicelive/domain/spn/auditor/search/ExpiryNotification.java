package com.servicelive.domain.spn.auditor.search;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author 
 *
 */
@Entity
@Table ( name = "audit_cred_expiry_notification")
public class ExpiryNotification {

	
	private static final long serialVersionUID = 20090114L;
	
	@Id 
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="audit_cred_expiry_notification_id", unique=true, nullable= false)
	private Integer notificationId;

	@Column(name="vendor_id")
	private Integer vendorId;
	
	@Column(name="resource_id")
	private Integer resourceId;
	
	@Column(name="notification_type")
	private Integer notificationType;
	
	@Column(name="expiration_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationDate;
	
	@Column(name = "notification_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date notificationDate;
	
	@Column(name="spn_id")
	private Integer spnId;
	
	@Column(name="cred_id")
	private Integer credId;
	
	@Column(name="credential_name")
	private String credentialName;
	
	@Column(name="credential_ind")
	private Integer credentialInd;

	public Integer getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
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

	public Integer getSpnId() {
		return spnId;
	}

	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}

	public Integer getCredId() {
		return credId;
	}

	public void setCredId(Integer credId) {
		this.credId = credId;
	}

	public String getCredentialName() {
		return credentialName;
	}

	public void setCredentialName(String credentialName) {
		this.credentialName = credentialName;
	}

	public Integer getCredentialInd() {
		return credentialInd;
	}

	public void setCredentialInd(Integer credentialInd) {
		this.credentialInd = credentialInd;
	}
	

}
