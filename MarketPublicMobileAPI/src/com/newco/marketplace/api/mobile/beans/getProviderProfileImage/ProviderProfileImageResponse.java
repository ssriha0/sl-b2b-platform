package com.newco.marketplace.api.mobile.beans.getProviderProfileImage;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.dto.vo.provider.ProviderDocumentVO;

public class ProviderProfileImageResponse {
	
	private Results results;
	
	private ProviderDocumentVO providerDocument;

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public ProviderDocumentVO getProviderDocument() {
		return providerDocument;
	}

	public void setProviderDocument(ProviderDocumentVO providerDocument) {
		this.providerDocument = providerDocument;
	}

}
