package com.newco.marketplace.api.beans.so;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("parts")
public class EstimatePartsHistory {

	@XStreamImplicit(itemFieldName="part")
	private List<EstimatePartHistory> part;

	public List<EstimatePartHistory> getPart() {
		return part;
	}

	public void setPart(List<EstimatePartHistory> part) {
		this.part = part;
	}

	
	
}
