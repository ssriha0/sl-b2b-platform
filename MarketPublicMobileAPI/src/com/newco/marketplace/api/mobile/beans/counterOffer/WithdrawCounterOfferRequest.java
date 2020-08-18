package com.newco.marketplace.api.mobile.beans.counterOffer;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.so.CounterOfferResources;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/16
 * This is a bean class for storing request information for the WithdrawCounterOfferRequest
 *
 */
@XSD(name = "withdrawCounterOfferRequest.xsd", path = "/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "withdrawCounterOfferRequest")
@XStreamAlias("withdrawCounterOfferRequest")
public class WithdrawCounterOfferRequest {

	@XStreamAlias("resourceIds")
	private CounterOfferResources resourceIds;

	/**
	 * @return the resourceIds
	 */
	public CounterOfferResources getResourceIds() {
		return resourceIds;
	}

	/**
	 * @param resourceIds the resourceIds to set
	 */
	public void setResourceIds(final CounterOfferResources resourceIds) {
		this.resourceIds = resourceIds;
	}

}
