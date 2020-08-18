package com.newco.marketplace.utils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author svanloon
 *
 */
public class SharedSecret implements Serializable {
	private static final long serialVersionUID = 20100209L;

	private String userName;
	private Integer buyerId;
	private Date createdDate;
	private String ipAddress;
	private String encryptedPassword;

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the buyerId
	 */
	public Integer getBuyerId() {
		return buyerId;
	}
	/**
	 * @param buyerId the buyerId to set
	 */
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean validate(RequestInfo requestInfo) {
		String ipAddress = requestInfo.getIpAddress();
		Integer requestTimeOutSeconds = requestInfo.getRequestTimeOutSeconds();
		if(!this.getIpAddress().equals(ipAddress)) {
			return false;
		}
		Calendar c = Calendar.getInstance();
		c.add(Calendar.SECOND, -1*requestTimeOutSeconds);
		Date expirationTime = c.getTime();
		if(this.getCreatedDate().before(expirationTime)) {
			return false;
		}
		return true;

	}
	/**
	 * @return the encryptedPassword
	 */
	public String getEncryptedPassword() {
		return encryptedPassword;
	}
	/**
	 * @param encryptedPassword the encryptedPassword to set
	 */
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
}
