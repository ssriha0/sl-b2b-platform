package com.newco.marketplace.api.mobile.beans.sodetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing information for addons.
 * @author Infosys
 *
 */

@XStreamAlias("addons")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddOns {
	@XStreamAlias("addonList")
	private AddOnList addonList;	

	@XStreamAlias("addonMailCheckAddress")
	private String addonMailCheckAddress;

	@XStreamAlias("allowedCardTypes")
	private AllowedCardTypes allowedCardTypes;
	
	/*Removing additional payment info
	 * 
	 * @XStreamAlias("addonPayment")
	private AddonPayment addonPayment;*/
	/**
	 * @return the addonMailCheckAddress
	 */
	public String getAddonMailCheckAddress() {
		return addonMailCheckAddress;
	}

	/**
	 * @param addonMailCheckAddress the addonMailCheckAddress to set
	 */
	public void setAddonMailCheckAddress(String addonMailCheckAddress) {
		this.addonMailCheckAddress = addonMailCheckAddress;
	}

	/**
	 * @return the allowedCardTypes
	 */
	public AllowedCardTypes getAllowedCardTypes() {
		return allowedCardTypes;
	}

	/**
	 * @param allowedCardTypes the allowedCardTypes to set
	 */
	public void setAllowedCardTypes(AllowedCardTypes allowedCardTypes) {
		this.allowedCardTypes = allowedCardTypes;
	}

	public AddOnList getAddonList() {
		return addonList;
	}

	public void setAddonList(AddOnList addonList) {
		this.addonList = addonList;
	}


}
