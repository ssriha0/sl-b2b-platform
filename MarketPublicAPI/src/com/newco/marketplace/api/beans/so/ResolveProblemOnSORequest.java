package com.newco.marketplace.api.beans.so;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name = "ResolveProblemOnSORequest.xsd", path = "/resources/schemas/so/")
@XStreamAlias("resolveProblemOnSORequest")
public class ResolveProblemOnSORequest {
	@XStreamAlias("resolveComments")
	private String resolveComments;

	public String getResolveComments() {
		return resolveComments;
	}

	public void setResolveComments(String resolveComments) {
		this.resolveComments = resolveComments;
	}
}
