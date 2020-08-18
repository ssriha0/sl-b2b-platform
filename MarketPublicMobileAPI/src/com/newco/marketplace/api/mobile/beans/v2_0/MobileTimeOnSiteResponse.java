package com.newco.marketplace.api.mobile.beans.v2_0;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing response information for the Mobile
 * TimeOnSite Response
 * 
 * @author Infosys
 * 
 */
@XSD(name = "mobileTimeOnSiteResponse.xsd", path = "/resources/schemas/mobile/v2_0/")
@XmlRootElement(name = "soTimeOnSiteResponse")
@XStreamAlias("soTimeOnSiteResponse")
public class MobileTimeOnSiteResponse implements IAPIResponse {

	@XStreamAlias("soId")
	private String soId;
	
	@XStreamAlias("tripNo")
	private int tripNo;

	@XStreamAlias("results")
	private Results results;

	// default constructor
	public MobileTimeOnSiteResponse() {
	}

	// overloaded constructor to set Results
	public MobileTimeOnSiteResponse(Results results) {
		this.results = results;
	}

	// overloaded constructor to set Results and SO Id
	public MobileTimeOnSiteResponse(String soId, Results results) {
		this.soId = soId;
		this.results = results;
	}
	
	// overloaded constructor to set Results and SO Id
	public MobileTimeOnSiteResponse(String soId, Results results,int trip) {
		this.soId = soId;
		this.results = results;
		if(0!=trip){
			this.tripNo = trip;
		}
		
	}


	public static MobileTimeOnSiteResponse getInstanceForError(
			ResultsCode resultCode, String soId) {

		if (StringUtils.isNotBlank(soId) && null != soId) {
			return new MobileTimeOnSiteResponse(soId, Results.getError(
					resultCode.getMessage(), resultCode.getCode()));
		} else {
			return new MobileTimeOnSiteResponse(Results.getError(
					resultCode.getMessage(), resultCode.getCode()));
		}
	}

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
	public void setSoId(String soId) {
		this.soId = soId;
	}
	
	/**
	 * @return tripNo
	 * the tripNo 
	 */
	public int getTripNo() {
		return tripNo;
	}
	
	/**
	 * @param tripNo
	 * the tripNo to set
	 */
	public void setTripNo(int tripNo) {
		this.tripNo = tripNo;
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
	public void setResults(Results results) {
		this.results = results;
	}

	public void setVersion(String version) {
		// Auto-generated method stub
	}

	public void setSchemaLocation(String schemaLocation) {
		// Auto-generated method stub
	}

	public void setSchemaInstance(String schemaInstance) {
		// Auto-generated method stub
	}

	public void setNamespace(String namespace) {
		// Auto-generated method stub
	}
}
