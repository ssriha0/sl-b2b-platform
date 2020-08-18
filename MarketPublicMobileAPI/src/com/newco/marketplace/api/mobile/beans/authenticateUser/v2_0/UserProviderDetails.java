package com.newco.marketplace.api.mobile.beans.authenticateUser.v2_0;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XmlRootElement(name = "userDetails")
@XStreamAlias("userDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserProviderDetails {

	private static final long serialVersionUID = 1L;

	@XStreamAlias("firmId")
	private Integer firmId;
	
	@XStreamAlias("providerId")
	private Integer providerId;

	
	@XStreamAlias("firstName")
	private String firstName;
	
	@XStreamAlias("lastName")
	private String lastName;
	
	@XStreamAlias("firmName")
	private String firmName;
	
	@XStreamAlias("phoneNo")
	private String phoneNo;
	
	@XStreamAlias("email")
	private String email ;
	
	@XStreamAlias("resourceLevel")
	private Integer resourceLevel ;
	
	@XStreamAlias("outhToken")
	private String outhToken; 
	
	@XStreamAlias("temporaryPassword")
	private boolean temporaryPassword; 

	@XmlElement(name="locations")
	private Locations locations;

	public Integer getFirmId() {
		return firmId;
	}


	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}


	public Integer getProviderId() {
		return providerId;
	}


	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}


	public String getFirmName() {
		return firmName;
	}


	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}


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


	public String getPhoneNo() {
		return phoneNo;
	}


	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}




	public Integer getResourceLevel() {
		return resourceLevel;
	}


	public void setResourceLevel(Integer resourceLevel) {
		this.resourceLevel = resourceLevel;
	}


	public String getOuthToken() {
		return outhToken;
	}


	public void setOuthToken(String outhToken) {
		this.outhToken = outhToken;
	}
	
	
	public Locations getLocations() {
		return locations;
	}


	public void setLocations(Locations locations) {
		this.locations = locations;
	}


	public boolean isTemporaryPassword() {
		return temporaryPassword;
	}


	public void setTemporaryPassword(boolean temporaryPassword) {
		this.temporaryPassword = temporaryPassword;
	}



}
