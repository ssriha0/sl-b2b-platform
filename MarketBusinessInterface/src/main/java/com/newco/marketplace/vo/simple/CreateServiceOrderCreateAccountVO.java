package com.newco.marketplace.vo.simple;

import java.util.List;

import com.newco.marketplace.dto.vo.LookupVO;
import com.sears.os.vo.SerializableBaseVO;

public class CreateServiceOrderCreateAccountVO extends SerializableBaseVO {

	private static final long serialVersionUID = 2981112650509554804L;
	
	private String username;
	private String usernameConfirm;
	private String email;
	private String emailConfirm;
	private String firstName;
	private String lastName;
	private String primaryPhone; 	// Break this into 3 parts? CG
	private String secondaryPhone; // Break this into 3 parts? CG

	private String street1;
	private String aptNo;
	private String street2;
	private String city;
	private String state;
	private String zip;
	private String homeAddressInd;
	private String locName;
    private String buyerTermsAndConditionAgreeInd;
    private String slBucksAgreeInd;
    private Integer slBucksAgreeId; //id from term and cond table
    
	private List<LookupVO> statesList;
    private String buyerTermsAndConditionText;
    private String slBucksText;

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsernameConfirm() {
		return usernameConfirm;
	}
	
	public void setUsernameConfirm(String usernameConfirm) {
		this.usernameConfirm = usernameConfirm;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmailConfirm() {
		return emailConfirm;
	}
	
	public void setEmailConfirm(String emailConfirm) {
		this.emailConfirm = emailConfirm;
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
	
	public String getPrimaryPhone() {
		return primaryPhone;
	}
	
	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}
	
	public String getSecondaryPhone() {
		return secondaryPhone;
	}
	
	public void setSecondaryPhone(String secondaryPhone) {
		this.secondaryPhone = secondaryPhone;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getAptNo() {
		return aptNo;
	}

	public void setAptNo(String aptNo) {
		this.aptNo = aptNo;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getHomeAddressInd() {
		return homeAddressInd;
	}

	public void setHomeAddressInd(String homeAddressInd) {
		this.homeAddressInd = homeAddressInd;
	}

	public String getLocName() {
		return locName;
	}

	public void setLocName(String locName) {
		this.locName = locName;
	}
	
	public String getBuyerTermsAndConditionAgreeInd() {
		return buyerTermsAndConditionAgreeInd;
	}

	public void setBuyerTermsAndConditionAgreeInd(String buyerTermsAndConditionAgreeInd) {
		this.buyerTermsAndConditionAgreeInd = buyerTermsAndConditionAgreeInd;
	}

	public String getSlBucksAgreeInd() {
		return slBucksAgreeInd;
	}

	public void setSlBucksAgreeInd(String slBucksAgreeInd) {
		this.slBucksAgreeInd = slBucksAgreeInd;
	}
	
	public List<LookupVO> getStatesList() {
		return statesList;
	}
	
	public void setStatesList(List<LookupVO> statesList) {
		this.statesList = statesList;
	}

	public String getBuyerTermsAndConditionText() {
		return buyerTermsAndConditionText;
	}

	public void setBuyerTermsAndConditionText(String buyerTermsAndConditionText) {
		this.buyerTermsAndConditionText = buyerTermsAndConditionText;
	}

	public String getSlBucksText() {
		return slBucksText;
	}

	public void setSlBucksText(String slBucksText) {
		this.slBucksText = slBucksText;
	}

	/**
	 * @return the slBucksAgreeId
	 */
	public Integer getSlBucksAgreeId() {
		return slBucksAgreeId;
	}

	/**
	 * @param slBucksAgreeId the slBucksAgreeId to set
	 */
	public void setSlBucksAgreeId(Integer slBucksAgreeId) {
		this.slBucksAgreeId = slBucksAgreeId;
	}
}
