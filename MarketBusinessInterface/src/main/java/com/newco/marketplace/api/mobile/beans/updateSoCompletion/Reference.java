package com.newco.marketplace.api.mobile.beans.updateSoCompletion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing customer reference information.
 * @author Infosys
 *
 */
@XStreamAlias("reference")
@XmlAccessorType(XmlAccessType.FIELD)
public class Reference {
	
	@XStreamAlias("referenceName")
	private String referenceName;
	
	@XStreamAlias("referenceValue")
	private String referenceValue;

	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	public String getReferenceValue() {
		return referenceValue;
	}

	public void setReferenceValue(String referenceValue) {
		this.referenceValue = referenceValue;
	}

	

}
