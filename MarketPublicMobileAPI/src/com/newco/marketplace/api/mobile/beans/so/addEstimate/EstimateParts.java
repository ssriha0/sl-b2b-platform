package com.newco.marketplace.api.mobile.beans.so.addEstimate;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


@XStreamAlias("estimateParts")
public class EstimateParts {
	
	@XStreamImplicit(itemFieldName="estimatePart")
	private List<EstimatePart> estimatePart;

	public List<EstimatePart> getEstimatePart() {
		return estimatePart;
	}

	public void setEstimatePart(List<EstimatePart> estimatePart) {
		this.estimatePart = estimatePart;
	}


}
