package com.newco.marketplace.api.beans.search.provider;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.newco.marketplace.vo.apiSearch.ProviderResponse;

@XmlRootElement(name = "searchProviderResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchProviderResponse {
	private String responseFilter;
	private String result;
	private String Messege;
	@XmlElement(name="providerResponse")
	private List<ProviderResponse> providerResponse;
	
	public String getResponseFilter() {
		return responseFilter;
	}
	public void setResponseFilter(String responseFilter) {
		this.responseFilter = responseFilter;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessege() {
		return Messege;
	}
	public void setMessege(String messege) {
		Messege = messege;
	}
	public List<ProviderResponse> getProviderResponse() {
		return providerResponse;
	}
	public void setProviderResponse(List<ProviderResponse> providerResponse) {
		this.providerResponse = providerResponse;
	}
}