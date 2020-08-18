package com.newco.marketplace.api.beans.login;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("buyer")
public class SimpleBuyer {
	
	@XStreamAlias("firstName")
	@XStreamAsAttribute()
	private String firstName;
		
	@XStreamAlias("lastName")
	@XStreamAsAttribute()
	private String lastName;
	
	@XStreamAlias("email")
	@XStreamAsAttribute()
	private String email;
	
	
	@XStreamAlias("globalBuyerId")
	@XStreamAsAttribute()
	private String globalBuyerId;
	
	@XStreamAlias("buyerResourceId")
	@XStreamAsAttribute()
	private String buyerResourceId;
	
	@XStreamAlias("profilePicUuid")
	@XStreamAsAttribute()
	private String profilePicUuid;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGlobalBuyerId() {
		return globalBuyerId;
	}

	public void setGlobalBuyerId(String globalBuyerId) {
		this.globalBuyerId = globalBuyerId;
	}

	public String getBuyerResourceId() {
		return buyerResourceId;
	}

	public void setBuyerResourceId(String buyerResourceId) {
		this.buyerResourceId = buyerResourceId;
	}

	public String getProfilePicUuid() {
		return profilePicUuid;
	}

	public void setProfilePicUuid(String profilePicUuid) {
		this.profilePicUuid = profilePicUuid;
	}

}
