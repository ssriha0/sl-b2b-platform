package com.newco.marketplace.persistence.vo.adminEmails;

import com.newco.marketplace.vo.MPBaseVO;

public class BackgroundTickleVo extends MPBaseVO {

	String companyName;
	String destinationEmail;
	String primaryContact;
	String contactId;
	String plusOneKey;
	String resourceId;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return companyName + "|" + destinationEmail+ "|" + primaryContact + "|" +contactId+ "|" +plusOneKey+ "|" +resourceId;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the destinationEmail
	 */
	public String getDestinationEmail() {
		return destinationEmail;
	}

	/**
	 * @param destinationEmail the destinationEmail to set
	 */
	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	/**
	 * @return the primaryContact
	 */
	public String getPrimaryContact() {
		return primaryContact;
	}

	/**
	 * @param primaryContact the primaryContact to set
	 */
	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}

	/**
	 * @return the contactId
	 */
	public String getContactId() {
		return contactId;
	}

	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	/**
	 * @return the plusOneKey
	 */
	public String getPlusOneKey() {
		return plusOneKey;
	}

	/**
	 * @param plusOneKey the plusOneKey to set
	 */
	public void setPlusOneKey(String plusOneKey) {
		this.plusOneKey = plusOneKey;
	}

	/**
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

}
