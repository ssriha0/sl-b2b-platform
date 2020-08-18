package com.newco.marketplace.api.mobile.beans.sodetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing Part Source information.
 * @author Infosys
 *
 */

@XStreamAlias("partSource")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartSource {
	
	@XStreamAlias("partSourceId")
	private Integer partSourceId;
	
	@XStreamAlias("partSourceValue")
	private String partSourceValue;

	/**
	 * @return the partSourceId
	 */
	public Integer getPartSourceId() {
		return partSourceId;
	}

	/**
	 * @param partSourceId the partSourceId to set
	 */
	public void setPartSourceId(Integer partSourceId) {
		this.partSourceId = partSourceId;
	}

	/**
	 * @return the partSourceValue
	 */
	public String getPartSourceValue() {
		return partSourceValue;
	}

	/**
	 * @param partSourceValue the partSourceValue to set
	 */
	public void setPartSourceValue(String partSourceValue) {
		this.partSourceValue = partSourceValue;
	}


}
