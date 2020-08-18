package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information on a list of Buyer References .
 * @author Infosys
 *
 */

@XStreamAlias("buyerReferences")
@XmlAccessorType(XmlAccessType.FIELD)
public class BuyerReferences {


	@XStreamImplicit(itemFieldName="buyerReference")
	private  List<BuyerReference> buyerReference;

	/**
	 * @return the buyerReference
	 */
	public List<BuyerReference> getBuyerReference() {
		return buyerReference;
	}

	/**
	 * @param buyerReference the buyerReference to set
	 */
	public void setBuyerReference(List<BuyerReference> buyerReference) {
		this.buyerReference = buyerReference;
	}

	
	
}
