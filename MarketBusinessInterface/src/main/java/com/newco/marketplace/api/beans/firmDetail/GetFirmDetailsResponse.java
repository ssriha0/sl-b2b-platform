package com.newco.marketplace.api.beans.firmDetail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * This class would act as response for the getFirmDetails service
 * @author neenu_manoharan
 *
 */
@XSD(name = "getFirmDetailsResponse.xsd", path = "/resources/schemas/search/")
@XmlRootElement(name = "getFirmResponse")
@XStreamAlias("getFirmResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetFirmDetailsResponse implements IAPIResponse {
	
	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("firms")
	private Firms firms;
	
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}
	
	public Firms getFirms() {
		return firms;
	}

	public void setFirms(Firms firms) {
		this.firms = firms;
	}

	public void setVersion(String version) {
		// TODO Auto-generated method stub
		
	}

	public void setSchemaLocation(String schemaLocation) {
		// TODO Auto-generated method stub
		
	}

	public void setNamespace(String namespace) {
		// TODO Auto-generated method stub
		
	}

	public void setSchemaInstance(String schemaInstance) {
		// TODO Auto-generated method stub
		
	}
		

}
