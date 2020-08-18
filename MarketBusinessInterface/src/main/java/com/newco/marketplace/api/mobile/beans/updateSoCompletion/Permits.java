package com.newco.marketplace.api.mobile.beans.updateSoCompletion;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for a list of Permits.
 * @author Infosys
 *
 */

@XStreamAlias("permits")
public class Permits {
	
	@XStreamImplicit(itemFieldName="permit")
	private List<Permit> permit;

	@XmlElement (name = "permit")	
	public List<Permit> getPermit() {
		return permit;
	}

	public void setPermit(List<Permit> permit) {
		this.permit = permit;
	}

	
	
}
