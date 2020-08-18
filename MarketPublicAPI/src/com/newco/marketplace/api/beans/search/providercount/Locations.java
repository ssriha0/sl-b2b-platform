/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */

package com.newco.marketplace.api.beans.search.providercount;

import java.util.Date;
import java.util.List;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.api.beans.search.skillTree.Category;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a bean class for storing all information of 
 * the providerType
 * @author Infosys
 * @author Shekhar Nirkhe
 *
 */
@XStreamAlias("locations")
public class Locations {
	
	@XStreamImplicit(itemFieldName="location")
	private List<Location> locationList;

	public Locations() {
	}

	public List<Location> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<Location> locationList) {
		this.locationList = locationList;
	}
}
