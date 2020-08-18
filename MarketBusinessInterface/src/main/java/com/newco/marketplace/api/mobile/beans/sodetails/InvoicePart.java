package com.newco.marketplace.api.mobile.beans.sodetails;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing documents information.
 * @author Infosys
 *
 */

@XStreamAlias("invoicePart")
public class InvoicePart {
	
	@XStreamAlias("partCoverage")
	private PartCoverage partCoverage;
	
	@XStreamAlias("partSource")
	private PartSource partSource;

	public PartCoverage getPartCoverage() {
		return partCoverage;
	}

	public void setPartCoverage(PartCoverage partCoverage) {
		this.partCoverage = partCoverage;
	}

	public PartSource getPartSource() {
		return partSource;
	}

	public void setPartSource(PartSource partSource) {
		this.partSource = partSource;
	}


	
}
