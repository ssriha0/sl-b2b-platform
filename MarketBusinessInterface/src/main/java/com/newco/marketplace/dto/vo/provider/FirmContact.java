package com.newco.marketplace.dto.vo.provider;

import com.newco.marketplace.api.beans.so.Location;


/**
 * This is a generic bean class for storing contact information.
 * @author Infosys
 *
 */

public class FirmContact{

	/**
	 * 
	 */
	private String firmName;
	private Integer vendorId;
	private String number;
	private String extension;
    private String adminName;
    private String adminPrimaryPhone;
    private String adminAlternatePhone;
    private String email;
  //SL-21529
    private Location firmLocation;
    
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Location getFirmLocation() {
		return firmLocation;
	}

	public void setFirmLocation(Location firmLocation) {
		this.firmLocation = firmLocation;
	}

}
