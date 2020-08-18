package com.newco.marketplace.api.mobile.beans.sodetails;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for InvoiceParts.
 * @author Infosys
 *
 */

@XStreamAlias("invoiceParts")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoiceParts {
	
	@XStreamImplicit(itemFieldName="partCoverage")
	private List<PartCoverage> partCoverage;
	
	@XStreamImplicit(itemFieldName="partSource")
	private List<PartSource> partSource;
    
	@XStreamAlias("invoicePartsList")
	private InvoicePartsList invoicePartsList;
	
	public List<PartCoverage> getPartCoverage() {
		return partCoverage;
	}

	public void setPartCoverage(List<PartCoverage> partCoverage) {
		this.partCoverage = partCoverage;
	}

	public List<PartSource> getPartSource() {
		return partSource;
	}

	public void setPartSource(List<PartSource> partSource) {
		this.partSource = partSource;
	}

	public InvoicePartsList getInvoicePartsList() {
		return invoicePartsList;
	}

	public void setInvoicePartsList(InvoicePartsList invoicePartsList) {
		this.invoicePartsList = invoicePartsList;
	}


}
