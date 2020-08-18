/**
 * 
 */
package com.newco.marketplace.vo.provider;

import java.util.Date;

/**
 * @author MTedder
 *
 */
public class BackgroundCheckVO extends BaseVO {
    /**
	 * 
	 */ 
	private static final long serialVersionUID = -3360674995615002057L;
	private String lastName = null;
    private String firstName = null;
    private String title = null;
    private String phoneNumber = null;
    private Integer vendorContactId = new Integer(-1);
    private Integer resourceStateId = new Integer(-1);
    private Integer backgroundCheckStateId = new Integer(-1);
    private Boolean owner = null;
    private String resourceState = null;
    private String backgroundCheckState = null;
    private String wfEntity = null;
    private Integer resourceId = new Integer(-1);
    private String editURL = null;
    private String fullName = null;
    private String ownerInd = null;
    private String marketStatus = null;
    private int primariInd=0;
    private String email = null;//MTedder
    private String emailAlt = null;//MTedder 
    private Integer contactId = 0;//MTedder 
    private Integer backgroundConfirmInd = 0;//MTedder

    private String userName = null;     //BNatara - Added user name
    private String provUserName = null;  //BNatara - Added provider user name
    private int bgCheckStatus = 0; //BNatara - Added to get the Back Ground Status
    
    
    
    private String backgroundCheckStatus;
    private Date certificationDate;
    private Date recertificationDate;
    private Integer bcCheckId;
    
    //R11_1
	//Jira SL-20434
    private String encryptedResourceIdSsn;
    
	 
	public String getEncryptedResourceIdSsn() {
		return encryptedResourceIdSsn;
	}
	public void setEncryptedResourceIdSsn(String encryptedResourceIdSsn) {
		this.encryptedResourceIdSsn = encryptedResourceIdSsn;
	}
    
    
  //R11_0 To set fourth parameter in Team Background Check Email
    private String recertificationInd;
    
	public int getBgCheckStatus() {
		return bgCheckStatus;
	}
	public void setBgCheckStatus(int bgCheckStatus) {
		this.bgCheckStatus = bgCheckStatus;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @return the vendorContactId
	 */
	public Integer getVendorContactId() {
		return vendorContactId;
	}
	/**
	 * @param vendorContactId the vendorContactId to set
	 */
	public void setVendorContactId(Integer vendorContactId) {
		this.vendorContactId = vendorContactId;
	}
	/**
	 * @return the resourceStateId
	 */
	public Integer getResourceStateId() {
		return resourceStateId;
	}
	/**
	 * @param resourceStateId the resourceStateId to set
	 */
	public void setResourceStateId(Integer resourceStateId) {
		this.resourceStateId = resourceStateId;
	}
	/**
	 * @return the backgroundCheckStateId
	 */
	public Integer getBackgroundCheckStateId() {
		return backgroundCheckStateId;
	}
	/**
	 * @param backgroundCheckStateId the backgroundCheckStateId to set
	 */
	public void setBackgroundCheckStateId(Integer backgroundCheckStateId) {
		this.backgroundCheckStateId = backgroundCheckStateId;
	}
	/**
	 * @return the owner
	 */
	public Boolean getOwner() {
		return owner;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(Boolean owner) {
		this.owner = owner;
	}
	/**
	 * @return the resourceState
	 */
	public String getResourceState() {
		return resourceState;
	}
	/**
	 * @param resourceState the resourceState to set
	 */
	public void setResourceState(String resourceState) {
		this.resourceState = resourceState;
	}
	/**
	 * @return the backgroundCheckState
	 */
	public String getBackgroundCheckState() {
		return backgroundCheckState;
	}
	/**
	 * @param backgroundCheckState the backgroundCheckState to set
	 */
	public void setBackgroundCheckState(String backgroundCheckState) {
		this.backgroundCheckState = backgroundCheckState;
	}
	/**
	 * @return the wfEntity
	 */
	public String getWfEntity() {
		return wfEntity;
	}
	/**
	 * @param wfEntity the wfEntity to set
	 */
	public void setWfEntity(String wfEntity) {
		this.wfEntity = wfEntity;
	}
	/**
	 * @return the resourceId
	 */
	public Integer getResourceId() {
		return resourceId;
	}
	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	/**
	 * @return the editURL
	 */
	public String getEditURL() {
		return editURL;
	}
	/**
	 * @param editURL the editURL to set
	 */
	public void setEditURL(String editURL) {
		this.editURL = editURL;
	}
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * @return the ownerInd
	 */
	public String getOwnerInd() {
		return ownerInd;
	}
	/**
	 * @param ownerInd the ownerInd to set
	 */
	public void setOwnerInd(String ownerInd) {
		this.ownerInd = ownerInd;
	}
	/**
	 * @return the marketStatus
	 */
	public String getMarketStatus() {
		return marketStatus;
	}
	/**
	 * @param marketStatus the marketStatus to set
	 */
	public void setMarketStatus(String marketStatus) {
		this.marketStatus = marketStatus;
	}
	/**
	 * @return the primariInd
	 */
	public int getPrimariInd() {
		return primariInd;
	}
	/**
	 * @param primariInd the primariInd to set
	 */
	public void setPrimariInd(int primariInd) {
		this.primariInd = primariInd;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the emailAlt
	 */
	public String getEmailAlt() {
		return emailAlt;
	}
	/**
	 * @param emailAlt the emailAlt to set
	 */
	public void setEmailAlt(String emailAlt) {
		this.emailAlt = emailAlt;
	}

	/**
	 * @return the contactId
	 */
	public Integer getContactId() {
		return contactId;
	}
	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	/**
	 * @return the backgroundConfirmInd
	 */
	public Integer getBackgroundConfirmInd() {
		return backgroundConfirmInd;
	}
	/**
	 * @param backgroundConfirmInd the backgroundConfirmInd to set
	 */
	public void setBackgroundConfirmInd(Integer backgroundConfirmInd) {
		this.backgroundConfirmInd = backgroundConfirmInd;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProvUserName() {
		return provUserName;
	}
	public void setProvUserName(String provUserName) {
		this.provUserName = provUserName;
	}
	
	public Date getCertificationDate() {
		return certificationDate;
	}
	public void setCertificationDate(Date certificationDate) {
		this.certificationDate = certificationDate;
	}
	public Date getRecertificationDate() {
		return recertificationDate;
	}
	public void setRecertificationDate(Date recertificationDate) {
		this.recertificationDate = recertificationDate;
	}
	public Integer getBcCheckId() {
		return bcCheckId;
	}
	public void setBcCheckId(Integer bcCheckId) {
		this.bcCheckId = bcCheckId;
	}
	public String getBackgroundCheckStatus() {
		return backgroundCheckStatus;
	}
	public void setBackgroundCheckStatus(String backgroundCheckStatus) {
		this.backgroundCheckStatus = backgroundCheckStatus;
	}
	public String getRecertificationInd() {
		return recertificationInd;
	}
	public void setRecertificationInd(String recertificationInd) {
		this.recertificationInd = recertificationInd;
	}

}
