package com.newco.marketplace.api.beans.so.retrieve;
/**
 * This is a bean class for storing request information for 
 * the SORetrieveSpendLimitService
 * @author Infosys
 *
 */
import com.newco.marketplace.api.beans.so.Identification;
import com.newco.marketplace.api.beans.so.retrieve.ResponseFilter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("soRequestForSpendLimit")
public class SORetrieveRequestForSpendLimit {
	@XStreamAlias("identification")
	private Identification identification;
	@XStreamAlias("responsefilter")
	private ResponseFilter responseFilter;
	public Identification getIdentification() {
		return identification;
	}
	public void setIdentification(Identification identification) {
		this.identification = identification;
	}
	public ResponseFilter getResponseFilter() {
		return responseFilter;
	}
	public void setResponseFilter(ResponseFilter responseFilter) {
		this.responseFilter = responseFilter;
	}
	

}
