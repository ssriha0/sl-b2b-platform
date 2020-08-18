package com.newco.marketplace.vo.apiSearch;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.newco.marketplace.vo.apiSearch.FirmResponseData;
import com.newco.marketplace.vo.apiSearch.ProviderResponseData;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderResponse {
	
	private String id;
	private String result; //indicates the whether search is success or failure
	private String Message;
	@XmlElement(name="listOfFirms")
	private List<FirmResponseData> listOfFirms;
	@XmlElement(name="listOfProviders")
	private List<ProviderResponseData> listOfProviders;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public List<FirmResponseData> getListOfFirms() {
		return listOfFirms;
	}

	public void setListOfFirms(List<FirmResponseData> listOfFirms) {
		this.listOfFirms = listOfFirms;
	}

	public List<ProviderResponseData> getListOfProviders() {
		return listOfProviders;
	}

	public void setListOfProviders(List<ProviderResponseData> listOfProviders) {
		this.listOfProviders = listOfProviders;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}