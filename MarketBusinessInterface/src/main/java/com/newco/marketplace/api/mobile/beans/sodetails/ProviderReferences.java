package com.newco.marketplace.api.mobile.beans.sodetails;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information on a list of provider References.
 * @author Infosys
 *
 */

@XStreamAlias("providerReferences")
public class ProviderReferences {

	
	@XStreamImplicit(itemFieldName="providerReference")
	private  List<ProviderReference> providerReference;

	public List<ProviderReference> getProviderReference() {
		return providerReference;
	}

	public void setProviderReference(List<ProviderReference> providerReference) {
		this.providerReference = providerReference;
	}

	
}
