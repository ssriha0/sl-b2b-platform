package com.newco.marketplace.api.beans.so;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for a list of parts.
 * @author Infosys
 *
 */
@XStreamAlias("parts")
public class Parts {
	
	@XStreamImplicit(itemFieldName="part")
	private List<Part> partList;

	public List<Part> getPartList() {
		return partList;
	}

	public void setPartList(List<Part> partList) {
		this.partList = partList;
	}

}
