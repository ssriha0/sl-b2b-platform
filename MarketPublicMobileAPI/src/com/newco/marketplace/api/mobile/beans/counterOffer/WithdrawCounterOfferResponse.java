package com.newco.marketplace.api.mobile.beans.counterOffer;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/16
 * This is a bean class for storing response information for the WithdrawCounterOffer
 */
@XSD(name = "withdrawCounterOfferResponse.xsd", path = "/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "withdrawCounterOfferResponse")
@XStreamAlias("withdrawCounterOfferResponse")
public class WithdrawCounterOfferResponse implements IAPIResponse {

	@XStreamAlias("soId")
	private String soId;

	@XStreamAlias("results")
	private Results results;

	/**
	 * @return the soId
	 */
	public String getSoId() {
		return soId;
	}

	/**
	 * @param soId
	 *            the soId to set
	 */
	public void setSoId(final String soId) {
		this.soId = soId;
	}

	/**
	 * @return the results
	 */
	public Results getResults() {
		return results;
	}

	/**
	 * @param results
	 *            the results to set
	 */
	public void setResults(final Results results) {
		this.results = results;
	}

	public void setVersion(final String version) {
		// TODO Auto-generated method stub

	}

	public void setSchemaLocation(final String schemaLocation) {
		// TODO Auto-generated method stub

	}

	public void setNamespace(final String namespace) {
		// TODO Auto-generated method stub

	}

	public void setSchemaInstance(final String schemaInstance) {
		// TODO Auto-generated method stub
	}
}
