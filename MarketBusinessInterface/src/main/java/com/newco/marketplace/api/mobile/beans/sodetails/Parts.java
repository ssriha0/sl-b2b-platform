package com.newco.marketplace.api.mobile.beans.sodetails;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for a list of parts.
 * @author Infosys
 *
 */


@XStreamAlias("parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class Parts {
	
	@XStreamAlias("countofParts")
	private Integer countofParts;
		
	@XStreamImplicit(itemFieldName="part")
	private List<Part> part;

	public List<Part> getPart() {
		return part;
	}

	public void setPart(List<Part> part) {
		this.part = part;
	}

	/**
	 * @return the countofParts
	 */
	public Integer getCountofParts() {
		return countofParts;
	}

	/**
	 * @param countofParts the countofParts to set
	 */
	public void setCountofParts(Integer countofParts) {
		this.countofParts = countofParts;
	}

}
