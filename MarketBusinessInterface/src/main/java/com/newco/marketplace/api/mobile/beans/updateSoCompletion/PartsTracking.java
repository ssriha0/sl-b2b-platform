package com.newco.marketplace.api.mobile.beans.updateSoCompletion;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for a list of parts tracking information.
 * @author Infosys
 *
 */

@XStreamAlias("partsTracking")
public class PartsTracking {
	
	@XStreamImplicit(itemFieldName="part")
	private List<PartTracking> part;

	@XmlElement (name = "part")	
	public List<PartTracking> getPart() {
		return part;
	}

	public void setPart(List<PartTracking> part) {
		this.part = part;
	}

	

	
}
