package com.newco.marketplace.api.mobile.beans.sodetails;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for a list of PermitAddons.
 * @author Infosys
 *
 */

@XStreamAlias("permitAddons")
public class PermitAddons {
	

	@XStreamImplicit(itemFieldName="permitAddon")
	private List<PermitAddon> permitAddon;

	public List<PermitAddon> getPermitAddon() {
		return permitAddon;
	}

	public void setPermitAddon(List<PermitAddon> permitAddon) {
		this.permitAddon = permitAddon;
	}


}
