package com.newco.marketplace.api.mobile.beans.getProviderProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.so.placeBid.MobilePlaceBidResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name = "ProviderProfileResponse.xsd", path = "/resources/schemas/mobile/")
@XmlRootElement(name = "providerDetails")
@XStreamAlias("providerDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderProfileResponse implements IAPIResponse {

	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("firmId")
	private String firmId;
	
	@XStreamAlias("resourceId")
	private String resourceId;
	
	@XStreamAlias("firstName")
	private String firstName;
	
	@XStreamAlias("lastName")
	private String lastName;
	
	@XStreamAlias("address")
	private Address address;
	
	@XStreamAlias("email")
	private String email;
	
	@XStreamAlias("phoneNumber")
	private String phoneNumber;
	
	@XStreamAlias("alternatePhone")
	private String alternatePhone;
	
	@XStreamAlias("phoneNumberExt")
	private String phoneNumberExt;
	
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	// default constructor
	public ProviderProfileResponse() {
	}

	// overloaded constructor to set Results
	public ProviderProfileResponse(Results results) {
		this.results = results;
	}

	public static ProviderProfileResponse getInstanceForError(
			ResultsCode resultCode) {
		return new ProviderProfileResponse(Results.getError(
				resultCode.getMessage(), resultCode.getCode()));
	}
	
	public String getFirmId() {
		return firmId;
	}

	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAlternatePhone() {
		return alternatePhone;
	}

	public void setAlternatePhone(String alternatePhone) {
		this.alternatePhone = alternatePhone;
	}

	public String getPhoneNumberExt() {
		return phoneNumberExt;
	}

	public void setPhoneNumberExt(String phoneNumberExt) {
		this.phoneNumberExt = phoneNumberExt;
	}

	public void setVersion(String version) {
		// TODO Auto-generated method stub
	}

	public void setSchemaLocation(String schemaLocation) {
		// TODO Auto-generated method stub
	}

	public void setNamespace(String namespace) {
		// TODO Auto-generated method stub
	}

	public void setSchemaInstance(String schemaInstance) {
		// TODO Auto-generated method stub
	}

}
