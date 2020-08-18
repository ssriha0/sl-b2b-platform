package com.newco.marketplace.api.mobile.beans.updateSoCompletion;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for a list of addOns.
 * @author Infosys
 *
 */
@XStreamAlias("addOns")
public class AddOns {
	
	@XStreamImplicit(itemFieldName="addOn")
	private List<AddOn> addOn;

	@XmlElement (name = "addOn")	
	public List<AddOn> getAddOn() {
		return addOn;
	}

	public void setAddOn(List<AddOn> addOn) {
		this.addOn = addOn;
	}



	


}
