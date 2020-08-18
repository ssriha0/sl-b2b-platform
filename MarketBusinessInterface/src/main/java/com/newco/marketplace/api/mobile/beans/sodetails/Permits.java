package com.newco.marketplace.api.mobile.beans.sodetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing information for Permits.
 * @author Infosys
 *
 */

@XStreamAlias("Permits")
@XmlAccessorType(XmlAccessType.FIELD)
public class Permits {
	
	@XStreamAlias("permitTasks")
	private PermitTasks permitTasks;
	
	@XStreamAlias("permitAddons")
	private PermitAddons permitAddons;

	/**
	 * @return the permitTasks
	 */
	public PermitTasks getPermitTasks() {
		return permitTasks;
	}

	/**
	 * @param permitTasks the permitTasks to set
	 */
	public void setPermitTasks(PermitTasks permitTasks) {
		this.permitTasks = permitTasks;
	}

	/**
	 * @return the permitAddons
	 */
	public PermitAddons getPermitAddons() {
		return permitAddons;
	}

	/**
	 * @param permitAddons the permitAddons to set
	 */
	public void setPermitAddons(PermitAddons permitAddons) {
		this.permitAddons = permitAddons;
	}
}
