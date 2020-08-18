package com.servicelive.wallet.alert.vo;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * Class ContactVO.
 */
public class ContactVO implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 132208403529277574L;

	/** contactId. */
	private long contactId;

	/** emailAddress. */
	private String emailAddress;

	/** firstName. */
	private String firstName;

	/** lastName. */
	private String lastName;
	
	/** primaryInd. */
	private int primaryInd;

	/**
	 * getContactId.
	 * 
	 * @return long
	 */
	public long getContactId() {

		return contactId;
	}

	/**
	 * getEmailAddress.
	 * 
	 * @return String
	 */
	public String getEmailAddress() {

		return emailAddress;
	}

	/**
	 * getFirstName.
	 * 
	 * @return String
	 */
	public String getFirstName() {

		return firstName;
	}

	/**
	 * getLastName.
	 * 
	 * @return String
	 */
	public String getLastName() {

		return lastName;
	}

	/**
	 * setContactId.
	 * 
	 * @param contactId 
	 * 
	 * @return void
	 */
	public void setContactId(long contactId) {

		this.contactId = contactId;
	}

	/**
	 * setEmailAddress.
	 * 
	 * @param emailAddress 
	 * 
	 * @return void
	 */
	public void setEmailAddress(String emailAddress) {

		this.emailAddress = emailAddress;
	}

	/**
	 * setFirstName.
	 * 
	 * @param firstName 
	 * 
	 * @return void
	 */
	public void setFirstName(String firstName) {

		this.firstName = firstName;
	}

	/**
	 * setLastName.
	 * 
	 * @param lastName 
	 * 
	 * @return void
	 */
	public void setLastName(String lastName) {

		this.lastName = lastName;
	}

	public int getPrimaryInd() {
		return primaryInd;
	}

	public void setPrimaryInd(int primaryInd) {
		this.primaryInd = primaryInd;
	}

}
