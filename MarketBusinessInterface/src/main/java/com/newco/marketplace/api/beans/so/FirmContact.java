package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * This is a generic bean class for storing contact information.
 * @author Infosys
 *
 */
@XStreamAlias("acceptedProviderFirmContact")
public class FirmContact{
	
	@XStreamAlias("firmId")
	private Integer firmId;
	
	@XStreamAlias("firmName")
	private String firmName;
	
	@XStreamAlias("businessPhone")
	private BusinessPhone businessPhone;
	
	@XStreamAlias("adminName")
    private String adminName;
	
	@XStreamAlias("adminPrimaryPhone")
    private String adminPrimaryPhone;
	
	@XStreamAlias("adminAlternatePhone")
    private String adminAlternatePhone;
	
	@XStreamAlias("email")
    private String email;
	
	//SL-19206 To show if the order is assigned or unassigned
	@XStreamAlias("response")
	private String response;
	
	//SL-21529
	@XStreamAlias("location")
	private Location firmLocation;
	
	public Integer getFirmId() {
		return firmId;
	}

	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public BusinessPhone getBusinessPhone() {
		return businessPhone;
	}

	public void setBusinessPhone(BusinessPhone businessPhone) {
		this.businessPhone = businessPhone;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminPrimaryPhone() {
		return adminPrimaryPhone;
	}

	public void setAdminPrimaryPhone(String adminPrimaryPhone) {
		this.adminPrimaryPhone = adminPrimaryPhone;
	}

	public String getAdminAlternatePhone() {
		return adminAlternatePhone;
	}

	public void setAdminAlternatePhone(String adminAlternatePhone) {
		this.adminAlternatePhone = adminAlternatePhone;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Location getFirmLocation() {
		return firmLocation;
	}

	public void setFirmLocation(Location firmLocation) {
		this.firmLocation = firmLocation;
	}
}
