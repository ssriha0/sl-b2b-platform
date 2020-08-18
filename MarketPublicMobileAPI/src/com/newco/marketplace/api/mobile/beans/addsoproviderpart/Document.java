package com.newco.marketplace.api.mobile.beans.addsoproviderpart;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("document")
public class Document {
	
	@XStreamImplicit(itemFieldName = "documentId")
	private List<Integer> documentId;

	public List<Integer> getDocumentId() {
		return documentId;
	}

	public void setDocumentId(List<Integer> documentId) {
		this.documentId = documentId;
	}

	
}
