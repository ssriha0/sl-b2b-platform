package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for AddOnList.
 * @author Infosys
 *
 */

@XStreamAlias("addonList")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddOnList {
	@XStreamImplicit(itemFieldName = "addon")
	private List<AddOn> addon;

	public List<AddOn> getAddon() {
		return addon;
	}

	public void setAddon(List<AddOn> addon) {
		this.addon = addon;
	}

	

}
