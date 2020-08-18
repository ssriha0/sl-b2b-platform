package com.newco.marketplace.api.mobile.beans.sodetails;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for a list of parts.
 * @author Infosys
 *
 */


@XStreamAlias("partsTracking")
public class PartsTracking {
	
	@XStreamImplicit(itemFieldName="part")
	private List<PartTrack> part;

	public List<PartTrack> getPart() {
		return part;
	}

	public void setPart(List<PartTrack> part) {
		this.part = part;
	}

}
