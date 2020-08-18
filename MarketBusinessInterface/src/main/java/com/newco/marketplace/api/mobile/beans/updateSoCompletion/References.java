package com.newco.marketplace.api.mobile.beans.updateSoCompletion;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information on a list of customer references.
 * @author Infosys
 *
 */
@XStreamAlias("references")
@XmlAccessorType(XmlAccessType.FIELD)
public class References {

	@XStreamImplicit(itemFieldName="reference")
	private  List<Reference> reference;

	public List<Reference> getReference() {
		return reference;
	}

	public void setReference(List<Reference> reference) {
		this.reference = reference;
	}

	

}
