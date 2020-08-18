package com.servicelive.wallet.batch.ofac.vo;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class OFACProcessQueueVO.
 */
public class OFACProcessQueueVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6608011980160871443L;

	/** The apt no. */
	private String aptNo;

	/** The business name. */
	private String businessName;

	/** The city. */
	private String city;

	/** The contact number. */
	private String contactNumber;

	/** The created date. */
	private String createdDate;

	/** The email. */
	private String email;

	/** The entity id. */
	private int entityId;

	/** The first name. */
	private String firstName;

	/** The last name. */
	private String lastName;

	/** The sl account number. */
	private Long slAccountNumber;

	/** The state. */
	private String state;

	/** The street1. */
	private String street1;

	/** The street2. */
	private String street2;

	/** The Tax payer id. */
	private String TaxPayerId;

	/** The user. */
	private String user;

	/** The User id. */
	private int UserID;

	/** The user type. */
	private String userType;

	/** The V1 account. */
	private Long V1Account;

	/** The V2 account. */
	private Long V2Account;

	/** The Zip code. */
	private String ZipCode;

	/**
	 * Gets the apt no.
	 * 
	 * @return the apt no
	 */
	public String getAptNo() {

		return aptNo;
	}

	/**
	 * Gets the business name.
	 * 
	 * @return the business name
	 */
	public String getBusinessName() {

		return businessName;
	}

	/**
	 * Gets the city.
	 * 
	 * @return the city
	 */
	public String getCity() {

		return city;
	}

	/**
	 * Gets the contact number.
	 * 
	 * @return the contact number
	 */
	public String getContactNumber() {

		return contactNumber;
	}

	/**
	 * Gets the created date.
	 * 
	 * @return the created date
	 */
	public String getCreatedDate() {

		return createdDate;
	}

	/**
	 * Gets the delimited string.
	 * 
	 * @param ofacProcessQueueVO 
	 * @param delimiter 
	 * 
	 * @return the delimited string
	 */
	public String getDelimitedString(OFACProcessQueueVO ofacProcessQueueVO, String delimiter) {

		StringBuffer sb = new StringBuffer("");

		sb.append(ofacProcessQueueVO.getUserType());
		sb.append(delimiter);

		sb.append(ofacProcessQueueVO.getEntityId());
		sb.append(delimiter);

		sb.append(ofacProcessQueueVO.getTaxPayerId());
		sb.append(delimiter);

		sb.append(ofacProcessQueueVO.getUser());
		sb.append(delimiter);

		sb.append(ofacProcessQueueVO.getUserID());
		sb.append(delimiter);

		sb.append(ofacProcessQueueVO.getBusinessName());
		sb.append(delimiter);

		sb.append(ofacProcessQueueVO.getContactNumber());
		sb.append(delimiter);

		sb.append(ofacProcessQueueVO.getFirstName());
		sb.append(delimiter);

		sb.append(ofacProcessQueueVO.getLastName());
		sb.append(delimiter);

		sb.append(ofacProcessQueueVO.getEmail());
		sb.append(delimiter);

		sb.append(ofacProcessQueueVO.getStreet1());
		sb.append(delimiter);

		sb.append(ofacProcessQueueVO.getStreet2());
		sb.append(delimiter);

		sb.append(ofacProcessQueueVO.getCity());
		sb.append(delimiter);

		sb.append(ofacProcessQueueVO.getState());
		sb.append(delimiter);

		sb.append(ofacProcessQueueVO.getZipCode());
		sb.append(delimiter);

		sb.append(ofacProcessQueueVO.getAptNo());
		sb.append(delimiter);

		sb.append(ofacProcessQueueVO.getV1Account());
		sb.append(delimiter);

		sb.append(ofacProcessQueueVO.getV2Account());
		sb.append(delimiter);

		sb.append(ofacProcessQueueVO.getCreatedDate());
		sb.append(delimiter);

		return (sb.toString());
	} // getDelimitedString

	/**
	 * Gets the email.
	 * 
	 * @return the email
	 */
	public String getEmail() {

		return email;
	}

	/**
	 * Gets the entity id.
	 * 
	 * @return the entity id
	 */
	public int getEntityId() {

		return entityId;
	}

	/**
	 * Gets the first name.
	 * 
	 * @return the first name
	 */
	public String getFirstName() {

		return firstName;
	}

	/**
	 * Gets the last name.
	 * 
	 * @return the last name
	 */
	public String getLastName() {

		return lastName;
	}

	/**
	 * Gets the sl account number.
	 * 
	 * @return the sl account number
	 */
	public Long getSlAccountNumber() {

		return slAccountNumber;
	}

	/**
	 * Gets the state.
	 * 
	 * @return the state
	 */
	public String getState() {

		return state;
	}

	/**
	 * Gets the street1.
	 * 
	 * @return the street1
	 */
	public String getStreet1() {

		return street1;
	}

	/**
	 * Gets the street2.
	 * 
	 * @return the street2
	 */
	public String getStreet2() {

		return street2;
	}

	/**
	 * Gets the tax payer id.
	 * 
	 * @return the tax payer id
	 */
	public String getTaxPayerId() {

		return TaxPayerId;
	}

	/**
	 * Gets the user.
	 * 
	 * @return the user
	 */
	public String getUser() {

		return user;
	}

	/**
	 * Gets the user id.
	 * 
	 * @return the user id
	 */
	public int getUserID() {

		return UserID;
	}

	/**
	 * Gets the user type.
	 * 
	 * @return the user type
	 */
	public String getUserType() {

		return userType;
	}

	/**
	 * Gets the v1 account.
	 * 
	 * @return the v1 account
	 */
	public Long getV1Account() {

		return V1Account;
	}

	/**
	 * Gets the v2 account.
	 * 
	 * @return the v2 account
	 */
	public Long getV2Account() {

		return V2Account;
	}

	/**
	 * Gets the zip code.
	 * 
	 * @return the zip code
	 */
	public String getZipCode() {

		return ZipCode;
	}

	/**
	 * Sets the apt no.
	 * 
	 * @param aptNo the new apt no
	 */
	public void setAptNo(String aptNo) {

		this.aptNo = aptNo;
	}

	/**
	 * Sets the business name.
	 * 
	 * @param businessName the new business name
	 */
	public void setBusinessName(String businessName) {

		this.businessName = businessName;
	}

	/**
	 * Sets the city.
	 * 
	 * @param city the new city
	 */
	public void setCity(String city) {

		this.city = city;
	}

	/**
	 * Sets the contact number.
	 * 
	 * @param contactNumber the new contact number
	 */
	public void setContactNumber(String contactNumber) {

		this.contactNumber = contactNumber;
	}

	/**
	 * Sets the created date.
	 * 
	 * @param createdDate the new created date
	 */
	public void setCreatedDate(String createdDate) {

		this.createdDate = createdDate;
	}

	/**
	 * Sets the email.
	 * 
	 * @param email the new email
	 */
	public void setEmail(String email) {

		this.email = email;
	}

	/**
	 * Sets the entity id.
	 * 
	 * @param entityId the new entity id
	 */
	public void setEntityId(int entityId) {

		this.entityId = entityId;
	}

	/**
	 * Sets the first name.
	 * 
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {

		this.firstName = firstName;
	}

	/**
	 * Sets the last name.
	 * 
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {

		this.lastName = lastName;
	}

	/**
	 * Sets the sl account number.
	 * 
	 * @param slAccountNumber the new sl account number
	 */
	public void setSlAccountNumber(Long slAccountNumber) {

		this.slAccountNumber = slAccountNumber;
	}

	/**
	 * Sets the state.
	 * 
	 * @param state the new state
	 */
	public void setState(String state) {

		this.state = state;
	}

	/**
	 * Sets the street1.
	 * 
	 * @param street1 the new street1
	 */
	public void setStreet1(String street1) {

		this.street1 = street1;
	}

	/**
	 * Sets the street2.
	 * 
	 * @param street2 the new street2
	 */
	public void setStreet2(String street2) {

		this.street2 = street2;
	}

	/**
	 * Sets the tax payer id.
	 * 
	 * @param taxPayerId the new tax payer id
	 */
	public void setTaxPayerId(String taxPayerId) {

		this.TaxPayerId = taxPayerId;
	}

	/**
	 * Sets the user.
	 * 
	 * @param user the new user
	 */
	public void setUser(String user) {

		this.user = user;
	}

	/**
	 * Sets the user id.
	 * 
	 * @param userID the new user id
	 */
	public void setUserID(int userID) {

		UserID = userID;
	}

	/**
	 * Sets the user type.
	 * 
	 * @param userType the new user type
	 */
	public void setUserType(String userType) {

		this.userType = userType;
	}

	/**
	 * Sets the v1 account.
	 * 
	 * @param account the new v1 account
	 */
	public void setV1Account(Long account) {

		V1Account = account;
	}

	/**
	 * Sets the v2 account.
	 * 
	 * @param account the new v2 account
	 */
	public void setV2Account(Long account) {

		V2Account = account;
	}

	/**
	 * Sets the zip code.
	 * 
	 * @param zipCode the new zip code
	 */
	public void setZipCode(String zipCode) {

		ZipCode = zipCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		return (getDelimitedString(this, "|"));
	}

}
