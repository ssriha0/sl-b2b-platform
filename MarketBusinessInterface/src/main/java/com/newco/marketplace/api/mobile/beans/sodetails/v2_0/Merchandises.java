package com.newco.marketplace.api.mobile.beans.sodetails.v2_0;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for a list of merchandises.
 * @author Infosys
 *
 */


@XStreamAlias("merchandises")
@XmlAccessorType(XmlAccessType.FIELD)
public class Merchandises {
		
	@XStreamImplicit(itemFieldName="merchandise")
	private List<Merchandise> merchandise;

	public List<Merchandise> getMerchandise() {
		return merchandise;
	}

	public void setMerchandise(List<Merchandise> merchandise) {
		this.merchandise = merchandise;
	}
	
}
