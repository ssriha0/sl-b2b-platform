package com.newco.marketplace.api.mobile.beans.updateSoCompletion;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for a list of parts.
 * @author Infosys
 *
 */

@XStreamAlias("parts")
public class Parts {
	
	@XStreamAlias("deleteAllInd")
	private String deleteAllInd;
	
	@XStreamImplicit(itemFieldName="part")
	private List<Part> part;

	@XmlElement (name = "part")	
	public List<Part> getPart() {
		return part;
	}

	public void setPart(List<Part> part) {
		this.part = part;
	}

	public String getDeleteAllInd() {
		return deleteAllInd;
	}

	public void setDeleteAllInd(String deleteAllInd) {
		this.deleteAllInd = deleteAllInd;
	}

	
}
