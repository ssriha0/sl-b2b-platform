/**
 *
 */
package com.newco.marketplace.vo.apiUserProfile;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.newco.marketplace.vo.apiSearch.FirmResponseData;

@XmlAccessorType(XmlAccessType.FIELD)
public class UserResponse {

	
	private static final long serialVersionUID = 1L;

	private String userName;
	
	private Integer firmId;
	
	private Integer resourceId; 
	
	private String primaryIndustry;

	private Integer leadPartnerId;
	
	private String leadStatus;

	private String userRoleType;
	
	private String firstName;
	
	private String lastName;
	
	private String phoneNo;
	
	private String mobileNo;
	
	private String email ;
	
	private String emailAlt ;
	
	private String companyName;
	
	
	
	@XmlElement(name="listOflocations")
	private List<LocationResponseDate> listOflocations;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getLeadPartnerId() {
		return leadPartnerId;
	}

	public void setLeadPartnerId(Integer leadPartnerId) {
		this.leadPartnerId = leadPartnerId;
	}

	public String getLeadStatus() {
		return leadStatus;
	}

	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}

	public String getUserRoleType() {
		return userRoleType;
	}

	public void setUserRoleType(String userRoleType) {
		this.userRoleType = userRoleType;
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

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailAlt() {
		return emailAlt;
	}

	public void setEmailAlt(String emailAlt) {
		this.emailAlt = emailAlt;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getFirmId() {
		return firmId;
	}

	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getPrimaryIndustry() {
		return primaryIndustry;
	}

	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}

	public List<LocationResponseDate> getListOflocations() {
		return listOflocations;
	}

	public void setListOflocations(List<LocationResponseDate> listOflocations) {
		this.listOflocations = listOflocations;
	}
	
	

	
	
}
