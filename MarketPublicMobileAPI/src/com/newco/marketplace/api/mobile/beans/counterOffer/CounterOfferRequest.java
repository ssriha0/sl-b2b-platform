package com.newco.marketplace.api.mobile.beans.counterOffer;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.so.CounterOfferResources;
import com.newco.marketplace.api.beans.so.OfferReasonCodes;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/16
 * This is a bean class for storing request information for the CounterOffer
 */
@XSD(name = "counterOfferRequest.xsd", path = "/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "counterOfferRequest")
@XStreamAlias("counterOfferRequest")
public class CounterOfferRequest {
	
	@XStreamAlias("serviceDateTime1")
	private String serviceDateTime1;
	
	@XStreamAlias("serviceDateTime2")
	private String serviceDateTime2;
	
	@XStreamAlias("spendLimit")
	private String spendLimit;

	@XStreamAlias("offerExpirationDate")
	private String offerExpirationDate;
	
	@XStreamAlias("reasonCodes")
	private OfferReasonCodes reasonCodes;
	
	@XStreamAlias("resourceIds")
	private CounterOfferResources resourceIds;
	

	/**
	 * @return the serviceDateTime1
	 */
	public String getServiceDateTime1() {
		return serviceDateTime1;
	}

	/**
	 * @param serviceDateTime1 the serviceDateTime1 to set
	 */
	public void setServiceDateTime1(final String serviceDateTime1) {
		this.serviceDateTime1 = serviceDateTime1;
	}

	/**
	 * @return the serviceDateTime2
	 */
	public String getServiceDateTime2() {
		return serviceDateTime2;
	}

	/**
	 * @param serviceDateTime2 the serviceDateTime2 to set
	 */
	public void setServiceDateTime2(final String serviceDateTime2) {
		this.serviceDateTime2 = serviceDateTime2;
	}

	/**
	 * @return the spendLimit
	 */
	public String getSpendLimit() {
		return spendLimit;
	}

	/**
	 * @param spendLimit the spendLimit to set
	 */
	public void setSpendLimit(final String spendLimit) {
		this.spendLimit = spendLimit;
	}

	/**
	 * @return the offerExpirationDate
	 */
	public String getOfferExpirationDate() {
		return offerExpirationDate;
	}

	/**
	 * @param offerExpirationDate the offerExpirationDate to set
	 */
	public void setOfferExpirationDate(final String offerExpirationDate) {
		this.offerExpirationDate = offerExpirationDate;
	}

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

	/**
	 * @return the reasonCodes
	 */
	public OfferReasonCodes getReasonCodes() {
		return reasonCodes;
	}

	/**
	 * @param reasonCodes the reasonCodes to set
	 */
	public void setReasonCodes(OfferReasonCodes reasonCodes) {
		this.reasonCodes = reasonCodes;
	}	

	

}
