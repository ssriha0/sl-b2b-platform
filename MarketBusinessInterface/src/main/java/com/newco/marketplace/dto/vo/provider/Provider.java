package com.newco.marketplace.dto.vo.provider;


import org.apache.commons.lang.builder.ToStringBuilder;

import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.webservices.base.CommonVO;

public class Provider extends CommonVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 28787536042702640L;
	private String vendorResourceId;
	private LocationVO providerLocation;
	private Contact providerPrimaryContact;
	private Contact providerAltContact;
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("providerId", getVendorResourceId())
			
			
			.toString();
	}
	
	
	

	public String getVendorResourceId() {
		return vendorResourceId;
	}

	public void setVendorResourceId(String vendorResourceId) {
		this.vendorResourceId = vendorResourceId;
	}




	public LocationVO getProviderLocation() {
		return providerLocation;
	}




	public void setProviderLocation(LocationVO providerLocation) {
		this.providerLocation = providerLocation;
	}




	public Contact getProviderPrimaryContact() {
		return providerPrimaryContact;
	}




	public void setProviderPrimaryContact(Contact providerPrimaryContact) {
		this.providerPrimaryContact = providerPrimaryContact;
	}




	public Contact getProviderAltContact() {
		return providerAltContact;
	}




	public void setProviderAltContact(Contact providerAltContact) {
		this.providerAltContact = providerAltContact;
	}

	

}
