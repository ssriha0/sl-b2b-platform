package com.servicelive.domain.userprofile;

import java.io.Serializable;

import com.servicelive.domain.buyer.Buyer;

/**
 * @author hoza
 * 
 */
public class SPNUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8138795931791386644L;
	private User userDetails;
	private Buyer buyer;

	/**
	 * @return the userDetails
	 */
	public User getUserDetails() {
		return userDetails;
	}

	/**
	 * @param userDetails
	 *            the userDetails to set
	 */
	public void setUserDetails(User userDetails) {
		this.userDetails = userDetails;
	}

	/**
	 * @return the buyer
	 */
	public Buyer getBuyer() {
		return buyer;
	}

	/**
	 * @param buyer
	 *            the buyer to set
	 */
	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

}
