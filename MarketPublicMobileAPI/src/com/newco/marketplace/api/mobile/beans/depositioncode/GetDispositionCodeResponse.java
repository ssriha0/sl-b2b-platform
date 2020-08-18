package com.newco.marketplace.api.mobile.beans.depositioncode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="getDispositionCodesResponse.xsd", path="/resources/schemas/mobile/")
@XStreamAlias("getDispositionCodesResponse")
@XmlRootElement(name="getDispositionCodesResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetDispositionCodeResponse implements IAPIResponse{
	
	@XStreamAlias("results")
	@XmlElement(name="results")
	private Results results;
	
	@XStreamAlias("dispositionCodeList")
	@XmlElement(name="despositionCodeList")
	private  DispositionCodeList dispositionCodeList;
	
	public DispositionCodeList getDispositionCodeList() {
		return dispositionCodeList;
	}

	public void setDispositionCodeList(DispositionCodeList dispositionCodeList) {
		this.dispositionCodeList = dispositionCodeList;
	}
	
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public void setVersion(String version) {
	}

	public void setSchemaLocation(String schemaLocation) {
	}

	public void setNamespace(String namespace) {
	}

	public void setSchemaInstance(String schemaInstance) {
	}

	

}
