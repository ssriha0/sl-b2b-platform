package com.newco.marketplace.api.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("invoiceParts")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoiceParts {

	@XStreamImplicit(itemFieldName = "invoicePart")
	private List<PartsDatas> partsDatas;

	public List<PartsDatas> getPartsDatas() {
		return partsDatas;
	}

	public void setPartsDatas(List<PartsDatas> partsDatas) {
		this.partsDatas = partsDatas;
	}

}
