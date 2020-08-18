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
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class for storing all information of 
 * the providerType
 * @author Infosys
 * @author Shekhar Nirkhe
 *
 */
@XStreamAlias("location")
public class Location implements Comparable<Location> {
	
	@XStreamAlias("name")
	private String name;

	@XStreamAlias("providerCount")
	private Integer count;
	
	@XStreamAlias("type")   
	@XStreamAsAttribute() 
	private String type;
	
	public Location(String location, int count) {
		this.name  = location;
		this.count = count;
	}


	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public int compareTo(Location another) {
		if (!(another instanceof Location))
			throw new ClassCastException("A Location object expected.");
		return this.name.compareTo(another.name);
	}
}
